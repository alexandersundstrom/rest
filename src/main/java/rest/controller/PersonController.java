package rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rest.model.Person;

@RestController
@RequestMapping("person")
public class PersonController {


    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "Hello world";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Person getPerson() {
        return new Person("Mr Test", "Tester");
    }
}
