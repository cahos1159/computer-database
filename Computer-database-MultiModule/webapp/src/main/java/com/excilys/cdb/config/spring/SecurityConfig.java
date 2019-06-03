package com.excilys.cdb.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.savedrequest.RequestCache;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	public SecurityConfig() {
		super(true);
	}
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		  http.sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .csrf()
          .disable();
		  http.authorizeRequests()
          .antMatchers("/login").permitAll()
          .anyRequest().authenticated()
		  ;
		  http.exceptionHandling()
		    .and()
		    .anonymous()
		    .and()
		    .servletApi()
		    .and()
		    .headers().cacheControl();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
	
}
