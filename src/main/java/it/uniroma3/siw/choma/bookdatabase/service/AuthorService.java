package it.uniroma3.siw.choma.bookdatabase.service;

import it.uniroma3.siw.choma.bookdatabase.model.Author;
import it.uniroma3.siw.choma.bookdatabase.model.Book;
import it.uniroma3.siw.choma.bookdatabase.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Transactional
    public void save(Author author){
        authorRepository.save(author);
    }

    public boolean alreadyExists(Author author){
        return authorRepository.existsByNameAndSurname(author.getName(), author.getSurname());
    }
    public Author findById(Long id){
        return authorRepository.findById(id).orElse(null);
    }

    public Iterable<Author> findAll(){
        return authorRepository.findAll();
    }

    public Iterable<Author> findDirectorsToSel(Book book){
        return authorRepository.findAllByWrittenBooksIsNotContaining(book);
    }

}
