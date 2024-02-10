package it.uniroma3.siw.choma.bookdatabase.repository;

import it.uniroma3.siw.choma.bookdatabase.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    public List<Book> findByYear(Integer year);
    public boolean existsByTitleAndYear(String title, Integer year);

    /*public List<Book> findByOrderByTitle();*/





}
