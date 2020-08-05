package rest.repository

import org.springframework.data.repository.CrudRepository
import rest.model.User

interface UserRepository : CrudRepository<User?, String?>