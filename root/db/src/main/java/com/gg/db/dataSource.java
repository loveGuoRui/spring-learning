package com.gg.db;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.Validator;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorDefinition;

public class dataSource extends DataSource{
	static PoolConfiguration poolConfiguration;
	
	private String driverClassName;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String initialSize;
	
	private String maxActive;
	
	private String maxIdle;
	
	private String minIdle;
	
	private String validationQuery;
	
	private String testWhileIdle;
	
	private String name;
    
	public dataSource() {
		super(poolConfiguration);
		poolConfiguration.setDriverClassName(this.driverClassName);
		
		
        
    }
}
