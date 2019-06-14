package com.backendjavatest.backendjavatest.model;

import java.io.Serializable;

public class UpdatePositionBean implements Serializable {
	private Long id;
	private Position position;
	
	public UpdatePositionBean() {
		
	}
	
	public UpdatePositionBean(Long Id, Position position) {
		this.id = id;
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Long getId() {
		return id;
	}	
}
