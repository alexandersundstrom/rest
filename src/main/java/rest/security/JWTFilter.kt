package rest.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import rest.exception.TokenException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

//TODO When errors thrown, set appropiate statuscode (401 or 400)
class JWTFilter : Filter {
    var logger: Logger = LoggerFactory.getLogger(JWTFilter::class.java)

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        if (req is HttpServletRequest && req.cookies != null) {
            req.cookies.asList()
                    .stream()
                    .filter() { it.name == "token" }
                    .findFirst()
                    .orElseThrow<TokenException> {
                        throw TokenException("Token cookie not found.")
                    }
                    .let { JWTUtil.validate(it.value) }
        } else {
            throw TokenException("Token cookie not found.")
        }
        chain?.doFilter(req, res)
    }
}