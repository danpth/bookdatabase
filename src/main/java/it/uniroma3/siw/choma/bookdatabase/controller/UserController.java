package it.uniroma3.siw.choma.bookdatabase.controller;

import it.uniroma3.siw.choma.bookdatabase.controller.util.FileUploadUtil;
import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.model.User;
import it.uniroma3.siw.choma.bookdatabase.service.BookService;
import it.uniroma3.siw.choma.bookdatabase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @GetMapping("/registered/profile")
    public String showProfilePage(Model model){
        return "registered/profile";
    }
    @PostMapping("/registered/saveProfileImage")
    public String saveProfileImage(@RequestParam("image")MultipartFile multipartFile, Model model) throws IOException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        User user = userService.getCurrentUser();
        user.setPicFilename(fileName);
        userService.save(user);
        String uploadDir = "src/main/upload/images/user_pics/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return showProfilePage(model);
    }
    @GetMapping("/registered/addToMyList/{id}")
    public String addToReadlist(@PathVariable("id") Long id,Model model  ){
        Book book = bookService.findById(id);
        if (book==null) return "errors/bookNotFoundError";

        User user = userService.getCurrentUser();
        user.getReadList().add(book);
        userService.save(user);
        return "redirect:/book";
    }

    @GetMapping("/registered/removeToMyList/{id}")
    public String removeToWatchlist(@PathVariable("id") Long id,Model model  ){
        Book book = bookService.findById(id);
        if (book==null) return "errors/bookNotFoundError";

        User user = userService.getCurrentUser();
        user.getReadList().remove(book);
        userService.save(user);
        return "redirect:/book";
    }
    //######ADMIN########

    @GetMapping("/admin/addToMyList/{id}")
    public String addToReadlistAdmin(@PathVariable("id") Long id,Model model  ){
        Book book = bookService.findById(id);
        if (book==null) return "errors/bookNotFoundError";

        User user = userService.getCurrentUser();
        user.getReadList().add(book);
        userService.save(user);
        return "redirect:/admin/book";
    }
    @GetMapping("/admin/removeFromMyList/{id}")
    public String removeToReadlistAdmin(@PathVariable("id") Long id,Model model  ){
        Book book = bookService.findById(id);
        if (book==null) return "errors/bookNotFoundError";

        User user = userService.getCurrentUser();
        user.getReadList().remove(book);
        userService.save(user);
        return "redirect:/admin/book";
    }
}
