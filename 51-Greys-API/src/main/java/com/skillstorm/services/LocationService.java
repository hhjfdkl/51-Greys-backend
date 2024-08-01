package com.skillstorm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Employee;
import com.skillstorm.models.Location;
import com.skillstorm.models.Project;
import com.skillstorm.repositories.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository repo;
	
	//CREATE
	public ResponseEntity<Location> createLocation(Location location)
	{
		
		if(location.getLocationName().equals(repo.getLocationName(location.getLocationName())))
		{
			return ResponseEntity
					.status(400)
					.header("Error", "A location with that name already exists.")
					.body(null);
		}
		
		return ResponseEntity
				.status(201)
				.header("Message", "Location created")
				.body(repo.save(location));
	}
	
	
	//READ
	public ResponseEntity<Iterable<Location>> getAllLocations()
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled all locations")
				.body(repo.findAll());
	}
	
	public ResponseEntity<Iterable<Location>> getLocationsByName(String name)
	{
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled locations by name provided.")
				.body(repo.findLocationsByName(name));
	}
	
	public ResponseEntity<Location> getLocationById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find location by ID provided - try again")
					.body(null);
		}
		
		return ResponseEntity
				.status(200)
				.header("Message", "Pulled location by id.")
				.body(repo.findById(id).get());
	}
	//UPDATE
	public ResponseEntity<Location> updateLocation(
			  int id
			, String locationName
			, String province
			, String country
			, double longitude
			, double latitude
			, List<Employee> employees
			)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find location by ID provided - try again")
					.body(null);
		}
		
		return ResponseEntity
				.status(200)
				.header("Message", "Location successfully updated")
				.body(repo.save(new Location(
						  id
						, locationName
						, province
						, country
						, longitude
						, latitude
						, employees
						)
					)
				);
		
	}
	
	
	//DELETE
	public ResponseEntity<Location> deleteLocationById(int id)
	{
		if(!repo.existsById(id))
		{
			return ResponseEntity
					.status(404)
					.header("Error", "Unable to find location by ID provided - try again")
					.body(null);
		}
		Location response = repo.findById(id).get();
		repo.deleteById(id);
		return ResponseEntity
				.status(200)
				.header("Message", "Location successfully deleted.")
				.body(response);
	}
	
	
}
