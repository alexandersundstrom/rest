package rest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rest.database.UserRepository
import rest.model.User
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class UserService {
    @Autowired
    var repository: UserRepository? = null

    fun findAll(): List<User> {
        return StreamSupport.stream<User>(repository!!.findAll().spliterator(), false)
                .collect(Collectors.toList())
    }

    fun findById(id: String): Optional<User?> {
        return repository!!.findById(id)
    }

    fun save(user: User): User {
        return repository!!.save(user)
    }

    fun deleteById(id: String) {
        repository!!.deleteById(id)
    }
}