package rest.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import rest.model.to.UserOUT
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class JWTUtil() {
    companion object {
        private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        fun create(user: UserOUT) = Jwts
                .builder()
                .claim("user", ObjectMapper().writeValueAsString(user))
                .setIssuedAt(Date())
                .setExpiration(Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .signWith(key)
                .compact()


        fun validate(token: String) {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        }

        fun update(token: String, user: UserOUT): String {
            validate(token)
            return create(user)
        }
    }
}