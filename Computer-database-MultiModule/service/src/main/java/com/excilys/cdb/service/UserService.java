package com.excilys.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.JpaUserDao;
import com.excilys.cdb.model.User;

@Scope
@Component
public class UserService {
	
	@Autowired
	private JpaUserDao userJdao;
	
	public User getUser(String login,String mdp) {
		SCryptPasswordEncoder hash = new SCryptPasswordEncoder(); 
		return userJdao.findByLoginAndMdp(login,hash.encode(mdp));
	}
	
	public void addUser(User us) {
		SCryptPasswordEncoder hash = new SCryptPasswordEncoder();
		us.setMdp(hash.encode(us.getMdp()));
		userJdao.save(us);
	}
}
