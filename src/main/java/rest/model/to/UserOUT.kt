package rest.model.to

import com.fasterxml.jackson.annotation.JsonProperty
import rest.model.db.Permission
import rest.model.db.User
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

data class UserOUT(
        val username: String = "",
        val email: String = "",
        @get:JsonProperty("isTemporaryPassword")
        val isTemporaryPassword: Boolean = true,
        val firstname: String = "",
        val lastname: String = "",
        val permissions: MutableList<Permission> = mutableListOf(),
        val updated: Date? = null,
        val created: Date = Date(),
        val failedAttempts: Long = 0,
        val passwordExpires: Date = Date(Instant.now().plus(180, ChronoUnit.DAYS).toEpochMilli())
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