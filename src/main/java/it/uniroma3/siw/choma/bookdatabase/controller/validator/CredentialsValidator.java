package it.uniroma3.siw.choma.bookdatabase.controller.validator;


import it.uniroma3.siw.choma.bookdatabase.model.Credentials;
import it.uniroma3.siw.choma.bookdatabase.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {
    @Autowired
    CredentialsService credentialsService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Credentials credentials = (Credentials) target;
        if(credentialsService.alreadyExistsUsername(credentials.getUsername())){
            errors.reject("credentials.alreadyExistsUsername");
        }
    }
}

