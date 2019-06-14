package com.backendjavatest.backendjavatest.model;

import java.io.Serializable;

public class NegotiationBean implements Serializable{
	private Long rebelde1Id;
	private Long rebelde2Id;
	private Inventory inventory1;
	private Inventory inventory2;
	
	public NegotiationBean() {
		
	}
	
	public NegotiationBean(Long rebelde1Id, Long rebelde2Id, Inventory inventory1, Inventory inventory2) {
		this.rebelde1Id = rebelde1Id;
		this.rebelde2Id = rebelde2Id;
		this.inventory1 = inventory1;
		this.inventory2 = inventory2;
	}
	
	public Long getRebelde1Id() {
		return rebelde1Id;
	}
	public void setRebelde1Id(Long rebelde1Id) {
		this.rebelde1Id = rebelde1Id;
	}
	public Long getRebelde2Id() {
		return rebelde2Id;
	}
	public void setRebelde2Id(Long rebelde2Id) {
		this.rebelde2Id = rebelde2Id;
	}
	public Inventory getInventory1() {
		return inventory1;
	}
	public void setInventory1(Inventory inventory1) {
		this.inventory1 = inventory1;
	}
	public Inventory getInventory2() {
		return inventory2;
	}
	public void setInventory2(Inventory inventory2) {
		this.inventory2 = inventory2;
	}
	
	
}
