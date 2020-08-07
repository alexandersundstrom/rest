package rest.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import rest.model.db.User
import java.util.*

interface UserRepository : CrudRepository<User?, String?> {

    @Query("select updated from User where username= :username")
    fun getUpdatedDate(username: String): Date?
}