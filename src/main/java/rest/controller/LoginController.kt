package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rest.exception.PasswordException
import rest.exception.UserException
import rest.model.to.ChangePswCredentialsIN
import rest.model.to.PswCredentialsIN
import rest.model.to.UserOUT
import rest.security.JWTService
import rest.service.UserService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/auth")
class LoginController {
    @Autowired
    private var service: UserService? = null

    @Autowired
    private var jwtService: JWTService? = null


    @RequestMapping(method = [RequestMethod.POST], value = ["/password"])
    fun changePassword(@RequestBody credentials: ChangePswCredentialsIN): ResponseEntity<String> {
        try {
            service!!.changePassword(credentials)
            return ResponseEntity.ok("OK")
        } catch (e: PasswordException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/login"])
    @ResponseBody
    fun login(@RequestBody credentials: PswCredentialsIN): ResponseEntity<Any> {
        try {
            val user = service!!.login(credentials)
            val header = HttpHeaders()
            header.add("Set-Cookie", "token=${jwtService!!.createToken(user)}; Path=/")
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(header)
                    .body(user)
        } catch (e: UserException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/updateToken"])
    @ResponseBody
    fun updateToken(request: HttpServletRequest): ResponseEntity<Any> {
        val user = request.getAttribute("user") as UserOUT
        val header = HttpHeaders()
        header.add("Set-Cookie", "token=${jwtService!!.createToken(user)}; Path=/")

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(header)
                .body(user)
    }

}