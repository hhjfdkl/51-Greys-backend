package com.skillstorm.services;

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

import com.skillstorm.misc.StringCutter;
import com.skillstorm.models.Clearance;
import com.skillstorm.models.Employee;
import com.skillstorm.models.Location;
import com.skillstorm.models.Project;
import com.skillstorm.repositories.EmployeeRepository;

@Service
public class EmployeeService 
{

	StringCutter c = new StringCutter();
	
	@Value("${sp-login.username}")
	private String username;
	
	@Value("${sp-login.password}")
	private String password;
	
	@Value("${sp-url}")
	private String url;
	
	@Autowired
	private EmployeeRepository repo;
	
	//CREATE
	public ResponseEntity<Employee> createEmployee(Employee employee)
	{
		//constructs a json via a string with the employee's first/last name and email
		//SP user id is generated based on Unix time
		String req = c	
		.userPostRequest(
				  employee.getFirstName()
				, employee.getLastName()
				, employee.getEmail());
		
		//set our employee's SP id to save in DB based on what comes back from our json
		employee.setSpId(c.findUserId(createUser(req)));
		
		return ResponseEntity
				.status(201)
				.header("Message", "Employee created")
				.body(repo.save(employee));
	}
	
	//to create user in SP
	public String createUser(String user)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, password);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(user, headers);
		ResponseEntity<String> response =
				template.exchange(
						  url + "/Users"
						, HttpMethod.POST
						, entity
						, String.class
						);
		return response.getBody();
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
	
	
	//to handle get requests for user to SP
	public String getUserById(String id)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = 
				template.exchange(
						  url + "/Users/" + id
						, HttpMethod.GET
						, entity
						, String.class
						);
		//if statement for when not a 200 response
		return response.getBody();
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
		//get employee to pull SP ID from backend then get info from SP to send back and update
		String spId = repo.findById(id).get().getSpId();
		
		//when SP id exists, call SP with a put request
		if(spId != null) {
			String user = getUserById(spId);
			String userName = c
					.findUserName(user);
			String body = c
					.userPutRequest(userName, firstName, lastName, email);
			updateUser(spId, body);
		//otherwise, create a user for this employee, then use spId to save it in the DB
		} else {
			String req = c
				.userPostRequest(
						  firstName
						, lastName
						, email);
				spId = c.findUserId(createUser(req));
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
						, spId
						, project
						)
					)
				);
	}
	
	//to handle our request to SP for PUT
	public String updateUser(String id, String body)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, password);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = 
				template.exchange(
						  url + "/Users/" + id
						, HttpMethod.PUT
						, entity
						, String.class
						);
		//if statement for when not 200 response
		return response.getBody();
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
		
		//in case we have a user without SP identity
		if(response.getSpId() != null)
			deleteUser(response.getSpId());
		
		repo.deleteById(id);
		return ResponseEntity
				.status(204)
				.header("Message", "Employee successfully deleted.")
				.body(response);
	}
	
	//to handle deleting SP user
	public String deleteUser(String id)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("spadmin", "admin");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = 
				template.exchange(
						  url + "/Users/" + id
						, HttpMethod.DELETE
						, entity
						, String.class
						);
		return response.getBody();
	}
	
}
