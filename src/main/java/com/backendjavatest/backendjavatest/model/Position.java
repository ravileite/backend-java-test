package com.backendjavatest.backendjavatest.model;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Embeddable
public class Position {
	
	@NotNull
	private BigDecimal latitude;
	@NotNull
	private BigDecimal longitude;
	@NotBlank
	private String nomeGalaxia;	
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getNomeGalaxia() {
		return nomeGalaxia;
	}
	public void setNomeGalaxia(String nomeGalaxia) {
		this.nomeGalaxia = nomeGalaxia;
	}


}
