package rest.model

import java.util.*

data class UserTO(
        val username: String = "",
        var email: String = "",
        var isTemporaryPassword: Boolean = true,
        var firstname: String = "",
        var lastname: String = "",
        var permissions: MutableList<Permission> = mutableListOf(),
        var updated: Date? = null,
        val created: Date = Date()
)