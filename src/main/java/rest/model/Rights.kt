package rest.model

import javax.persistence.*

@Entity
@Table(name = "rights")
class Rights(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var hasRead: Boolean = false,
        var hasCreate: Boolean = false,
        var hasUpdate: Boolean = false,
        var hasDelete: Boolean = false
)