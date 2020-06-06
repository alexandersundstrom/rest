package rest.controller

import org.springframework.web.bind.annotation.*
import rest.model.Person
import java.util.*

@RestController
@RequestMapping("/mocked/person")
class MockedPersonController {
    @get:RequestMapping(method = [RequestMethod.GET])
    val persons = listOf<Person>(
            Person(1L, "Alexander", "Svensson"),
            Person(2L, "Anna", "Andersson"),
            Person(3L, "Mikael", "Smith"),
            Person(4L, "Mia", "Pia")
    )

    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun getPerson(@PathVariable id: Long): Optional<Person> {
        return persons
                .stream()
                .filter { person: Person -> person.id == id }
                .findFirst()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun createPerson(@RequestBody person: Person): Person {
        return Person(2L, person.firstname, person.lastname)
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/{id}"])
    fun updatePerson(@PathVariable id: Long?, @RequestBody person: Person): Person {
        return Person(id, person.firstname, person.lastname)
    }

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/{id}"])
    fun deletePerson(@PathVariable id: Long?) {
        //        should call service to delete entity
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    fun ping(): String {
        return "Hello world"
    }
}