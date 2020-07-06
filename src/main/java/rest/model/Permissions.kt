package rest.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Permissions(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val permission: Permission? = null
)

enum class Permission {
    _CREATE, _READ, _UPDATE, _DELETE
}
