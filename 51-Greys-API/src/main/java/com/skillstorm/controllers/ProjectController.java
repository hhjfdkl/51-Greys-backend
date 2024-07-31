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

import com.skillstorm.models.Project;
import com.skillstorm.services.ProjectService;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*")	//could change when security is implemented
public class ProjectController {

	@Autowired
	private ProjectService service;
	
	
	//CREATE
	@PostMapping
	public ResponseEntity<Project> addProject(@RequestBody Project project)
	{
		return service.createProject(project);
	}
	
	
	//READ
	@GetMapping
	public ResponseEntity<Iterable<Project>> getAllProjects()
	{
		return service.getAllProjects();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable int id)
	{
		return service.getProjectById(id);
	}
	
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(
			  @PathVariable int id
			, @RequestBody Project project
			)
	{
		return service.updateProject(
				  id
				, project.getCodename()
				, project.getDescription()
				, project.getMinClearance()
				, project.getImg()
		//		, project.getEmployees()
				);
	}
	
	
	
	//DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Project> deleteProject(@PathVariable int id)
	{
		return service.deleteProjectById(id);
	}
	
	
}
