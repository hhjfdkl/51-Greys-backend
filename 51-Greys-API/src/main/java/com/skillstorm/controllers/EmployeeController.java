package com.skillstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Employee;
import com.skillstorm.services.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*") 	//leaving this here for now. Will review Security later
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	
	//CREATE
	@PostMapping
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee)
	{
		return service.createEmployee(employee);
	}
	
	//READ
	@GetMapping
	public ResponseEntity<Iterable<Employee>> getAllEmployees()
	{
		return service.getAllEmployees();
	}
	
		//TODO: make method that grabs employee iterable by string
		//Query from the API will grab it, not an endpoint
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id)
	{
		return service.getEmployeeById(id);
	}
	
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(
			  @PathVariable int id
			, @RequestBody Employee employee
			)
	{
		return service.updateEmployee(
				  id
				, employee.getFirstName()
				, employee.getLastName()
				, employee.getEmail()
				, employee.getPhoneNumber()
				, employee.getOccupation()
				, employee.getClearance()
				, employee.getImg()
		//		, employee.getProjects()
				);
	}
	
	//DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteDepartment(@PathVariable int id)
	{
		return service.deleteEmployeeById(id);
	}
}
