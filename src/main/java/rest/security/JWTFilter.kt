package rest.security

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class JWTFilter : Filter {
    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        //TODO Check JWT and validate that it's right, either filter or throw error
        chain?.doFilter(req, res)
    }
}