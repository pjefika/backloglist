package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.MotivoEncerramento;

@Stateless
public class MotivoEncerramentoServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public MotivoEncerramentoServico() {
		
	}
	
	/*
	 * Lista os motivos de encerramentos.
	 * */	
	@SuppressWarnings("unchecked")
	public List<MotivoEncerramento> listarMotivoEncerramento() {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM MotivoEncerramento m");
			return query.getResultList();
			
		} catch (Exception e) {
			
			return new ArrayList<MotivoEncerramento>();
			
		}
		
	}
	
	/*
	 * Cadastra os motivos para encerramentos.
	 * */
	public void cadastrarMotivosEncerramento(MotivoEncerramento motivoEncerramento) throws Exception {
		
		try {
			
			if (motivoEncerramento.getMotivo().isEmpty()){
				throw new Exception();
			}
			
			motivoEncerramento.setStatus(1);			
			this.entityManager.persist(motivoEncerramento);
			
		} catch (Exception e) {
			
			throw new Exception("Erro ao cadastar motivo de encerramento, verifique os campos!");
			
		}		
		
	}
	
	public void editarMotivoEncerramento(MotivoEncerramento motivoEncerramento) throws Exception {
		
		try {
			
			this.entityManager.merge(motivoEncerramento);
			
		} catch (Exception e) {
			
			throw new Exception("Erro ao editar motivo");
			
		}
		
	}
	

}
