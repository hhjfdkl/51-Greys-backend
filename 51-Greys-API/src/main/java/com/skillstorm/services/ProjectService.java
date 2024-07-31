package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Project;
import com.skillstorm.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository repo;
	
	//CREATE
	public ResponseEntity<Project> createProject(Project project)
	{
		//TODO:Add a check here for codename - we shouldn't have two projects with same name
		
		return ResponseEntity
				.status(200)
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
			, String minClearance
			, String img
		//	, Employee[] employees
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
					//	, employees
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
				.status(200)
				.header("Message", "Project successfully deleted.")
				.body(response);
	}
	
	
}

