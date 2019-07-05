package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.database.PersonRepository;
import rest.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Person> findByFirstname(String firstname) {
        return repository.findByFirstname(firstname);
    }
}
