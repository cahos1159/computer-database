package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(nullable = false,name = "username")
	private String username;
	@Column(nullable = false,name = "password")
	private String password;
	@Column(nullable = false,name = "authority")
	private String authority;
	
	
	public User() {
	}

	public User( String login, String mdp) {
		setUsername(login);
		setPassword(mdp);
		this.authority = "USER";
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String login) {
		this.username = login;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		
		return password;
	}
	public void setPassword(String mdp) {
		
		this.password = mdp;
	}
	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority=authority;
	}
}
