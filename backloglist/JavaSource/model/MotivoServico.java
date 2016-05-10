package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.MotivoEncerramento;

@Stateless
public class MotivoServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public MotivoServico() {
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoEncerramento> listaMotivoEncerramento() {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM MotivoEncerramento m");
			
			return query.getResultList();
			
		} catch (Exception e) {
			
			return new ArrayList<MotivoEncerramento>();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoEncerramento> listaMotivoEncerramentoPorStatus() {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM MotivoEncerramento m WHERE m.status =:param1");
			query.setParameter("param1", 1);		
			
			return query.getResultList();
			
		} catch (Exception e) {
			
			return new ArrayList<MotivoEncerramento>();
			
		}
		
	}
	
	public void cadastrarMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
		
		motivoEncerramento.setStatus(1);
		this.entityManager.persist(motivoEncerramento);
		
	}
	
	public void editarMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
		
		this.entityManager.merge(motivoEncerramento);
		
	}
}
