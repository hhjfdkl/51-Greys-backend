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

	@Value("${sp-login.username}")
	private String username;
	
	@Value("${sp-login.password}")
	private String password;
	
	@Autowired
	private EmployeeRepository repo;
	
	//CREATE
	public ResponseEntity<Employee> createEmployee(Employee employee)
	{
		//we might need to decide on some check for when an employee creation is invalid
		String req = StringCutter.getInstance()
		.userPostRequest(
				  employee.getFirstName()
				, employee.getLastName()
				, employee.getEmail());
		//should confirm we actually posted to sailpoint here
		employee.setSpId(StringCutter.getInstance().findUserId(createUser(req)));
		
		return ResponseEntity
				.status(201)
				.header("Message", "Employee created")
				.body(repo.save(employee));
	}
	
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
						"http://172.174.175.230:8080/identityiq/scim/v2/Users"
						, HttpMethod.POST
						, entity
						, String.class
						);
		//if statement for when not a 201 response
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
	
	
	public String getUserById(String id)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = 
				template.exchange(
						  "http://172.174.175.230:8080/identityiq/scim/v2/Users/" + id
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
		//needs exceptions handling / if statement
		String spId = repo.findById(id).get().getSpId();
		//same for here when we pull from SP (in case nothing comes back)
		String user = getUserById(spId);
		String userName = StringCutter.getInstance()
				.findUserName(user);
		String body = StringCutter.getInstance()
				.userPutRequest(userName, firstName, lastName, email);
		//check here as well
		updateUser(spId, body);
		
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
						  "http://172.174.175.230:8080/identityiq/scim/v2/Users/" + id
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
		deleteUser(response.getSpId());
		repo.deleteById(id);
		return ResponseEntity
				.status(200)
				.header("Message", "Employee successfully deleted.")
				.body(response);
	}
	
	public String deleteUser(String id)
	{
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("spadmin", "admin");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = 
				template.exchange(
						  "http://172.174.175.230:8080/identityiq/scim/v2/Users/" + id
						, HttpMethod.DELETE
						, entity
						, String.class
						);
		return response.getBody();
	}
	
}
