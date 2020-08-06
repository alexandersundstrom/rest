package rest.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rest.exception.PasswordException
import rest.exception.UserException
import rest.model.db.User
import rest.model.to.ChangePswCredentialsIN
import rest.model.to.PswCredentialsIN
import rest.model.to.UserIN
import rest.model.to.UserOUT
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

    fun findAll(): List<UserOUT> {
        return StreamSupport.stream<User>(repository!!.findAll().spliterator(), false)
                .map { UserOUT(it) }
                .collect(Collectors.toList())
    }

    fun findById(id: String): Optional<UserOUT> {
        val user = repository!!.findById(id)
        if (user.isPresent) {
            return Optional.of(UserOUT(user.get()))
        }
        return Optional.empty()
    }

    fun create(userIN: UserIN): UserOUT {
        //TODO VALIDATE
        if (repository!!.existsById(userIN.username)) throw UserException("A user already exists with username: ${userIN.username} ")

        val temporaryPassword = PswGenerator.temporaryPassword()
        val user = User(userIN).copy(
                created = Date(),
                password = temporaryPassword,
                isTemporaryPassword = true,
                passwordExpires = Date(Instant.now().plus(6, ChronoUnit.MONTHS).toEpochMilli()))

        val savedUser = repository!!.save(user)
        mailService!!.sendTemporaryPassword(savedUser, temporaryPassword)
        return UserOUT(savedUser)
    }

    fun save(userIN: UserIN): UserOUT {
        val optional = repository!!.findById(userIN.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${userIN.username}.")

        val original = optional.get()

        val toSave = User(userIN).copy(
                password = original.password,
                updated = Date(),
                isTemporaryPassword = original.isTemporaryPassword,
                created = original.created,
                failedAttempts = original.failedAttempts,
                passwordExpires = original.passwordExpires
        )

        val updated = repository!!.save(toSave)
        return UserOUT(updated)
    }

    fun deleteById(id: String) {
        if (!repository!!.existsById(id)) throw UserException("No user found with username: $id} ")
        repository!!.deleteById(id)
    }

    fun changePassword(credentials: ChangePswCredentialsIN) {
        val optional = repository!!.findById(credentials.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${credentials.username}.")

        val user = optional.get()
        when {
            user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.")
        }
        if (!PasswordEncoder.matches(credentials.oldPsw, user.password)) {
            val copy = user.copy(failedAttempts = user.failedAttempts + 1)
            repository!!.save(copy)
            throw PasswordException("Password doesn't match the old password")
        }

        val copy = user.copy(
                isTemporaryPassword = false,
                password = PasswordEncoder.encode(credentials.newPsw),
                updated = Date(),
                passwordExpires = Date(Instant.now().plus(6, ChronoUnit.MONTHS).toEpochMilli())
        )
        repository!!.save(copy)

    }

    fun login(credentials: PswCredentialsIN): UserOUT {
        val optional = repository!!.findById(credentials.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${credentials.username}.")

        val user = optional.get()
        when {
            user.isTemporaryPassword -> throw UserException("Temporary password needs to be changed before logging in.")
            user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.")
            !PasswordEncoder.matches(credentials.psw, user.password) -> {
                val copy = user.copy(failedAttempts = user.failedAttempts + 1)
                repository!!.save(copy)
                throw PasswordException("Password doesn't match")
            }
            user.passwordExpires.before(Date()) -> throw UserException("Password has expired.")
            else -> {
                val copy = user.copy(failedAttempts = 0)
                repository!!.save(copy)
                return UserOUT(user)
            }
        }
    }
}