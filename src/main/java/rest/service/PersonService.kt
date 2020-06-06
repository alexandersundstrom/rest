package rest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rest.database.PersonRepository
import rest.model.Person
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class PersonService {
    @Autowired
    var repository: PersonRepository? = null

    fun findAll(): List<Person> {
//        return repository!!.findAll()
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