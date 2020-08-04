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
        val created: Date = Date(),
        var failedAttempts: Long = 0
) {
    constructor(user: User) : this(
            username = user.username,
            email = user.email,
            isTemporaryPassword = user.isTemporaryPassword,
            firstname = user.firstname,
            lastname = user.lastname,
            permissions = user.permissions,
            updated = user.updated,
            created = user.created,
            failedAttempts = user.failedAttempts)
}