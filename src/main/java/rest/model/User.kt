package rest.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String = "",
        var email: String = "",
        var password: String = "",
        var firstname: String = "",
        var lastname: String = "",
        @OneToMany(cascade = [CascadeType.ALL])
        var permissions: MutableList<Permissions> = mutableListOf(),
        var updated: Date? = null,
        val created: Date = Date()
)