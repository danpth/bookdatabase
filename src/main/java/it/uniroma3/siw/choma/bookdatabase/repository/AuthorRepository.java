package it.uniroma3.siw.choma.bookdatabase.repository;

import it.uniroma3.siw.choma.bookdatabase.model.Author;
import it.uniroma3.siw.choma.bookdatabase.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    public boolean existsByNameAndSurname(String name, String surname);
    public Iterable<Author> findAllByWrittenBooksIsNotContaining(Book book);

}