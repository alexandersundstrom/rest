package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rest.exception.PasswordException
import rest.model.ChangePswCredentials
import rest.model.User
import rest.service.UserService

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    var service: UserService? = null

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll() = service!!.findAll()


    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun findById(@PathVariable id: String) = service!!.findById(id)

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User {
        //validation
        return service!!.create(user)
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/{id}"])
    fun updateUser(@RequestBody user: User, @PathVariable id: String?): User {
        //validation
        return service!!.save(user)
    }

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        //validation
        service!!.deleteById(id)
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    fun ping(): String {
        println("Anrop till ping, returnerar svar")
        return "Hello world"
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/password"])
    fun changePassword(@RequestBody credentials: ChangePswCredentials): ResponseEntity<String> {
        //validation
        try {
            service!!.changePassword(credentials)
            return ResponseEntity.ok("OK")
        } catch (e: PasswordException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}