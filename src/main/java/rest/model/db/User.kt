package rest.model.db

import com.fasterxml.jackson.annotation.JsonIgnore
import rest.model.to.UserIN
import java.time.Instant
import java.time.temporal.ChronoUnit
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
        val created: Date = Date(),
        val failedAttempts: Long = 0,
        val passwordExpires: Date = Date(Instant.now().plus(180, ChronoUnit.DAYS).toEpochMilli())
) {
    constructor(user: UserIN) : this(
            username = user.username,
            email = user.email,
            firstname = user.firstname,
            lastname = user.lastname,
            permissions = user.permissions
    )
}