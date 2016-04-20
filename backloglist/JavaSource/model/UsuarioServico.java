package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.UsuarioEfika;

@Stateless
public class UsuarioServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public UsuarioServico() {
		
	}
			
	@SuppressWarnings("unchecked")
	public List<UsuarioEfika> listarUsuarios() {	
		
		try {
			Query query = this.entityManager.createQuery("FROM UsuarioEfika u");
			return query.getResultList();			
		} catch (Exception e) {
			return new ArrayList<UsuarioEfika>();
		}
		
	}
	
}
