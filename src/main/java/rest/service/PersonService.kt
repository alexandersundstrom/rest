package rest.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rest.model.Person
import rest.repository.PersonRepository
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class PersonService {
    @Autowired
    var repository: PersonRepository? = null

    var logger: Logger = LoggerFactory.getLogger(PersonService::class.java)

    fun findAll(): List<Person> {
        return StreamSupport.stream<Person>(repository!!.findAll().spliterator(), false)
                .collect(Collectors.toList())
    }

    fun findById(id: Long): Optional<Person?> {
        return repository!!.findById(id)
    }

    fun save(person: Person): Person {
        return repository!!.save(person)
    }

    fun deleteById(id: Long) {
        repository!!.deleteById(id)
    }

    fun findByFirstname(firstname: String?): List<Person?>? {
        return repository!!.findByFirstname(firstname)
    }
}