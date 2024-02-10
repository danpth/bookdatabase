package it.uniroma3.siw.choma.bookdatabase.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    private String picFilename;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Book> readList;
    @OneToMany(mappedBy = "author")
    private Set<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email);
    }
    public String getPicPath(){
        if(picFilename != null) return "/upload/images/user_pics/" + this.getId() + "/"
                +this.getPicFilename();
        return "/images/default_profile_pic.jpg";
    }

    public Set<Book> getReadList() {
        return readList;
    }

    public void setReadList(Set<Book> watchList) {
        this.readList = watchList;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String path_of_pic) {
        this.picFilename = path_of_pic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
