package com.mitchelltsutsulis.updown.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        // TODO: Implement security with token based auth (maybe JWT?)
        web.ignoring().antMatchers(HttpMethod.GET).antMatchers(HttpMethod.POST)
    }
}
