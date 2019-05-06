package com.excilys.cdb.DataBase;

public class DataBaseCredentials {
	private String url;
	private String dbUser ;
	private String dbPass ;
	
	public DataBaseCredentials(String url,String dbUser, String dbPass){
		this.url = url;
		this.dbUser = dbUser;
		this.dbPass = dbPass;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getUser() {
		return this.dbUser;
	}
	
	public String getPass() {
		return this.dbPass;
	}
}
