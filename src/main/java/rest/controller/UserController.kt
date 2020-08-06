package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import rest.model.to.UserIN
import rest.model.to.UserOUT
import rest.service.UserService

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private var service: UserService? = null

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll() = service!!.findAll()


    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun findById(@PathVariable id: String) = service!!.findById(id)

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@RequestBody user: UserIN): UserOUT {
        return service!!.create(user)
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/{id}"])
    fun updateUser(@RequestBody user: UserIN, @PathVariable id: String?): UserOUT {
        return service!!.save(user)
    }

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        service!!.deleteById(id)
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    fun ping(): String {
        println("Anrop till ping, returnerar svar")
        return "Hello world"
    }
}