package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Clearance;

@Repository
public interface ClearanceRepository extends CrudRepository<Clearance, Integer> {

}
