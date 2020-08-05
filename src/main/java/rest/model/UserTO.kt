package rest.model

import java.time.Instant
import java.time.temporal.ChronoUnit
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
        var failedAttempts: Long = 0,
        var passwordExpires: Date? = Date(Instant.now().plus(180, ChronoUnit.DAYS).toEpochMilli())
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
            failedAttempts = user.failedAttempts,
            passwordExpires = user.passwordExpires)
}