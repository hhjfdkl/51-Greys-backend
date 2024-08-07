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
import org.springframework.web.client.HttpServerErrorException;
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

	StringCutter stringCutter = new StringCutter();
	
	SailPointHandler sailpoint = new SailPointHandler();
	
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
		String req = stringCutter	
		.userPostRequest(
				  employee.getFirstName()
				, employee.getLastName()
				, employee.getEmail());
		
		//set our employee's SP id to save in DB based on what comes back from our json
		employee.setSpId(stringCutter.findUserId(
				sailpoint
				.createUser(req)
				.getBody()));
		
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
				.body(repo.findAllReversed());
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
		
		Employee dbEmployee = repo.findById(id).get();
		String spId = dbEmployee.getSpId();
		
		//when SP id exists, call SP with a put request
		if(spId != null) {
			String user = sailpoint.getUserById(spId).getBody();
			String userName = stringCutter
					.findUserName(user);
			String body = stringCutter
					.userPutRequest(userName, firstName, lastName, email);
			sailpoint.updateUser(spId, body);
		//otherwise, create a user for this employee, then use spId to save it in the DB
		} else {
			String req = stringCutter
				.userPostRequest(
						  firstName
						, lastName
						, email);
				spId = stringCutter.findUserId(
						sailpoint
						.createUser(req)
						.getBody()
						);
		}	
		
		
		/**
		 * If clearance level is 3 or higher, make sure we have an associated account with the user
		 * 1 is Top Secret
		 * 2 is Secret
		 * 3 is Confidential
		 */
		
		int clrNumeric = clearance.getClearanceLevel();
		String spAcctId = dbEmployee.getSpAcctId();
		
		if((spAcctId == null) && (clrNumeric <= 3) ) 
		{
			//SailPoint always returns a 500 error when account is created
			//the 500 error always states that nativeIdentity doesn't exist 
			//even if nativeIdentity is provided, no body in the 500 response
			//Error override is in the createAccount method.
				sailpoint.createAccount(
						stringCutter.accountPostRequest(
								  spId
								, email
								, lastName)
						);
				
				//since we always get a 500, we're going to call the user by id
				//and then assign its associated account to our employee object
			
				spAcctId = 
						stringCutter.findAccountId(
								sailpoint.getUserById(spId).getBody()
									);
		}
			
			
		
		
		if((clrNumeric > 3) && (spAcctId != null))
		{
			if(sailpoint.deleteAccount(spAcctId).getStatusCode().value()==204)
				spAcctId = null;
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
						, spAcctId
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
		Employee response = repo.findById(id).get();
		
		//in case we have a user without SP identity and/or account
		if(response.getSpAcctId() != null)
			sailpoint.deleteAccount(response.getSpAcctId());
		if(response.getSpId() != null)
			sailpoint.deleteUser(response.getSpId());
		
		
 
		
		repo.deleteById(id);
		return ResponseEntity
				.status(204)
				.header("Message", "Employee successfully deleted.")
				.body(response);
	}
	

	
	
	/**
	 * private class for SailPoint methods - above felt too cluttered
	 * 
	 * Below contains all the methods which call to the SailPoint's API
	 * 
	 * I'm keeping them in employees since all SailPoint functionality is
	 * related to employees and not other objects in our API 
	 * 
	 * 
	 * NOTE that PUT is intentionally not here for accounts. 
	 * For some reason, we can't get PUT to work, but we'll make it once 
	 * we can figure out how to create a Salesforce account through SailPoint
	 * 
	 */
	private class SailPointHandler {
		
	//CREATE
		//to POST user in SP
		public ResponseEntity<String> createUser(String user)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(username, password);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<>(user, headers);
			
			return		template.exchange(
							  url + "/Users"
							, HttpMethod.POST
							, entity
							, String.class
							);
			
		}
		
		//to POST account in SP
		public ResponseEntity<String> createAccount(String account) 
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(username, password);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<>(account, headers);
			
			/**
			 * SailPoint always returns a 500 error
			 * This is here for force a 201 response
			 */
			
			try {
				template.exchange(
							  url + "/Accounts"
							, HttpMethod.POST
							, entity
							, String.class
							);
			} catch (HttpServerErrorException e) {
				return ResponseEntity
						.status(201)
						.header("Message", "Account Created")
						.body("No body to return")
						;
			}
			return ResponseEntity
					.status(201)
					.header("Message", "Account Created")
					.body("No body to return")
					;
					
				
//			return response.getBody();
		}
		
		
	//READ
		//to handle GET requests for user to SP
		public ResponseEntity<String> getUserById(String id)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(username, password);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			 
			return		template.exchange(
							  url + "/Users/" + id
							, HttpMethod.GET
							, entity
							, String.class
							);
			
		}
		

		
		//to handle GET requests for account to SP
		public ResponseEntity<String> getAccountById(String id)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(username, password);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			 
			return		template.exchange(
							  url + "/Accounts/" + id
							, HttpMethod.GET
							, entity
							, String.class
							);
			
		}
		
	//UPDATE
		//to handle PUT requests for user to SP
		public ResponseEntity<String> updateUser(String id, String body)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(username, password);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<>(body, headers);
			 
			return		template.exchange(
							  url + "/Users/" + id
							, HttpMethod.PUT
							, entity
							, String.class
							);
			
			
		}
		
		
	//DELETE
		//to handle DELETE requests for user to SP
		public ResponseEntity<String> deleteUser(String id)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(username, password);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			 
			return		template.exchange(
							  url + "/Users/" + id
							, HttpMethod.DELETE
							, entity
							, String.class
							);
			
		}
		
		//to handle DELETE requests for account to SP
		public ResponseEntity<String> deleteAccount(String id)
		{
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(username, password);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			 
			return		template.exchange(
							  url + "/Accounts/" + id
							, HttpMethod.DELETE
							, entity
							, String.class
							);
			
		}
		
		
	}
}

