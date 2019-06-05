package com.excilys.cdb.config.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@ComponentScan({"com.excilys.cdb.persistanceconfig"})
@PropertySource(value = { "classpath:db.properties" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.jdbcAuthentication().dataSource(dataSource)
		 * .usersByUsernameQuery("SELECT id,username,password FROM user WHERE username = ?"
		 * )
		 * .authoritiesByUsernameQuery("SELECT  id,username,password,authority from user where username=?"
		 * ) .passwordEncoder(new BCryptPasswordEncoder());
		 */
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("user")).roles("USER");
	  }
	


	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		 
//		http.csrf().disable().authorizeRequests();
		
		/*
		 * http.csrf().disable().authorizeRequests()
		 * .antMatchers(HttpMethod.POST,"/login").permitAll()
		 * .antMatchers(HttpMethod.POST,"/logine").permitAll()
		 * .antMatchers("/404").permitAll() .antMatchers("/").hasAuthority("USER")
		 * .antMatchers("/computer-add").authenticated()
		 * .antMatchers("/computer-edit").authenticated()
		 * .antMatchers("/login").permitAll() .and() .formLogin() .loginPage("/login")
		 * .loginProcessingUrl("/Loginp") .usernameParameter("username")
		 * .passwordParameter("password") .defaultSuccessUrl("/") .failureUrl("/login")
		 * ;
		 */
		  
		 http.authorizeRequests().anyRequest().authenticated().and().formLogin().permitAll();
		  
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
	
}
