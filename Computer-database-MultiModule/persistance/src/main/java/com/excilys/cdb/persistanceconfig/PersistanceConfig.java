package com.excilys.cdb.persistanceconfig;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan
public class PersistanceConfig {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(HikariDataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("com.excilys.cdb.model");
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
}
	
	@Bean
    public HikariDataSource dataSource(Environment environement) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setMaximumPoolSize(10);
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl(environement.getRequiredProperty("jdbcUrl"));
		dataSource.setUsername(environement.getRequiredProperty("dataSource.user"));
		dataSource.setPassword(environement.getRequiredProperty("dataSource.password"));
		dataSource.addDataSourceProperty("useSSL",false);
		dataSource.addDataSourceProperty("allowPublicKeyRetrieval",true);
		dataSource.setMaxLifetime(600_000L);
		dataSource.setIdleTimeout(300_000L);
		dataSource.setLeakDetectionThreshold(300_000L);
		dataSource.setConnectionTimeout(10_000L);
        return dataSource;
	}

}
