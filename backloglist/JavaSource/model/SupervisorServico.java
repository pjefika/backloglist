package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Supervisor;

@Stateless
public class SupervisorServico {
	
	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public SupervisorServico() {
		
	}
	
	public void cadastrarSupervisor(Supervisor supervisor) throws Exception {
		
		try {
			this.entityManager.persist(supervisor);
		} catch (Exception e) {
			throw new Exception("Erro ao cadastrar supervisor!");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Supervisor> listarSupervisores() {
		
		try {
			Query query = this.entityManager.createQuery("FROM Supervisor s");
			return query.getResultList();			
		} catch (Exception e) {
			return new ArrayList<Supervisor>();
		}
		
	}

}
