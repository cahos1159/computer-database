package com.excilys.cdb.database;

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
	
	public void initPool() {
		setUpHikari();
	}
	
	public void closePool() {
		this.hikariDataSource.close();
	}
	
	public Connection getConnection() throws SQLException {
		if(this.hikariDataSource == null) {
			setUpHikari();
		}
		return this.hikariDataSource.getConnection();
	}
}
