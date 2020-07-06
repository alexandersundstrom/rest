package rest.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String = "",
        var email: String = "",
       @Transient
        var password: String = "",
        var firstname: String = "",
        var lastname: String = "",
        @OneToMany(cascade = [CascadeType.ALL])
        var permissions: MutableList<Permission> = mutableListOf(),
        var updated: Date? = null,
        val created: Date = Date()
)