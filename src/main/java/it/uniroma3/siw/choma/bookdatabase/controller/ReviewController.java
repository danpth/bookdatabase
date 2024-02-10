package it.uniroma3.siw.choma.bookdatabase.controller;


import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.model.Review;
import it.uniroma3.siw.choma.bookdatabase.service.BookService;
import it.uniroma3.siw.choma.bookdatabase.service.ReviewService;
import it.uniroma3.siw.choma.bookdatabase.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @GetMapping("/registered/formNewReview/{book_id}")
    public String formNewReview(@PathVariable("book_id") Long book_id,Model model){
        model.addAttribute("review", new Review());
        model.addAttribute("book_id", book_id);
        return "registered/formNewReview";
    }
    @PostMapping("/registered/review/{book_id}")
    public String newBook(@PathVariable("book_id") Long book_id,@Valid @ModelAttribute("review") Review review, BindingResult bindingResult, Model model){
        Book book= bookService.findById(book_id);
        if(!userService.canReview(userService.getCurrentUser(), book)){
            return "errors/cannotCreateMoreReview";
        }
        if(!bindingResult.hasErrors()){
            if(book == null) return "errors/bookNotFoundError";

            review.setreviewedBook(book);
            review.setAuthor(userService.getCurrentUser());
            review.setCreationDateTime(LocalDateTime.now());
            this.reviewService.save(review);
            model.addAttribute("review", review);
            return "redirect:/book/" + book_id;
        }else{
            return "registered/formNewReview";
        }
    }
    @GetMapping("/registered/removeOwnReview/{review_id}")
    public String removeOwnReview(@PathVariable("review_id") Long id, Model model){
        Review review = reviewService.findById(id);
        if(review == null ||
                !userService.getCurrentUser().equals(review.getAuthor()))
            return "errors/reviewNotFoundError";
        Book book = review.getreviewedBook();
        reviewService.remove(review);
        return "redirect:/book/"+book.getId();
    }
    //########### ADMIN #################
    @GetMapping("/admin/removeReview/{review_id}")
    public String removeReview(@PathVariable("review_id") Long id, Model model){
        Review review = reviewService.findById(id);
        if(review == null) return "errors/reviewNotFoundError";

        Book book = review.getreviewedBook();
        reviewService.remove(review);
        return "redirect:/admin/book/"+book.getId();
    }
}
