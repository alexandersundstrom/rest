package rest.database;

import org.springframework.data.repository.CrudRepository;
import rest.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
