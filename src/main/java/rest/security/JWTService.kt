package rest.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import rest.exception.TokenException
import rest.model.to.UserOUT
import rest.service.UserService
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

//TODO IN validate methods, handle Exceptions from parseClaims and cast to TokenException as well as log the original

@Service
class JWTService() {
    @Autowired
    private var userService: UserService? = null
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun createToken(user: UserOUT) = Jwts
            .builder()
            .claim("user", jacksonObjectMapper().writeValueAsString(user))
            .setIssuedAt(Date())
            .setExpiration(Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
            .signWith(key)
            .compact()

    fun validateToken(token: String) {
        val parseClaimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        val jsonUser = parseClaimsJws.body["user"] as String
        val tokenUser: UserOUT = jacksonObjectMapper().readValue(jsonUser)
        val dbUpdated = userService!!.getUpdatedDate(tokenUser.username)
        if (!Objects.equals(dbUpdated, tokenUser.updated)) throw TokenException("User has been updated, you need to log in again.", HttpStatus.CONFLICT)
    }

    fun validateTokenAndGetUser(token: String): UserOUT {
        val parseClaimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        val jsonUser = parseClaimsJws.body["user"] as String
        return jacksonObjectMapper().readValue(jsonUser)
    }
}
