package rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @RequestMapping(method = [RequestMethod.POST], value = ["/login"])
    @ResponseBody
    fun login(@RequestBody credentials: PswCredentialsIN): ResponseEntity<UserOUT> {
        val user = service!!.login(credentials)
        val header = HttpHeaders()
        header.add("Set-Cookie", "token=${jwtService!!.createToken(user)}; Path=/")
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(header)
                .body(user)

    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/password"])
    fun changePassword(@RequestBody credentials: ChangePswCredentialsIN): ResponseEntity<String> {
        service!!.changePassword(credentials)
        return ResponseEntity.ok("OK")
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/updateToken"])
    @ResponseBody
    fun updateToken(request: HttpServletRequest): ResponseEntity<UserOUT> {
        val user = request.getAttribute("user") as UserOUT
        val header = HttpHeaders()
        header.add("Set-Cookie", "token=${jwtService!!.createToken(user)}; Path=/")

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(header)
                .body(user)
    }

}