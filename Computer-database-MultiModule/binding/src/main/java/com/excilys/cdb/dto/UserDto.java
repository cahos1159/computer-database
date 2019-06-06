package com.excilys.cdb.dto;

public class UserDto extends Dto {
	
	
	public UserDto(String id, String login, String mdp) {
		super(id);
		setLogin(login);
		setMdp(mdp);
	}


	private String id;
	private String login;
	private String mdp;
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getMdp() {
		return mdp;
	}


	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	

}
