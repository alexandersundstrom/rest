package rest.model.to

import com.fasterxml.jackson.annotation.JsonProperty
import rest.model.db.Permission
import rest.model.db.User
import java.sql.Timestamp

data class UserOUT(
        val username: String,
        val email: String,
        @get:JsonProperty("isTemporaryPassword")
        val isTemporaryPassword: Boolean,
        val firstname: String,
        val lastname: String,
        val permissions: MutableList<Permission>,
        val updated: Timestamp?,
        val created: Timestamp,
        val failedAttempts: Long,
        val passwordExpires: Timestamp
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