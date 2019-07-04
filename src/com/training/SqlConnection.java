package com.training;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SqlConnection {

	
public static Connection getSqlConnection(){
		
		Connection con =null;
		
		
		
		InputStream stream;
		
		try {
			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
			stream = classLoader.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(stream);
			Class.forName(props.getProperty("db.driverClass"));
			con = DriverManager.getConnection(props.getProperty("db.url"),
					props.getProperty("db.userName"),
					props.getProperty("db.passWord"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
	
	
}
