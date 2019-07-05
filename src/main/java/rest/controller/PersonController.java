package rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {


    @RequestMapping("/ping")
    public String ping() {
          return "Hello world";
    }
}
