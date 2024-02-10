package it.uniroma3.siw.choma.bookdatabase.controller;


import it.uniroma3.siw.choma.bookdatabase.service.AuthorService;
import it.uniroma3.siw.choma.bookdatabase.controller.util.FileUploadUtil;
import it.uniroma3.siw.choma.bookdatabase.controller.validator.BookValidator;
import it.uniroma3.siw.choma.bookdatabase.model.Author;
import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    BookValidator bookValidator;

    @GetMapping("/book")
    public String showBooks(Model model){
        model.addAttribute("books", this.bookService.findAll());
        return "books";
    }
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "/errors/bookNotFoundError";

        model.addAttribute("book", book);
        return "book";
    }
    @GetMapping("/formSearchBooks")
    public String formSearchBooks(Model model){
        return "formSearchBooks";
    }
    @PostMapping("/searchBooks")
    public String searchBooks(Model model, @RequestParam Integer year){
        model.addAttribute("books", this.bookService.findByYear(year));
        return "foundBooks";
    }
    //############### ADMIN #################
    @GetMapping("/admin/book")
    public String showBooksAdmin(Model model){
        model.addAttribute("books", this.bookService.findAll());
        return "admin/booksAdmin";
    }
    @PostMapping("/admin/book")
    public String newBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        bookValidator.validate(book, bindingResult);
        if(!bindingResult.hasErrors()){
            this.bookService.createNewBook(book);
            model.addAttribute("book", book);
            return "admin/bookAdmin";
        }else{
            return "admin/formNewBook";
        }
    }
    @GetMapping("/admin/book/{id}")
    public String getBookAdmin(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        model.addAttribute("book", book);
        return "admin/bookAdmin";
    }
    @GetMapping("/admin/formNewBook")
    public String formNewBook(Model model){
        Book book =  new Book();
        model.addAttribute("book", book);
        return "admin/formNewBook";
    }
    @GetMapping("/admin/selectAuthorToBook/{id}")
    public String selectAuthorToBook(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findDirectorsToSel(book));
        return "admin/selectAuthorToBook";
    }
    @GetMapping("/admin/setAuthorToBook/{book_id}/{author_id}")
    public String addAuthorToBook(@PathVariable("book_id") Long book_id,
                                     @PathVariable("author_id") Long author_id,
                                     Model model){
        Author author = authorService.findById(author_id);
        if(author == null) return "errors/authorNotFoundError";

        Book book = bookService.findById(book_id);
        if(book == null) return "errors/bookNotFoundError";
        book.setAuthor(author);
        bookService.save(book);
        model.addAttribute("book", book);
        return "admin/bookAdmin";
    }


    @GetMapping("/admin/choseCategoryToBook/{book_id}")
    public String choseCategoryFromBookToRemove(@PathVariable("book_id") Long book_id,
                                       Model model) {

        Book book = bookService.findById(book_id);
        if (book == null) return "errors/bookNotFoundError";
        model.addAttribute("inputText", new String());
        model.addAttribute("book", book);
        return "admin/choseCategoryToBook";
    }
    @GetMapping("/admin/addCategoryToBook/{category}/{book_id}")
    public String addCategoryToBook(@PathVariable("book_id") Long book_id,
                                         @PathVariable("category") String category,
                                         Model model) {

        Book book = bookService.findById(book_id);
        if (book == null) return "errors/bookNotFoundError";
        book.getCategories().add(category);
        bookService.save(book);
        model.addAttribute("book", book);
        return "/admin/bookAdmin";
    }
    @GetMapping("/admin/removeCategoryFromBook/{book_id}/{category}")
    public String removeCategoryFromBook(@PathVariable("book_id") Long book_id,
                                                @PathVariable("category") String category,
                                                Model model){

        Book book = bookService.findById(book_id);
        if(book == null) return "errors/bookNotFoundError";
        Set<String> categories = book.getCategories();
        for (String s:
             categories){
                 if(s.equals(category))
                     bookService.removeCategory(book,s);

        }
        model.addAttribute("book", book);
        return "/admin/bookAdmin";
    }
    @GetMapping("/admin/removeBook/{bookId}")
    public String removeBook(@PathVariable("bookId") Long id,
                             Model model){
        Book book = bookService.findById(id);
        if(book==null) return "/errors/bookNotFoundError";

        bookService.removeBook(bookService.findById(id));
        return showBooksAdmin(model);
    }

    @PostMapping("/admin/saveBookImage/{id}")
    public String saveBookImage(@PathVariable("id") Long id,
                                 @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        book.setPicFilename(fileName);
        bookService.save(book);
        String uploadDir = "src/main/upload/images/book_pics/" + book.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/book/"+ id;
    }
}
