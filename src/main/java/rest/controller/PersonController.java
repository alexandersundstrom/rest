package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.model.Person;
import rest.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService service;


    @RequestMapping(method = RequestMethod.GET)
    public List<Person> getPersons() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Optional<Person> getPerson(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Person createPerson(@RequestBody Person person) {
        return service.save(person);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Person updatePerson(@RequestBody Person person) {
        return service.save(person);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deletePerson(@PathVariable Long id) {
       service.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "Hello world";
    }
}
