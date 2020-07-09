package rest.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom


class PasswordEncoder() {
    companion object {
        fun encode(psw: String) = BCryptPasswordEncoder(10, SecureRandom()).encode(psw)

        fun matches(rawPsw: String, encodedPsw: String) =
                BCryptPasswordEncoder(10, SecureRandom()).matches(rawPsw, encodedPsw)
    }
}