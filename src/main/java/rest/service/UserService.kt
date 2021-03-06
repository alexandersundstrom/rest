package rest.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class UserService {
    @Autowired
    private var repository: UserRepository? = null

    @Autowired
    private var mailService: MailService? = null
    private var logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAll(): List<UserOUT> {
        return StreamSupport.stream<User>(repository!!.findAll().spliterator(), false)
                .map { UserOUT(it) }
                .collect(Collectors.toList())
    }

    fun findById(username: String): Optional<UserOUT> {
        val user = repository!!.findById(username)
        if (user.isPresent) {
            return Optional.of(UserOUT(user.get()))
        }
        return Optional.empty()
    }

    fun getUpdatedDate(username: String): Date? {
        val updatedDate = repository!!.getUpdatedDate(username)
        if (updatedDate != null) {
            //Convert from Timestamp to Date, otherwise comparison is off on milliseconds
            return Date(updatedDate.time)
        }
        return updatedDate
    }

    fun create(userIN: UserIN): UserOUT {
        if (repository!!.existsById(userIN.username)) throw UserException("A user already exists with username: ${userIN.username}", HttpStatus.CONFLICT)

        val temporaryPassword = PswGenerator.temporaryPassword()
        val user = User(userIN).copy(
                created = Date(LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()),
                password = PasswordEncoder.encode(temporaryPassword),
                isTemporaryPassword = true,
                passwordExpires = Date(LocalDateTime.now().plusDays(180).withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()))

        val savedUser = repository!!.save(user)
        mailService!!.sendTemporaryPassword(savedUser, temporaryPassword)
        return UserOUT(savedUser)
    }

    fun save(userIN: UserIN): UserOUT {
        val optional = repository!!.findById(userIN.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${userIN.username}.", HttpStatus.NOT_FOUND)

        val original = optional.get()

        val toSave = User(userIN).copy(
                password = original.password,
                updated = Date(LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()),
                isTemporaryPassword = original.isTemporaryPassword,
                created = original.created,
                failedAttempts = original.failedAttempts,
                passwordExpires = original.passwordExpires
        )

        val updated = repository!!.save(toSave)
        return UserOUT(updated)
    }

    fun deleteById(id: String) {
        if (!repository!!.existsById(id)) throw UserException("No user found with username: $id} ", HttpStatus.NOT_FOUND)
        repository!!.deleteById(id)
    }

    fun changePassword(credentials: ChangePswCredentialsIN) {
        val optional = repository!!.findById(credentials.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${credentials.username}.", HttpStatus.NOT_FOUND)

        val user = optional.get()
        when {
            user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.", HttpStatus.UNAUTHORIZED)
        }
        if (!PasswordEncoder.matches(credentials.oldPsw, user.password)) {
            val copy = user.copy(failedAttempts = user.failedAttempts + 1)
            repository!!.save(copy)
            throw PasswordException("Password doesn't match the old password", HttpStatus.UNAUTHORIZED)
        }

        val copy = user.copy(
                isTemporaryPassword = false,
                password = PasswordEncoder.encode(credentials.newPsw),
                updated = Date(LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()),
                passwordExpires = Date(LocalDateTime.now().plusDays(180).withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli())
        )
        repository!!.save(copy)

    }

    fun login(credentials: PswCredentialsIN): UserOUT {
        val optional = repository!!.findById(credentials.username)
        if (optional.isEmpty) throw UserException("No user found with username: ${credentials.username}.", HttpStatus.NOT_FOUND)

        val user = optional.get()
        when {
            user.passwordExpires.before(Date()) -> throw UserException("Password has expired.", HttpStatus.BAD_REQUEST)
            user.isTemporaryPassword -> throw UserException("Temporary password needs to be changed before logging in.", HttpStatus.BAD_REQUEST)
            user.failedAttempts >= 3 -> throw UserException("To many failed attempts, contact support.", HttpStatus.UNAUTHORIZED)
            !PasswordEncoder.matches(credentials.psw, user.password) -> {
                val copy = user.copy(failedAttempts = user.failedAttempts + 1)
                repository!!.save(copy)
                throw PasswordException("Password doesn't match", HttpStatus.UNAUTHORIZED)
            }
            else -> {
                val copy = user.copy(failedAttempts = 0)
                repository!!.save(copy)
                return UserOUT(user)
            }
        }
    }
}