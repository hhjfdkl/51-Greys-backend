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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Location;
import com.skillstorm.services.LocationService;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*") //possibly change when security is put in
public class LocationController {

	@Autowired
	private LocationService service;
	
	//CREATE
	@PostMapping
	public ResponseEntity<Location> addLocation(@RequestBody Location location)
	{
		return service.createLocation(location);
	}
	
	//READ
	@GetMapping
	public ResponseEntity<Iterable<Location>> getAllLocations()
	{
		return service.getAllLocations();
	}
	
	@GetMapping("/search")
	public ResponseEntity<Iterable<Location>> getLocationsByName(@RequestParam("name") String name)
	{
		return service.getLocationsByName(name);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Location> getLocationById(@PathVariable int id)
	{
		return service.getLocationById(id);
	}
	
	//UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Location> updateLocation(@PathVariable int id, @RequestBody Location location)
	{
		return service.updateLocation(
					  id
					, location.getLocationName()
					, location.getProvince()
					, location.getCountry()
					, location.getLongitude()
					, location.getLatitude()
					, location.getEmployees()
				);
	}

	//no interactivity on front end at this time. Could re-implement
//	//DELETE
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Location> deleteLocation(@PathVariable int id)
//	{
//		return service.deleteLocationById(id);
//	}
//	
	
}
