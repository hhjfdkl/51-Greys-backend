package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

	//like employee, we're most likely to only need searches by name added here
	
}
