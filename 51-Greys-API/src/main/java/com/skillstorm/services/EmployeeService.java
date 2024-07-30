package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Employee;
import com.skillstorm.repositories.EmployeeRepository;

@Service
public class EmployeeService 
{

	@Autowired
	private EmployeeRepository repo;
	
	//CREATE
	public ResponseEntity<Employee> createEmployee(Employee employee)
	{
		//we might need to decide on some check for when an employee creation is invalid
		
		
		return ResponseEntity
				.status(201)
				.header("Message", "Employee created")
				.body(repo.save(employee));
	}
	
	
	//READ
	public ResponseEntity<Iterable<Employee>> getAllEmployees()
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled all employees.")
				.body(repo.findAll());
	}
	//get list of employees by name? (make in EmployeeRepository)
	
	
	//UPDATE
	
	
	//DELETE
	
	
	
	
}
