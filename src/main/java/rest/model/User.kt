package rest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String = "",
        var email: String = "",
        @JsonIgnore
        var password: String = "",
        var isTemporaryPassword: Boolean = true,
        var firstname: String = "",
        var lastname: String = "",
        @OneToMany(cascade = [CascadeType.ALL])
        var permissions: MutableList<Permission> = mutableListOf(),
        var updated: Date? = null,
        val created: Date = Date(),
        var failedAttempts: Long = 0
)