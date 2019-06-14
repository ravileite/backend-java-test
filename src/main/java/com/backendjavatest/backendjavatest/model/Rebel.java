package com.backendjavatest.backendjavatest.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Rebel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@NotBlank
	private String nome;
	@NotNull @Min(0)
	private BigInteger idade;
	@NotBlank
	private String genero;
	@NotNull
	private Position position;
	@NotNull
	private Inventory inventory;
	@NotNull 
	private Boolean traidor = false;
	
	@Valid @Embedded
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setTraidor(boolean traidor) {
		this.traidor = traidor;
	}
	
    public boolean isTraidor() {
    	return traidor;
    }
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigInteger getIdade() {
		return idade;
	}
	public void setIdade(BigInteger idade) {
		this.idade = idade;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getId() {
		return id;
	}

	@Valid @Embedded
	public Position getPosition() {
		return position;
	}
	
	public void setPosition( Position position) {
		this.position = position;
	}
}
