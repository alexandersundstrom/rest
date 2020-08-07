package rest.exception

import org.springframework.http.HttpStatus

open class UserException(message: String, val status: HttpStatus) : Exception(message)