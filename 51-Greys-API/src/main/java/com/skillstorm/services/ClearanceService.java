package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Clearance;
import com.skillstorm.repositories.ClearanceRepository;

@Service
public class ClearanceService {

	@Autowired
	private ClearanceRepository repo;
	
	
	public ResponseEntity<Iterable<Clearance>> getAllClearances()
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled all clearances.")
				.body(repo.findAll());
	}
	
	public ResponseEntity<Clearance> getClearanceById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find clearance by ID provided - try again")
					.body(null);
		}
		return ResponseEntity
				.status(200)
				.header("Message", "Successfully pulled employee by ID.")
				.body(repo.findById(id).get());
	}
}
