package rest.exception

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UserException::class)
    protected fun handleUserException(e: UserException): ResponseEntity<String> = ResponseEntity
            .status(e.status)
            .body(e.message)

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<String> = ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Please contact admin")


}