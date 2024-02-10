package it.uniroma3.siw.choma.bookdatabase.controller;

import it.uniroma3.siw.choma.bookdatabase.controller.util.FileUploadUtil;
import it.uniroma3.siw.choma.bookdatabase.controller.validator.AuthorValidator;
import it.uniroma3.siw.choma.bookdatabase.model.Author;
import it.uniroma3.siw.choma.bookdatabase.service.AuthorService;
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

@Controller
public class AuthorController {
    @Autowired
    private AuthorValidator authorValidator;
    @Autowired
    private AuthorService authorService;
    @GetMapping("/author")
    public String showAuthors(Model model){
        model.addAttribute("authors", authorService.findAll());
        return "authors";
    }
    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model){
        model.addAttribute("author", authorService.findById(id));
        return "author";
    }
    //###########  ADMIN  ############
    @GetMapping("/admin/formNewAuthor")
    public String formNewAuthor(Model model){
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor";
    }
    @PostMapping("/admin/author")
    public String newAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult, Model model){
        authorValidator.validate(author, bindingResult);
        if(!bindingResult.hasErrors()){
            this.authorService.save(author);
            model.addAttribute("author", author);
            return "admin/authorAdmin";
        }else{
            return "admin/formNewAuthor";
        }
    }
    @GetMapping("/admin/author")
    public String showAuthorsAdmin(Model model){
        model.addAttribute("authors", authorService.findAll());
        return "admin/authorsAdmin";
    }
    @GetMapping("/admin/author/{id}")
    public String getAuthorAdmin(@PathVariable("id") Long id, Model model){
        Author author = authorService.findById(id);
        if(author == null) return "errors/authorNotFoundError";

        model.addAttribute("author", author);
        return "admin/authorAdmin";
    }
    @PostMapping("/admin/saveAuthorImage/{id}")
    public String saveAuthorImage(@PathVariable("id") Long id,
                                  @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Author author = authorService.findById(id);
        if(author == null) return "errors/authorNotFoundError";

        author.setPicFilename(fileName);
        authorService.save(author);
        String uploadDir = "src/main/upload/images/author_pics/" + author.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/author/"+ id;
    }

}
