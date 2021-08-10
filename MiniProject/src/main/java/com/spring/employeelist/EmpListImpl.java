package com.spring.employeelist;

import java.util.HashMap;
import java.util.Map;

import com.spring.employeerec.Employee;

public class EmpListImpl implements EmpList {
//	private static EmpListImpl singInst = null;
	Map<Integer,Employee> map;
	
	public EmpListImpl() {
		map=new HashMap<>();
	}
	
	@Override
	public void addToList(Employee emp) {
		// TODO Auto-generated method stub
		map.put(emp.getEmpID(),emp);
	}

	@Override
	public Map<Integer,Employee> getList() {
		// TODO Auto-generated method stub
		return map;
	}
	
//	public static EmpListImpl singleEmpInst() {
//		if(singInst==null)
//			singInst = new EmpListImpl();
//		return singInst;
//	}

}

