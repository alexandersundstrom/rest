package rest.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Permission(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val permission: PermissionEnum? = null
)

enum class PermissionEnum {
    _CREATE, _READ, _UPDATE, _DELETE
}
