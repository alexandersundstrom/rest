package rest.repository

import org.springframework.data.repository.CrudRepository
import rest.model.db.User

interface UserRepository : CrudRepository<User?, String?>