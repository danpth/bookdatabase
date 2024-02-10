package it.uniroma3.siw.choma.bookdatabase.service;


import it.uniroma3.siw.choma.bookdatabase.repository.ReviewRepository;
import it.uniroma3.siw.choma.bookdatabase.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void save(Review review){
        reviewRepository.save(review);
    }

    @Transactional
    public void remove(Review review){
        reviewRepository.delete(review);
    }

    public Review findById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }
}