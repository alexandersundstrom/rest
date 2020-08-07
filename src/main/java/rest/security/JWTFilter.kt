package rest.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import rest.exception.TokenException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter : Filter {
    private var logger: Logger = LoggerFactory.getLogger(JWTFilter::class.java)

    @Autowired
    private var jwtService: JWTService? = null

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        req as HttpServletRequest
        try {
            if (req.cookies != null) {
                req.cookies.asList()
                        .stream()
                        .filter() { it.name == "token" }
                        .findFirst()
                        .orElseThrow<TokenException> {
                            throw TokenException("Token cookie not found.", HttpStatus.UNAUTHORIZED)
                        }
                        .let {
                            jwtService!!.validateToken(it.value)
                            val user = jwtService!!.validateTokenAndGetUser(it.value)
                            req.setAttribute("user", user)
                        }
            } else {
                throw TokenException("Token cookie not found.", HttpStatus.UNAUTHORIZED)
            }
        } catch (e: Exception) {
            //Needed as RestExceptionHandler only can be used by @RestController classes
            logger.warn(e.message, e) //TODO Remove if loggs when error thrown in JWTSERVICE
            res as HttpServletResponse
            res.sendError(HttpStatus.UNAUTHORIZED.value(), e.message)
        }
        chain?.doFilter(req, res)
    }
}