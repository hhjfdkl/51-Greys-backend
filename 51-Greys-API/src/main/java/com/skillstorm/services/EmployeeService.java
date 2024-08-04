package com.skillstorm.services;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skillstorm.misc.StringManipulator;
import com.skillstorm.models.Clearance;
import com.skillstorm.models.Employee;
import com.skillstorm.models.Location;
import com.skillstorm.models.Project;
import com.skillstorm.repositories.EmployeeRepository;

@Service
public class EmployeeService 
{
	@Value("${sp-login.username}")
	private String username;
	
	@Value("${sp-login.password}")
	private String password;
	
	@Value("${sp.url}" + "${sp.employee-endpoint}")
	private String url;
	
	
	
	@Autowired
	private EmployeeRepository repo;
	
	//CREATE
	public ResponseEntity<Employee> createEmployee(Employee employee)
	{
		//we might need to decide on some check for when an employee creation is invalid
		
		//add method to ping SailPoint here to create a user
		//conditional on 201 response
		ResponseEntity<String> temp = this.createUserSP(
				employee.getFirstName()
				, employee.getLastName()
				, employee.getEmail());
		String empId = StringManipulator.getInstance()
		.findUserId(temp.getBody());
		employee.setId(empId);
		return ResponseEntity
				.status(201)
				.header("Message", "Employee created")
				.body(repo.save(employee));
	}
	
	public ResponseEntity<String> createUserSP(String firstName, String lastName, String email)
	{
		String userReq = StringManipulator.getInstance()
				.userRequest(firstName, lastName, email);
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String authString = Base64.getEncoder()
				.encodeToString(
						(username + ":" + password)
						.getBytes()
						);
		headers.setBasicAuth(authString);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(userReq, headers);
		return template.exchange(url, HttpMethod.POST, entity, String.class);
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
			  String id
			, String firstName
			, String lastName
			, String email
			, String phoneNumber
			, String occupation
			, Clearance clearance
			, Location location
			, String img
			, Project project
			)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find employee specified - try again.")
					.body(null);
		}
		//get request to SP user to check if email there is the same as this one
		//if they don't equal, send a put request to SP		
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
						, project
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
		//send delete request to SP - if not 204, send a 404 SailPoint error
		
		Employee response = repo.findById(id).get();
		repo.deleteById(id);
		return ResponseEntity
				.status(204)
				.header("Message", "Employee successfully deleted.")
				.body(response);
	}
	
}
