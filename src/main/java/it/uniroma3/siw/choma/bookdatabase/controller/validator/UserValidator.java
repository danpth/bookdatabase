package it.uniroma3.siw.choma.bookdatabase.controller.validator;


import it.uniroma3.siw.choma.bookdatabase.model.User;
import it.uniroma3.siw.choma.bookdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.alreadyExists(user)){
            errors.reject("user.alreadyRegistered");
        }
    }
}

