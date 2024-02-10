package it.uniroma3.siw.choma.bookdatabase.controller.validator;


import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class BookValidator implements Validator {
    @Autowired
    private BookService bookService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book)target;
        if(bookService.alreadyExists(book)){
            errors.reject("book.duplicate");
        }
    }
}

