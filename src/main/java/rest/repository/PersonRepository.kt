package rest.repository

import org.springframework.data.repository.CrudRepository
import rest.model.Person

interface PersonRepository : CrudRepository<Person?, Long?> {
    fun findByFirstname(firstname: String?): List<Person?>?
}