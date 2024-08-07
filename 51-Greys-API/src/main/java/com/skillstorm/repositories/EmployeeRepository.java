package com.skillstorm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	//Returns a list of employees with the name queried (first or last)
	//TODO:update this to update with JOIN on clearance and location tables
	@Query(
			nativeQuery = true,
			value =
			  "SELECT employee_id, first_name, last_name, email, phone_number, occupation, clearance, img "
			+ "FROM employee "
			+ "WHERE first_name LIKE %?1% "
			+ "OR last_name LIKE %?1% "
			+ "ORDER BY last_name"
	)
	public Iterable<Employee> findEmployeesByName(String name);
	
	@Query(nativeQuery = true,
			value = 
			  "SELECT * FROM employee "
			+ "ORDER BY employee_id DESC"
			)
	public Iterable<Employee> findAllReversed();
}
