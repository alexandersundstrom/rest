package rest.model.to

import rest.model.db.Permission
import rest.model.db.User

data class UserIN(
        val username: String,
        val email: String,
        val firstname: String,
        val lastname: String,
        val permissions: MutableList<Permission> = mutableListOf()
) {
    constructor(user: User) : this(
            username = user.username,
            email = user.email,
            firstname = user.firstname,
            lastname = user.lastname,
            permissions = user.permissions)
}