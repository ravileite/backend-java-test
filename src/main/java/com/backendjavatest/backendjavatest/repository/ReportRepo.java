package com.backendjavatest.backendjavatest.repository;

import org.springframework.data.repository.CrudRepository;

import com.backendjavatest.backendjavatest.model.Report;

public interface ReportRepo  extends CrudRepository<Report, Integer>{

}
