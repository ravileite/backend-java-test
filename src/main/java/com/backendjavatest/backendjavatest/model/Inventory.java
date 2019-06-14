package com.backendjavatest.backendjavatest.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
public class Inventory {
	@Min(0)
	private int arma;
	@Min(0)
	private int municao;
	@Min(0)
	private int agua;
	@Min(0)
	private int comida;
	
	public int getPontos() {
		return (arma*4) + (municao*3) + (agua*2) + (comida);
	}
	
	public int getArma() {
		return arma;
	}
	public void setArma(int arma) {
		this.arma = arma;
	}
	public int getMunicao() {
		return municao;
	}
	public void setMunicao(int municao) {
		this.municao = municao;
	}
	public int getAgua() {
		return agua;
	}
	public void setAgua(int agua) {
		this.agua = agua;
	}
	public int getComida() {
		return comida;
	}
	public void setComida(int comida) {
		this.comida = comida;
	}
}
