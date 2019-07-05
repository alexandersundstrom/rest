package rest.database;

import org.springframework.data.repository.CrudRepository;
import rest.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByFirstname(String firstname);
}
