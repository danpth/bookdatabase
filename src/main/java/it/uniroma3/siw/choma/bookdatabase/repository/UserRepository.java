package it.uniroma3.siw.choma.bookdatabase.repository;


import it.uniroma3.siw.choma.bookdatabase.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    public boolean existsByNameAndSurnameAndEmail(String name, String surname, String email);
}

