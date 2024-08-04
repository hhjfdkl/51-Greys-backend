package com.skillstorm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Clearance;
import com.skillstorm.models.Employee;
import com.skillstorm.models.Project;
import com.skillstorm.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository repo;
	
	//CREATE
	public ResponseEntity<Project> createProject(Project project)
	{
		
		if(project.getCodename().equals(repo.getProjectName(project.getCodename())))
		{
			return ResponseEntity
					.status(400)
					.header("Error", "A project with that name already exists.")
					.body(null);
		}
		
		return ResponseEntity
				.status(201)
				.header("Message", "Project created")
				.body(repo.save(project));
	}
	
	
	//READ
	public ResponseEntity<Iterable<Project>> getAllProjects()
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled all projects")
				.body(repo.findAll());
	}
	
	//should we add getAllByString?? Depends on front end functionality
	public ResponseEntity<Iterable<Project>> getProjectsByName(String name)
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled projects by name provided.")
				.body(repo.findProjectsByName(name));
	}
	
	
	public ResponseEntity<Project> getProjectById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find project by ID provided - try again")
					.body(null);
		}
		
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled project by id.")
				.body(repo.findById(id).get());
	}
	
	
	//UPDATE
	public ResponseEntity<Project> updateProject(
			  int id
			, String codename
			, String description
			, Clearance minClearance
			, String img
			, List<Employee> employees
			)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find project by ID provided - try again")
					.body(null);
		}
		
		return ResponseEntity
				.status(200)
				.header("Message", "Project successfully updated")
				.body(repo.save(new Project(
						  id
						, codename
						, description
						, minClearance
						, img
						, employees
						)
					)
				);
		
	}
	
	
	//DELETE
	public ResponseEntity<Project> deleteProjectById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find project by ID provided - try again")
					.body(null);
		}
		Project response = repo.findById(id).get();
		repo.deleteById(id);
		return ResponseEntity
				.status(204)
				.header("Message", "Project successfully deleted.")
				.body(response);
	}
	
	
}

