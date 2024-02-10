package it.uniroma3.siw.choma.bookdatabase.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    @Max(2023)
    private Integer year;

    private String picFilename;

    @ElementCollection
    private Set<String> categories;

    @ManyToOne
    private Author author;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reviewedBook")
    private Set<Review> reviews;
    public Book(){
        reviews = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(year, book.year);
    }
    public String getPicPath(){
        if(picFilename != null) return "/upload/images/book_pics/" + this.getId() + "/"
                +this.getPicFilename();
        return "/images/default_profile_pic.png";
    }
    public String getAvgReviews(){
        int sum=0;
        if (reviews.size()==0) return "not reviewed";
        for (Review r:
                reviews) {
            sum += r.getScore();
        }
        int res = sum/reviews.size();

        if(res<10) return "0"+res;

        return ""+res;
    }
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String urlImage) {
        this.picFilename = urlImage;
    }
}
