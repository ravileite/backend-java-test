package com.backendjavatest.backendjavatest.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class ReportID  implements Serializable {
	
	@NotNull
	private Long acusadorId;
	@NotNull
	private Long traidorId;
	
	public ReportID  () {
		
	}
	
	public ReportID  (Long acusadorId, Long traidorId){
		this.acusadorId = acusadorId;
		this.traidorId = traidorId;
	}
	
	public Long getAcusadorId() {
		return acusadorId;
	}

	public void setAcusadorId(Long acusadorId) {
		this.acusadorId = acusadorId;
	}

	public Long getTraidorId() {
		return traidorId;
	}

	public void setTraidorId(Long traidorId) {
		this.traidorId = traidorId;
	}


}
