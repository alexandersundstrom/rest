package rest.exception

import org.springframework.http.HttpStatus

open class TokenException(message: String, val status: HttpStatus) : Exception(message)