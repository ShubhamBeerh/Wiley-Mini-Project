package com.spring.employeerec;

public class Employee {
	private int empID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	private String hireDate;
	private String jobID;
	private double salary;
	private double commission;
	private int managerID;
	private int departmentID;
	
	public Employee(int empID, String firstName, String lastName, String email, String phoneNo, String hireDate,
			String jobID, double salary, double commission, int managerID, int departmentID) {
		super();
		this.empID = empID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.hireDate = hireDate;
		this.jobID = jobID;
		this.salary = salary;
		this.commission = commission;
		this.managerID = managerID;
		this.departmentID = departmentID;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", hireDate=" + hireDate + ", jobID=" + jobID + ", salary=" + salary
				+ ", commission=" + commission + ", managerID=" + managerID + ", departmentID=" + departmentID + "] \n";
	}
	
	
}

