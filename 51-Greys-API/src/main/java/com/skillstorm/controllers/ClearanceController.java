package com.skillstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Clearance;
import com.skillstorm.services.ClearanceService;

@RestController
@RequestMapping("/clearance")
//@CrossOrigin(origins = "*")
public class ClearanceController {

	@Autowired
	private ClearanceService service;
	
	@GetMapping
	public ResponseEntity<Iterable<Clearance>> getAllClearances()
	{
		return service.getAllClearances();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Clearance> getClearanceById(@PathVariable int id)
	{
		return service.getClearanceById(id);
	}
}
