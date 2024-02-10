package it.uniroma3.siw.choma.bookdatabase.controller;


import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.model.Review;
import it.uniroma3.siw.choma.bookdatabase.model.User;
import it.uniroma3.siw.choma.bookdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private UserService userService;
    @ModelAttribute("userAuthDetails")
    public UserDetails getUserAuthDetails() {
        UserDetails user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
    @ModelAttribute("globalUser")
    public User getUser() {
        return userService.getCurrentUser();
    }
    @ModelAttribute("reviewedBooks")
    public Set<Book> reviewedBooks() {
        User user = userService.getCurrentUser();
        if (user != null) {
            Set<Review> reviews = user.getReviews();
            Set<Book> books = new HashSet<>();
            for (Review review : reviews) {
                books.add(review.getreviewedBook());
            }
            return books;
        }
        return Collections.EMPTY_SET;
    }
}
