package rest.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import rest.model.UserTO
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class JWTUtil() {
    companion object {
        private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        fun create(user: UserTO) = Jwts
                .builder()
                .claim("user", ObjectMapper().writeValueAsString(user))
                .setIssuedAt(Date())
                .setExpiration(Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .signWith(key)
                .compact()


        fun validate(token: String) {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        }

        fun update(token: String, user: UserTO): String {
            validate(token)
            return create(user)
        }
    }
}