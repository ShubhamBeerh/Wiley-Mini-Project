package com.spring.employeelist;


import java.util.Map;

import com.spring.employeerec.Employee;

public interface EmpList {

	void addToList(Employee emp);
	Map<Integer, Employee> getList();
}

