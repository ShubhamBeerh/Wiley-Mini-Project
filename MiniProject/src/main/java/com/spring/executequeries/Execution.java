package com.spring.executequeries;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import com.spring.employeelist.EmpList;
import com.spring.employeelist.EmpListImpl;
import com.spring.employeerec.Employee;

public class Execution {
	ApplicationContext app;
	EmpList imp;
	public Execution(ApplicationContext app) {
		this.app=app;
	}
	public void executeDisplay(Statement st,String query) throws SQLException{
		ResultSet rs = st.executeQuery(query);
		imp = (EmpList) app.getBean("emplistimpl");
		while(rs.next()) {
			Employee emp = new Employee(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8),rs.getDouble(9),rs.getInt(10),rs.getInt(11));
			imp.addToList(emp);
		}
		System.out.println(imp.getList());
	}
	
	public void executeSearch(Properties queryProp,Connection con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Search Options : 1)ID \n 2)Name \n 3)Location \n 4)Department \n 5)Manager \n 6)Country");
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			System.out.println("Enter ID: ");
			int checkID = sc.nextInt();
			System.out.println(imp.getList().get(checkID));
			break;
		case 2:
			System.out.println("Enter Name: ");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.println("Name entered: "+name);
			getPreparedString(queryProp,con,name,3);
			break;
		case 3:
			System.out.println("Enter Location : ");
			int location = sc.nextInt();
			getPreparedInt(queryProp,con,location,4);
			break;
		case 4:
			System.out.println("Enter Department : ");
			sc.nextLine();
			String department = sc.nextLine();
			getPreparedString(queryProp,con,department,5);
			break;
		case 5:
			System.out.println("Enter Manager ID : ");
			int managerID = sc.nextInt();
			getPreparedInt(queryProp,con,managerID,6);
			break;
		case 6:
			System.out.println("Enter Country : ");
			sc.nextLine();
			String country= sc.nextLine();
			getPreparedString(queryProp,con,country,7);
			break;
		}
	}
	
	public void executeInsertion(Properties queryProp, Connection con) throws SQLException, ParseException {
		String query = queryProp.getProperty("q8");
		PreparedStatement prep = con.prepareStatement(query);
		HashMap<Integer,Employee> hashMap = createObjects();
		for(Map.Entry<Integer,Employee> getEmp: hashMap.entrySet()) {
			prep.setInt(1,getEmp.getKey());
			prep.setString(2,getEmp.getValue().getFirstName());
			prep.setString(3,getEmp.getValue().getLastName());
			prep.setString(4,getEmp.getValue().getEmail());
			prep.setString(5,getEmp.getValue().getPhoneNo());
			prep.setString(6,getEmp.getValue().getHireDate());
			prep.setString(7,getEmp.getValue().getJobID());
			prep.setDouble(8,getEmp.getValue().getSalary());
			prep.setDouble(9,getEmp.getValue().getCommission());
			prep.setInt(10,getEmp.getValue().getManagerID());
			prep.setInt(11,getEmp.getValue().getDepartmentID());
			prep.addBatch();
		}
		int[] count = prep.executeBatch();
		int rowCount=0;
		for(int c: count) {
			if(c!=0)
				rowCount++;
		}
		System.out.println(rowCount+" Row(s) Affected!!");
	}
	
	public void executeUpdation(Properties queryProp, Connection con) throws IOException, SQLException {
		Scanner sc = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String query = queryProp.getProperty("q9");
		System.out.println("Enter no. of fields to update");
		int noOfFields = sc.nextInt();
		String[] key = new String[noOfFields];
		Object[] value = new Object[noOfFields];
		for(int i=0;i<noOfFields;i++) {
			System.out.println("Enter field to update: ");
			do {
			key[i]=reader.readLine();
			if(!(checkForString(key[i])||checkForInt(key[i])||checkForDouble(key[i])))
				System.out.println("Wrong Field Entered! Please enter correct field!");
			}while(!(checkForString(key[i])||checkForInt(key[i])||checkForDouble(key[i])));
			System.out.println("Enter value to be put: ");
			if(checkForString(key[i])) {
				value[i]=reader.readLine();
			}
			else if(key[i].equalsIgnoreCase("employee_id")||key[i].equalsIgnoreCase("manager_id")||key[i].equalsIgnoreCase("department_id"))
				value[i]=sc.nextInt();
			else
				value[i]=sc.nextDouble();
			}
		System.out.println("Enter Employee ID against which you wanna make the update?");
		int empID = sc.nextInt();
		StringBuffer field = new StringBuffer();
		for(int i=0;i<key.length-1;i++) {
			if(checkForString(key[i]))
				field.append(key[i]).append(" = '").append(value[i]).append("'").append(",");
			else
				field.append(key[i]).append(" = ").append(value[i]).append(",");
		}
		if(checkForString(key[key.length-1]))
			field.append(key[key.length-1]).append(" = '").append(value[value.length-1]).append("'");
		else
			field.append(key[key.length-1]).append(" = ").append(value[value.length-1]);
		
		query = MessageFormat.format((String)queryProp.getProperty("q9"),field.toString());
		PreparedStatement prep = con.prepareStatement(query);
		prep.setInt(1,empID);
		System.out.println(prep.toString().substring(prep.toString().indexOf("update")));
		int rowCount = prep.executeUpdate();
		System.out.println(rowCount+" Row(s) Updated Successfully!");
		
	}
	
//------------------------------------- UTILITIES BEGIN -----------------------------------------------------
	
	public HashMap<Integer,Employee> createObjects() throws ParseException{
		HashMap<Integer,Employee> map = new HashMap<Integer,Employee>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of employees to insert: ");
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
		int numEmp = sc.nextInt();
		for(int i=0;i<numEmp;i++) {
			System.out.println("Enter Employee ID: ");
			int empID = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter First Name: ");
			String firstName = sc.nextLine();
			System.out.println("Enter Last Name: ");
			String lastName = sc.nextLine();
			System.out.println("Enter Email: ");
			String email = sc.nextLine();
			System.out.println("Enter PhoneNumber: ");
			String phoneNo = sc.nextLine();
			System.out.println("Enter Hire Date");
			String hireDate = sc.nextLine();
			System.out.println("Enter JobID : ");
			String jobID = sc.nextLine();
			System.out.println("Enter Salary: ");
			double salary = sc.nextDouble();
			System.out.println("Enter Commission Percent: ");
			double compct = sc.nextDouble();
			System.out.println("Enter Manager ID: ");
			int mgID = sc.nextInt();
			System.out.println("Enter Department ID: ");
			int deptID= sc.nextInt();
			Employee employee = new Employee(empID,firstName,lastName,email,phoneNo,hireDate,jobID,salary,compct,mgID,deptID);
			map.put(empID,employee);
		}
		return map;
	}
	
	public void getPreparedString(Properties queryProp,Connection con,String value,int queryNumber) throws SQLException {
		String query = queryProp.getProperty("q"+queryNumber);
		PreparedStatement prep = con.prepareStatement(query);
		prep.setString(1, value);
		ResultSet rs = prep.executeQuery();
		while(rs.next()) {
			System.out.println(imp.getList().get(rs.getInt(1)));
		}
	}
	
	public void getPreparedInt(Properties queryProp,Connection con,int value,int queryNumber) throws SQLException {
		String query = queryProp.getProperty("q"+queryNumber);
		PreparedStatement prep = con.prepareStatement(query);
		prep.setInt(1,value);
		ResultSet rs = prep.executeQuery();
		while(rs.next()) {
			System.out.println(imp.getList().get(rs.getInt(1)));
		}
	}
	
	public static boolean checkForString(String str) {
		if(str.equalsIgnoreCase("first_name")||str.equalsIgnoreCase("last_name")||str.equalsIgnoreCase("email")||str.equalsIgnoreCase("phone_number")||str.equalsIgnoreCase("hire_date")||str.equalsIgnoreCase("job_id"))
			return true;
		return false;
	}
	
	public static boolean checkForInt(String str) {
		if(str.equalsIgnoreCase("employee_id")||str.equalsIgnoreCase("manager_id")||str.equalsIgnoreCase("department_id"))
			return true;
		return false;
	}
	
	public static boolean checkForDouble(String str) {
		if(str.equalsIgnoreCase("salary")||str.equalsIgnoreCase("commission_pct"))
			return true;
		return false;
	}
}
