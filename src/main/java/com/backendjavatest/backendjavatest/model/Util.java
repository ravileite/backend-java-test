package com.backendjavatest.backendjavatest.model;

import java.util.ArrayList;
import java.util.List;

import com.backendjavatest.backendjavatest.repository.RebelRepo;
import com.backendjavatest.backendjavatest.repository.ReportRepo;

public class Util {
	
	public int countTraitor(ReportRepo repository, Long traidorId ) {
		
		List<Report> list = (List<Report>) repository.findAll();
		
		int count = 0;
		
		for (Report report : list) {
			if(report.getReport().getTraidorId() == traidorId) {
				count++;
			}
		}
		return count;
	}
	
	public Rebel transaction(Rebel rebel, Inventory inventory1, Inventory inventory2) {
			rebel.getInventory().setArma(rebel.getInventory().getArma() - inventory1.getArma() + inventory2.getArma());
			rebel.getInventory().setMunicao(rebel.getInventory().getMunicao() - inventory1.getMunicao() + inventory2.getMunicao());
			rebel.getInventory().setAgua(rebel.getInventory().getAgua() - inventory1.getAgua() + inventory2.getAgua());
			rebel.getInventory().setComida(rebel.getInventory().getComida() - inventory1.getComida() + inventory2.getComida());
		return rebel;
	}
	
	public boolean verificaQuantidade(Rebel rebel, Inventory inventory) {
    	if(inventory.getArma() > rebel.getInventory().getArma() ||
    	   inventory.getMunicao() > rebel.getInventory().getMunicao()|| 
    	   inventory.getAgua() > rebel.getInventory().getAgua()||
    	   inventory.getComida() > rebel.getInventory().getComida()){
    		   return false;
    	   }
    	return true;
	}
	
	public int getQuantidadeTraidores(RebelRepo repository) {
		List<Rebel> list = (List<Rebel>) repository.findAll();
		int count = 0;
		
		for (Rebel rebel : list) {
			if(rebel.isTraidor()) {
				count++;
			}
		}
		
		return count;
	}
	
	public int countLostPoints(RebelRepo repository) {
		List<Rebel> list = (List<Rebel>) repository.findAll();
		int count = 0;
		
		for (Rebel rebel : list) {
			if(rebel.isTraidor()) {
				count+=rebel.getInventory().getPontos();
			}
		}
		return count;
	}
	
	public ArrayList<Double> countResources(RebelRepo repository){
		ArrayList<Double> means = new ArrayList<Double>();
		List<Rebel> list = (List<Rebel>) repository.findAll();
		double total = (double)repository.count() - getQuantidadeTraidores(repository); 
	
		for (int i = 1; i <= 4; i++) {
			means.add(0.0);
		}
		
		for (Rebel rebel : list) {
			if(!rebel.isTraidor()) {
				means.set(0, means.get(0) + rebel.getInventory().getArma());
				means.set(1, means.get(1) + rebel.getInventory().getMunicao());
				means.set(2, means.get(2) + rebel.getInventory().getAgua());
				means.set(3, means.get(3) + rebel.getInventory().getComida());
			}
		}
		
		means.set(0, means.get(0) / total);
		means.set(1, means.get(1) / total);
		means.set(2, means.get(2) / total);
		means.set(3, means.get(3) / total);
		
		return means;
	}
}
