package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import rest.model.Person
import rest.service.PersonService
import java.util.*

@RestController
@RequestMapping("/person")
class PersonController {
    @Autowired
    var service: PersonService? = null

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(@RequestParam(required = false) firstname: String?): List<Person?>? {
        return if (firstname != null) {
            service!!.findByFirstname(firstname)
        } else service!!.findAll()
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun findById(@PathVariable id: Long): Optional<Person?> {
        return service!!.findById(id)
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun createPerson(@RequestBody person: Person): Person {
        //validation
        return service!!.save(person)
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/{id}"])
    fun updatePerson(@RequestBody person: Person, @PathVariable id: Long?): Person {
        //validation
        return service!!.save(person)
    }

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/{id}"])
    fun deleteById(@PathVariable id: Long) {
        //validation
        service!!.deleteById(id)
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    fun ping(): String {
        println("Anrop till ping, returnerar svar")
        return "Hello world"
    }
}