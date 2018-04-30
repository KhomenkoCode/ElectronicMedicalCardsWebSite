package com.khomenkocode.graduationproject.config;

import java.util.Properties;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	 @Value("${jdbc.driverClassName}")
	    private String driver;
	    @Value("${jdbc.url}")
	    private String url;
	    @Value("${jdbc.username}")
	    private String username;
	    @Value("${jdbc.password}")
	    private String password;
	    @Value("${hibernate.dialect}")
	    private String dialect;
	    @Value("${hibernate.hbm2ddl.auto}")
	    private String hbm2ddlAuto;
	    @Value("${hibernate.show_sql}")
	    private String showsql;

	    @Value("${entitymanager.packagesToScan}")
	    private String packagesToScan;
	
 
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  /**
   * Declare the JPA entity manager factory.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactory =
        new LocalContainerEntityManagerFactoryBean();
    
    entityManagerFactory.setDataSource(dataSource);
    
    // Classpath scanning of @Component, @Service, etc annotated class
    entityManagerFactory.setPackagesToScan(packagesToScan);
    
    // Vendor adapter
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
    
    // Hibernate properties
    Properties additionalProperties = new Properties();
    additionalProperties.put(
        "hibernate.dialect", dialect);
    additionalProperties.put(
        "hibernate.show_sql",showsql);
    additionalProperties.put(
        "hibernate.hbm2ddl.auto", hbm2ddlAuto);
    entityManagerFactory.setJpaProperties(additionalProperties);
    
    return entityManagerFactory;
  }

  /**
   * Declare the transaction manager.
   */
  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = 
        new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
        entityManagerFactory.getObject());
    return transactionManager;
  }
  
  /**
   * PersistenceExceptionTranslationPostProcessor is a bean post processor
   * which adds an advisor to any bean annotated with Repository so that any
   * platform-specific exceptions are caught and then rethrown as one
   * Spring's unchecked data access exceptions (i.e. a subclass of 
   * DataAccessException).
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  // Private fields
  
  @Autowired
  private DataSource dataSource;

  @Autowired
  private LocalContainerEntityManagerFactoryBean entityManagerFactory;

}