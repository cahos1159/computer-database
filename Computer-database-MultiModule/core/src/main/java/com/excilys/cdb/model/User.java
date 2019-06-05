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
	
	
	public User( String login, String mdp) {
		setLogin(login);
		setMdp(mdp);
		this.authority = "USER";
		
	}
	
	public String getLogin() {
		return username;
	}
	public void setLogin(String login) {
		this.username = login;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMdp() {
		
		return password;
	}
	public void setMdp(String mdp) {
		
		this.password = mdp;
	}
}
