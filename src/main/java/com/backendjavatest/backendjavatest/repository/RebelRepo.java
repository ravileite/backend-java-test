package com.backendjavatest.backendjavatest.repository;

import org.springframework.data.repository.CrudRepository;

import com.backendjavatest.backendjavatest.model.Rebel;

public interface RebelRepo extends CrudRepository<Rebel, Integer>{

	Rebel findById(Long id);
	
}
