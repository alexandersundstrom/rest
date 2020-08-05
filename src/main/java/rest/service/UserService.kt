package rest.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rest.exception.PasswordException
import rest.exception.UserException
import rest.mail.MailService
import rest.model.ChangePswCredentials
import rest.model.PswCredentials
import rest.model.User
import rest.model.UserTO
import rest.repository.UserRepository
import rest.util.PasswordEncoder
import rest.util.PswGenerator
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class UserService {
    @Autowired
    var repository: UserRepository? = null

    @Autowired
    var mailService: MailService? = null;

    var logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAll(): List<User> {
        return StreamSupport.stream<User>(repository!!.findAll().spliterator(), false)
                .collect(Collectors.toList())
    }

    fun findById(id: String): Optional<User?> {
        return repository!!.findById(id)
    }

    fun create(user: User): User {
        //VALIDATE
        val temporaryPassword = PswGenerator.temporaryPassword()
        user.password = PasswordEncoder.encode(temporaryPassword)
        user.isTemporaryPassword = true
        user.passwordExpires = Date(Instant.now().plus(6, ChronoUnit.MONTHS).toEpochMilli())

        val savedUser = repository!!.save(user)
        mailService!!.sendTemporaryPassword(savedUser, temporaryPassword)
        return savedUser
    }

    fun save(user: User): User {
        //VALIDATE
        user.updated = Date()
        //fetch password and set
        return repository!!.save(user)
    }

    fun deleteById(id: String) {
        repository!!.deleteById(id)
    }

    fun changePassword(credentials: ChangePswCredentials) {
        val optional = findById(credentials.username)
        if (optional.isPresent) {
            val user = optional.get()
            when {
                user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.")
            }
            if (!PasswordEncoder.matches(credentials.oldPsw, user.password)) {
                user.failedAttempts++
                save(user)
                throw PasswordException("Password doesn't match the old password")
            }

            if (user.isTemporaryPassword) {
                user.isTemporaryPassword = false
            }
            user.password = PasswordEncoder.encode(credentials.newPsw)
            user.updated = Date()
            user.passwordExpires = Date(Instant.now().plus(6, ChronoUnit.MONTHS).toEpochMilli())
            save(user)
        }
    }

    fun login(credentials: PswCredentials): UserTO {
        val optional = findById(credentials.username)
        if (optional.isPresent) {
            val user = optional.get()
            when {
                user.isTemporaryPassword -> throw UserException("Temporary password needs to be changed before logging in.")
                user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.")
                !PasswordEncoder.matches(credentials.psw, user.password) -> {
                    user.failedAttempts++
                    save(user)
                    throw PasswordException("Password doesn't match")
                }
                user.passwordExpires.before(Date()) -> throw UserException("Password has expired.")
                else -> {
                    user.failedAttempts = 0
                    save(user)
                    return UserTO(user)
                }
            }
        } else {
            throw UserException("A user with username ${credentials.username} could not be found.")
        }
    }
}