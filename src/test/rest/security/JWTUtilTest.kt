package rest.security

import io.jsonwebtoken.JwtException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import rest.model.Permission
import rest.model.PermissionEnum
import rest.model.UserTO

internal class JWTUtilTest {
    @Test
    fun create() {
        assertNotNull(JWTUtil.create(UserTO()))
    }

    @Test
    fun validateValidToken() {
        JWTUtil.validate(JWTUtil.create(UserTO(permissions = mutableListOf(Permission(1, PermissionEnum._CREATE)))))
    }

    @Test
    fun updateToken() {
        val token: String = JWTUtil.create(UserTO())
        val updatedToken = JWTUtil.update(token, UserTO())
        assertNotNull(updatedToken)
        JWTUtil.validate(updatedToken)
    }

    @Test
    fun shouldThrowExceptionWhenTokenHasBeenAltered() {
        assertThrows(JwtException::class.java) {
            val modifiedPermissionToUpdate = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoie1widXNlcm5hbWVcIjpcIlwiLFwiZW1haWxcIjpcIlwiLFwiZmlyc3RuYW1lXCI6XCJcIixcImxhc3RuYW1lXCI6XCJcIixcInBlcm1pc3Npb25zXCI6W3tcImlkXCI6MSxcInBlcm1pc3Npb25cIjpcIl9VUERBVEVcIn1dLFwidXBkYXRlZFwiOm51bGwsXCJjcmVhdGVkXCI6MTU5NjUzNjc5MDM5MixcInRlbXBvcmFyeVBhc3N3b3JkXCI6dHJ1ZX0iLCJpc3MiOiJpc3N1ZXIiLCJleHAiOjE1OTY1NDAzOTB9.3lwfpcvDeu71BwL-Z-pfs_1aGYDHkOVIRX78E7dlicI"
            JWTUtil.validate(modifiedPermissionToUpdate)
        }
    }
}