package it.uniroma3.siw.choma.bookdatabase.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    @Max(10)
    @Min(1)
    private Integer score;
    private LocalDateTime creationDateTime;
    private String content;
    @ManyToOne
    private Book reviewedBook;
    @ManyToOne
    private User author;

    public Review(){

    }
    public Review(Book movie, User author){
        this.reviewedBook = movie;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(title, review.title) && Objects.equals(creationDateTime, review.creationDateTime) && Objects.equals(author, review.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, creationDateTime, author);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Book getreviewedBook() {
        return reviewedBook;
    }

    public void setreviewedBook(Book reviewedBook) {
        this.reviewedBook = reviewedBook;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
    public String getCreationDateTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" - dd-MM-yyyy - HH:mm");
        return this.creationDateTime.format(formatter);
    }

    public void setCreationDateTime(LocalDateTime date) {
        this.creationDateTime = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
