package rest.model.db

import com.fasterxml.jackson.annotation.JsonIgnore
import rest.model.to.UserIN
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String = "",
        val email: String = "",
        @JsonIgnore
        val password: String = "",
        val isTemporaryPassword: Boolean = true,
        val firstname: String = "",
        val lastname: String = "",
        @OneToMany(cascade = [CascadeType.ALL])
        val permissions: MutableList<Permission> = mutableListOf(),
        val updated: Date? = null,
        val created: Date = Date(LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()),
        val failedAttempts: Long = 0,
        val passwordExpires: Date = Date(LocalDateTime.now().plusDays(180).withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli())
) {
    constructor(user: UserIN) : this(
            username = user.username,
            email = user.email,
            firstname = user.firstname,
            lastname = user.lastname,
            permissions = user.permissions
    )
}