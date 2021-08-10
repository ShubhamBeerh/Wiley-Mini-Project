package com.spring.connections;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connections {
	public Connection makeConnection() throws IOException, SQLException {
		FileInputStream config = new FileInputStream("/Users/shubhambeerh/JavaEE Programs/MiniProject/config.properties");
		Properties prop = new Properties();
		prop.load(config);
		String url= prop.getProperty("url")+prop.getProperty("db");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("Connection Successfully Made!!");
		System.out.println();
		return con;
	}
}
