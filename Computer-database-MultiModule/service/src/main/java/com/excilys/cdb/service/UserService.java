package com.excilys.cdb.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.carrotsearch.hppc.ObjectObjectScatterMap;
import com.excilys.cdb.dao.JpaUserDao;
import com.excilys.cdb.model.User;

@Scope
@Component
public class UserService implements UserDetailsService {
	
	@Autowired
	private JpaUserDao userJdao;
	

	
	public void addUser(User us) {
		BCryptPasswordEncoder hash = new BCryptPasswordEncoder();
		System.out.println("coucou");
		us.setPassword(hash.encode(us.getPassword()));
		userJdao.save(us);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		final User user = userJdao.findByUsername(username);
		if(Objects.isNull(user)) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(user);
		
	}
}
