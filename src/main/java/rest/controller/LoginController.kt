package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rest.exception.PasswordException
import rest.exception.UserException
import rest.model.ChangePswCredentials
import rest.model.PswCredentials
import rest.security.JWTUtil
import rest.service.UserService

@RestController
@RequestMapping("/auth")
class LoginController {
    @Autowired
    var service: UserService? = null


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

    @RequestMapping(method = [RequestMethod.POST], value = ["/login"])
    @ResponseBody
    fun login(@RequestBody credentials: PswCredentials): ResponseEntity<Any> {
        try {
            val user = service!!.login(credentials)
            val header = HttpHeaders()
            header.add("Set-Cookie", "token=${JWTUtil.create(user)}")
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(header)
                    .body(user)
        } catch (e: UserException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
        }
    }

//    update token
}