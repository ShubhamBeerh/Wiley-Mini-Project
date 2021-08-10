package com.spring.miniproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.connections.Connections;
import com.spring.executequeries.Execution;

@SpringBootApplication
public class MiniProjectApplication {

	public static void main(String[] args) throws SQLException, IOException, ParseException {
		SpringApplication.run(MiniProjectApplication.class, args);
		ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
		Connections connect = new Connections();
		Connection con = connect.makeConnection();
		Statement st = con.createStatement();
		String query = getPropertyFile().getProperty("q1");
		Execution execute = new Execution(app);
		int choice;
		Scanner sc = new Scanner(System.in);
		try {
			do {
				showMenu();
				choice = sc.nextInt();
				switch(choice) {
					case 1 :execute.executeDisplay(st,query); break;
					case 2 :execute.executeSearch(getPropertyFile(), con); break;
					case 3 :execute.executeInsertion(getPropertyFile(),con); break;
					case 4 :execute.executeUpdation(getPropertyFile(),con); break;
					default:System.out.println("Exitting Now. . . Have a Nice Day ! :) ");System.exit(0); break;
					}
				}while(true);
			}
		finally {
			sc.close();
		}
	}
	public static Properties getPropertyFile() throws IOException {
		FileInputStream queryFile = new FileInputStream("/Users/shubhambeerh/JavaEE Programs/MiniProject/query.properties");
		Properties queryProp = new Properties();
		queryProp.load(queryFile);
		return queryProp;
		
	}
	
	public static void showMenu() {
		System.out.println("****** WELCOME MENU ******");
		System.out.println("1. Display the Records");
		System.out.println("2. Search for a Record");
		System.out.println("3. Insert Records(s)");
		System.out.println("4. Update a Record");
		System.out.println("5. Exit");
	}

}
