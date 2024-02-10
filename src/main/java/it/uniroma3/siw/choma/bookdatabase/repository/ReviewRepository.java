package it.uniroma3.siw.choma.bookdatabase.repository;

import it.uniroma3.siw.choma.bookdatabase.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
