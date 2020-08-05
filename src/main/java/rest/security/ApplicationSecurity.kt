package rest.security

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Profile("dev", "prod")
@Configuration
open class ApplicationSecurity : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**")
    }

    @Bean
    open fun jwtFilter(): FilterRegistrationBean<JWTFilter>? {
        val registrationBean: FilterRegistrationBean<JWTFilter> = FilterRegistrationBean<JWTFilter>()
        registrationBean.filter = JWTFilter()
        registrationBean.addUrlPatterns("/user/*", "/auth/password/*", "/auth/update/*")
        return registrationBean
    }
}