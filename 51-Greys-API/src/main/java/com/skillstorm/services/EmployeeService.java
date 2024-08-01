package com.skillstorm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Clearance;
import com.skillstorm.models.Employee;
import com.skillstorm.models.Location;
import com.skillstorm.models.Project;
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
	
	
	public ResponseEntity<Iterable<Employee>> getEmployeesByName(String name)
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled employees by name provided")
				.body(repo.findEmployeesByName(name));
	}
	
	public ResponseEntity<Employee> getEmployeeById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find employee by ID provided - try again")
					.body(null);
		}
		return ResponseEntity
				.status(200)
				.header("Message", "Successfully pulled employee by ID.")
				.body(repo.findById(id).get());
	}
	
	
	//UPDATE
	public ResponseEntity<Employee> updateEmployee(
			  int id
			, String firstName
			, String lastName
			, String email
			, String phoneNumber
			, String occupation
			, Clearance clearance
			, Location location
			, String img
			, List<Project> projects
			)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find employee specified - try again.")
					.body(null);
		}
		return ResponseEntity
				.status(200)
				.header("Message", "Employee successfully updated")
				.body(repo.save(new Employee(
						  id
						, firstName
						, lastName
						, email
						, phoneNumber
						, occupation
						, clearance
						, location
						, img
						, projects
						)
					)
				);
	}
	
	
	//DELETE
	public ResponseEntity<Employee> deleteEmployeeById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find employee specified - try again.")
					.body(null);
		}
		Employee response = repo.findById(id).get();
		repo.deleteById(id);
		return ResponseEntity
				.status(200)
				.header("Message", "Employee successfully deleted.")
				.body(response);
	}
	
}
