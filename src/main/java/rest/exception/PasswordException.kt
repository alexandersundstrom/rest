package rest.exception

import org.springframework.http.HttpStatus

class PasswordException(message: String, status: HttpStatus) : UserException(message, status)