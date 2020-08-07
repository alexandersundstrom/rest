package rest.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Profile("dev", "prod")
@Configuration
open class ApplicationSecurity : WebSecurityConfigurerAdapter() {

    @Autowired
    private var jwtFilter: JWTFilter? = null

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**")
    }

    @Bean
    open fun jwtFilter(): FilterRegistrationBean<JWTFilter>? {
        val registrationBean: FilterRegistrationBean<JWTFilter> = FilterRegistrationBean()
        registrationBean.filter = jwtFilter!!
        registrationBean.addUrlPatterns("/user/*", "/person/*", "/auth/updateToken/*")
        return registrationBean
    }
}