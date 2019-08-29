package rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rest.model.Person;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "Hello world";
    }
}
