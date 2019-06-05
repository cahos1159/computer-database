package com.excilys.cdb.config.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.cdb.service.UserService;

@EnableWebSecurity
@Configuration
@ComponentScan({"com.excilys.cdb.persistanceconfig"})
@PropertySource(value = { "classpath:db.properties" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserService userServ;
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
		System.out.println("configure");
	    auth.userDetailsService(userServ);
	}

	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		 
		
		
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/login/register").permitAll()
				.antMatchers("/").authenticated()
				.antMatchers("/computer-add").authenticated()
				.antMatchers("/computer-edit").authenticated()
				.antMatchers("/login").permitAll()
				.and()
				.formLogin().loginPage("/login")
				.loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/").failureUrl("/loginerr");
		  
//		 http.authorizeRequests().anyRequest().authenticated().and().formLogin().permitAll();
		  
    }
	
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
