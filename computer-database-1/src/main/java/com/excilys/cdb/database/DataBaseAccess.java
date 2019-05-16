package com.excilys.cdb.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DataBaseAccess {
	
	private HikariDataSource hikariDataSource;
	String configFile ;
	 
	public DataBaseAccess(String test) {
		this.configFile =  "/testdb.properties";
		setUpHikariTest();
	}
	
	public DataBaseAccess() {
		this.configFile = "/db.properties";
		setUpHikari();
	}
	
	
	private void setUpHikari() {

		final HikariConfig hikariConfig = new HikariConfig(configFile);

		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.addDataSourceProperty("useSSL",false);
		hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval",true);
		hikariConfig.setMaxLifetime(600_000L);
		hikariConfig.setIdleTimeout(300_000L);
		hikariConfig.setLeakDetectionThreshold(300_000L);
		hikariConfig.setConnectionTimeout(10_000L);
		this.hikariDataSource = new HikariDataSource(hikariConfig);

		}
	
	private void setUpHikariTest() {
		System.out.println("class Name");
		HikariConfig hikariConfig = new HikariConfig(configFile);
		hikariConfig.setDriverClassName("org.h2.Driver");
		System.out.println("class Name"+hikariConfig.getDriverClassName());
		this.hikariDataSource = new HikariDataSource(hikariConfig);

		}
	

	
	public void closePool() {
		this.hikariDataSource.close();
	}
	
	public Connection getConnection() throws SQLException {
		return this.hikariDataSource.getConnection();
	}
}
