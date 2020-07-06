package rest.model

import javax.persistence.*

@Entity
@Table(name = "person")
class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var firstname: String? = null,
        var lastname: String? = null,
        @OneToOne(cascade = [CascadeType.ALL])
        var phone: Phone? = null
)