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
	@Column(nullable = false,name = "login")
	private String login;
	@Column(nullable = false,name = "mdp")
	private String mdp;
	
	public User(int id, String login, String mdp) {
		setId(id);
		setLogin(login);
		setMdp(mdp);
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMdp() {
		
		return mdp;
	}
	public void setMdp(String mdp) {
		
		this.mdp = mdp;
	}
}
