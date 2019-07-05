package rest.controller;

import org.springframework.web.bind.annotation.*;
import rest.model.Person;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mocked/person")
public class MockedPersonController {

    private List<Person> persons = List.of(
            new Person(1L, "Alexander", "Svensson"),
            new Person(2L, "Anna", "Andersson"),
            new Person(3L, "Mikael", "Smith"),
            new Person(4L, "Mia", "Pia")
    );


    @RequestMapping(method = RequestMethod.GET)
    public List<Person> getPersons() {
        return persons;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Optional<Person> getPerson(@PathVariable Long id) {
        return persons
                .stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Person createPerson(@RequestBody Person person) {
        return new Person(2L, person.getFirstname(), person.getLastname());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return new Person(id, person.getFirstname(), person.getLastname());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deletePerson(@PathVariable Long id) {
        //        should call service to delete entity
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "Hello world";
    }
}
