package com.backendjavatest.backendjavatest.controller;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backendjavatest.backendjavatest.model.NegotiationBean;
import com.backendjavatest.backendjavatest.model.Rebel;
import com.backendjavatest.backendjavatest.model.Report;
import com.backendjavatest.backendjavatest.model.ReportID;
import com.backendjavatest.backendjavatest.model.UpdatePositionBean;
import com.backendjavatest.backendjavatest.model.Util;
import com.backendjavatest.backendjavatest.repository.RebelRepo;
import com.backendjavatest.backendjavatest.repository.ReportRepo;


@RestController
public class RebelController {
	
	@Autowired
	RebelRepo repository;
	@Autowired
	ReportRepo reports;
	
	Util util = new Util();
	
	/**
	 * @param rebel
	 * @return ResponseEntity<>
	 * 
	 * Adiciona um novo rebelde ao sistema.
	 * 
	 */
	
	@RequestMapping(value="/addRebel", 
			        method = RequestMethod.POST)
	public ResponseEntity<Rebel> addRebel(@RequestBody @NotNull Rebel rebel) {
			repository.save(rebel);
			return new ResponseEntity<> (rebel, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param reportId
	 * @return ResponseEntity<Report>
	 * @throws Exception
	 * 
	 * Aqui um rebelde pode reportar outro rebelde. O report será salvo em um banco de dados que irá armazenar apenas o Id
	 * do acusador e do suposto traidor. Um rebelde pode denunciar um outro rebelde apenas uma vez, não haverão linhas repetidas
	 * na tabela Report. Um rebelde não pode denunciar a ele mesmo e só poderão ser passados Ids de rebeldes já cadastrados no sistema.
	 * O método faz uso da classe Util para contar quantas vezes o suposto traidor foi reportado, caso seja maior ou igual a 3 ele 
	 * se torna um traidor.
	 * 
	 */
	
    @RequestMapping(value="/reportRebel",
					method = RequestMethod.POST)
    public ResponseEntity<Report> reportRebel(@RequestBody @NotNull ReportID reportId) throws Exception{
    	try {
    		if(repository.findById(reportId.getAcusadorId()).equals(null)|| repository.findById(reportId.getTraidorId()).equals(null) ||
    				reportId.getAcusadorId().equals(reportId.getTraidorId())) {
    			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    		}
    		Report report = new Report();
    		report.setReport(reportId);
    		reports.save(report);
    		if(util.countTraitor(reports, reportId.getTraidorId()) >= 3) {
    			Rebel rebel = repository.findById(reportId.getTraidorId());
    			rebel.setTraidor(true);
    			repository.save(rebel);
    		} 	
    		return new ResponseEntity<> (report, HttpStatus.OK);
    	}catch(Exception e) {
    		return  new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    	}
    }

    /**
     * 
     * @param id
     * @param latitude
     * @param longitude
     * @param nomeGalaxia
     * @return ResponseEntity<Rebel>
     * @throws Exception
     * 
     * Nesse metodo é passado um bean que contem o id de um rebelde e uma posição. Ele verifica se o rebelde está no banco de dados,
     * se sim, ele atualiza todos os dados referentes à localização do rebelde.
     * 
     */
	
    @RequestMapping(value="/updateLocation",
    				method = RequestMethod.PUT)
    public ResponseEntity<Rebel> updateLocation(@RequestBody @NotNull UpdatePositionBean uPosition) throws Exception {
    	try {
    		Rebel rebel = repository.findById(uPosition.getId());
    		if(rebel.equals(null)) {
    			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    		}
    		rebel.getPosition().setLatitude(uPosition.getPosition().getLatitude());
    		rebel.getPosition().setLongitude(uPosition.getPosition().getLongitude());
    		rebel.getPosition().setNomeGalaxia(uPosition.getPosition().getNomeGalaxia());
    		repository.save(rebel);
    		return new ResponseEntity<> (rebel, HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    	}
    }
    
 /**
  * 
  * @param neg
  * @return ResponseEntity<String>
  * 
  * Esse método realiza várias verificações, por isso ficou um pouco extenso. Ele verifica se os ids estão no sistema, se algum dos
  * 2 rebeldes que querem negociar é traidor, se os rebeldes realmente têm os itens que querem negociar e se os somatório de pontos
  * dos itens que cada um quer negociar são iguais. Se tudo isso estiver ok os itens do rebelde 1 passarão para o rebelde 2 e vice-versa.
  * Foi utilizado um ben, pois o RequestBody seria formado pelos Ids dos 2 rebeldes e de 2 inventários (reutilizei a classe Inventory, 
  * considerando que cada rebelde irá disponibilizar um "sub-inventário" para a troca, podendo ser até todo o seu inentário).
  * 
  */
    
    @RequestMapping(value="/negotiation",
			method = RequestMethod.PUT)
    public ResponseEntity<String> negotiation(@NotNull @RequestBody NegotiationBean neg){   
    	try {
			Rebel rebel1 = repository.findById(neg.getRebelde1Id());
			Rebel rebel2 = repository.findById(neg.getRebelde2Id());
    		if(rebel1.equals(null) || rebel2.equals(null)  ) {
    			return new ResponseEntity<> ("Os IDs não constam no sistema. Tente novamente.",HttpStatus.BAD_REQUEST);	
    		}
    		
    		if(rebel1.isTraidor() || rebel2.isTraidor()) {
    			return new ResponseEntity<> ("Um dos dois rebeldes é um traidor. Negociação não permitida!",HttpStatus.BAD_REQUEST);	
    		}
    		
    		if(neg.getInventory1().getPontos() == neg.getInventory2().getPontos()) {

    			if(!(util.verificaQuantidade(rebel1, neg.getInventory1()) || !(util.verificaQuantidade(rebel2, neg.getInventory2())))) {
    				return new ResponseEntity<> ("Um dos rebeldes não tem a quantidade de itens que foi fornecida.",HttpStatus.BAD_REQUEST);
    			}
    			rebel1 = util.transaction(rebel1, neg.getInventory1(), neg.getInventory2());
    			repository.save(rebel1);
    			rebel2 = util.transaction(rebel2, neg.getInventory2(), neg.getInventory1());
    			repository.save(rebel2);
    		}else {
    			return new ResponseEntity<> ("Os itens informados não têm a mesma quantidade de pontos.",HttpStatus.BAD_REQUEST);
    		}
    		return new ResponseEntity<> ("Transação efetuada com sucesso",HttpStatus.OK);
    	
    		}catch(Exception e) {
    			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);	
    	}
    }
    
    /**
     * 
     * @return ResponseEntity<String>
     * 
     * Retorna um pequeno relatório contendo o número de traidores, o total de cadastrados e a pocentagem de traidores presentes no sistema.
     * 
     */
    
    @RequestMapping(value="/traitorsPercent",
			method = RequestMethod.GET)
    public ResponseEntity<String> getTraitorsPercent(){
    		double total = (double)repository.count();
    		if (total == 0 ) return new ResponseEntity<> ("Ainda não há cadastros no sistema.",HttpStatus.BAD_REQUEST);
    		double qntTraitors = (double)util.getQuantidadeTraidores(repository);
    		double traitorsPercent = ( qntTraitors/ total) * 100.0;
    		return new ResponseEntity<> (" - - - - - - - - Porcentagem de Traidores no Sistema - - - - - - - -  \n\n" + 
    				" - Número de traidores: " + (int)qntTraitors + "\n" + 
    				" - Total: " + (int)total + "\n" + 
    				" - Porcentagem de Traidores: " +  traitorsPercent + "%",HttpStatus.OK);		
    }
    
    /**
     * 
     * @return ResponseEntity<String>
     * 
     * Retorna um pequeno relatório, contendo o número de rebeldes, o total de cadastrados e a porcentagem de rebeldes presentes no sistema.
     * 
     */
    
    @RequestMapping(value="/rebelsPercent",
			method = RequestMethod.GET)
    public ResponseEntity<String> getRebelsPercent(){
    	    double total = (double)repository.count();
    	    if (total == 0 ) return new ResponseEntity<> ("Ainda não há cadastros no sistema.",HttpStatus.BAD_REQUEST);
    		double qntRebels = (double)(total - util.getQuantidadeTraidores(repository));
    		double rebelsPercent = ( qntRebels/ total) * 100.0;
    		return new ResponseEntity<> (" - - - - - - - - Porcentagem de Rebeldes no Sistema - - - - - - - -  \n\n" + 
    				" - Número de Rebeldes: " + (int)qntRebels + "\n" + 
    				" - Total: " + (int)total + "\n" + 
    				" - Porcentagem de Rebeldes: " +  rebelsPercent + "%",HttpStatus.OK);		
    }
    
    /**
     * 
     * @return ResponseEntity<String>
     * 
     * Retorn um pequeno relatório contendo o numero de rebeldes (não foram contabilizados os traidores) e a média  por rebelde de
     *  cada um dos 4 recursos.
     * 
     */
    
    
    @RequestMapping(value="/resourcesMeans",
			method = RequestMethod.GET)
    public ResponseEntity<String> getResourcesMeans(){
    	double total = (double)repository.count();
	    if (total == 0 ) return new ResponseEntity<> ("Ainda não há cadastros no sistema.",HttpStatus.BAD_REQUEST);
    	double totalRebels = total - util.getQuantidadeTraidores(repository);
    	ArrayList<Double> means = util.countResources(repository);
    	
    	return new ResponseEntity<> (" - - - - - - - - Média de Recursos por Rebeldes - - - - - - - -  \n\n" + 
				" - Número de Rebeldes: " + (int) totalRebels + "\n" + 
				" - Total: " + (int) total + "\n"  +
				" - Armas: " + String.format("%.2f", means.get(0)) + " por rebelde;" + "\n" + 
				" - Munições: " + String.format("%.2f", means.get(1)) + " por rebelde;" + "\n"  + 
				" - Agua: " + String.format("%.2f", means.get(2)) + " por rebelde;" + "\n" + 
				" - Comida: " + String.format("%.2f", means.get(3)) + " por rebelde.", HttpStatus.OK);	
    } 
    
    /**
     * 
     * @return ResponseEntity<String>
     * 
     * Retorna um pequeno relatório, contendo o número de traidores e o somatório dos pontos perdidos devido a eles.
     * 
     */
    
    @RequestMapping(value="/lostPoints",
			method = RequestMethod.GET)
    public ResponseEntity<String> getLostPoints(){
	    double total = (double)repository.count();
	    if (total == 0 ) return new ResponseEntity<> ("Ainda não há cadastros no sistema.",HttpStatus.BAD_REQUEST);
	    double qntTraitors = (double)util.getQuantidadeTraidores(repository);
	    int lostPoints = util.countLostPoints(repository);
	    return new ResponseEntity<> (" - - - - - - - - Pontos Perdidos Devido a Traidores - - - - - - - -  \n\n" + 
				" - Número de Traidores: " + (int) qntTraitors + "\n" + 
				" - Pontos Perdidos: " +  lostPoints, HttpStatus.OK);			    
    }
}
