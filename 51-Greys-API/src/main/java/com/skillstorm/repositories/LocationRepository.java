package com.skillstorm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{

	//to check against existing locations so we don't have duplicates
	@Query(
		nativeQuery = true,
		value =
		  "SELECT location_name "
		+ "FROM location "
		+ "WHERE location_name LIKE ?1"
		)
	public String getLocationName(String name);
	
	//For potential use in search bar
	//likely won't be used
	@Query(
		nativeQuery = true,
		value =
		  "SELECT location_id, location_name, province, country, longitude, latitude "
		+ "FROM location "
		+ "WHERE location_name LIKE %?1%"
		)
	public Iterable<Location> findLocationsByName(String name);
}
