package com.excilys.cdb.DataBase;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Scope

public class DataBaseAccess {
	
	private HikariDataSource hikariDataSource;
	String configFile = "/db.properties";
	
	public DataBaseAccess() {}
	
	private void setUpHikari() {
		System.out.println("Création hikari config");
		final HikariConfig hikariConfig = new HikariConfig(configFile);
		System.out.println("Hikari config Créé");
		
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.addDataSourceProperty("useSSL",false);
		hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval",true);
		hikariConfig.setMaxLifetime(600_000L);
		hikariConfig.setIdleTimeout(300_000L);
		hikariConfig.setLeakDetectionThreshold(300_000L);
		hikariConfig.setConnectionTimeout(10_000L);
		
		this.hikariDataSource = new HikariDataSource(hikariConfig);
		System.out.println("Hikari config Setup Done");
		}
	
	public void initPool() {
		setUpHikari();
	}
	
	public void closePool() {
		this.hikariDataSource.close();
	}
	
	public Connection getConnection() throws SQLException {
		if(this.hikariDataSource == null) {
			System.out.println("Not connected");
			setUpHikari();
		}
		return this.hikariDataSource.getConnection();
	}
}
