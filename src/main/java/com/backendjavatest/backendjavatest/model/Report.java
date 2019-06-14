package com.backendjavatest.backendjavatest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;


@Entity 
public class Report {
	
	
	ReportID report;
	
	@Id @Valid
	public ReportID getReport() {
		return report;
	}
	
	public void setReport(ReportID report) {
		this.report = report;
	}


}



