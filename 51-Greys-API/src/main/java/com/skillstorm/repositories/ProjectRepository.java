package com.skillstorm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

	//Get a project name (used to check our project name is unique
	@Query(
		nativeQuery = true,
		value = 
		   "SELECT codename "
		 + "FROM project "
		 + "WHERE codename LIKE ?1"
		)
	public String getProjectName(String name);
	
	//For use in search bar potentially
	@Query(
		nativeQuery = true,
		value =
		  "SELECT project_id, codename, description, min_clearance, priority, img "
		+ "FROM project "
		+ "WHERE codename LIKE %?1%"
		)
	public Iterable<Project> findProjectsByName(String name);
		
	
}
