package rest.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        @Column(unique = true)
        val username: String = "",
        val email: String = "",
        var password: String = "",
        var firstname: String = "",
        var lastname: String = "",
        @OneToOne(cascade = [CascadeType.ALL])
        var rights: Rights = Rights(),
        var updated: Date? = null
)
{
    init {
        val created = Date()
    }
}