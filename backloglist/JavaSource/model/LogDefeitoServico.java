package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Defeito;
import entidades.LogDefeito;

@Stateless
public class LogDefeitoServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public LogDefeitoServico() {
		
	}
	
	public void gerarLogAcoes(LogDefeito logDefeito, Defeito defeito) throws Exception {
		
		try {			
					
			this.entityManager.persist(logDefeito);
			
		} catch (Exception e) {
			
			throw new Exception("Erro ao completar a ação");
			
		}
		
	}

}
