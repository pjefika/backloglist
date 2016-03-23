package model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.LogDefeito;
import entidades.Usuario;

@Stateless
public class LogServico {
	
	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public LogServico() {
				
	}
	
	@SuppressWarnings("unchecked")
	public List<LogDefeito> listarLogDefeitoSS(String loginOUss, Usuario usuario) throws Exception {
		
		try {
			
			if (usuario.getNivel() == 3){
				Query query = this.entityManager.createQuery("FROM LogDefeito l WHERE l.defeito.ss =:param1 OR l.usuario.login =:param1");
				query.setParameter("param1", loginOUss);
				return query.getResultList();
			}else{
				Query query = this.entityManager.createQuery("FROM LogDefeito l WHERE l.defeito.ss =:param1 OR l.usuario.login =:param1 AND l.usuario.supervisor.matricula =:param2");
				query.setParameter("param1", loginOUss);
				query.setParameter("param2", usuario.getLogin());
				return query.getResultList();
			}
			
		} catch (Exception e) {
			
			//return new ArrayList<LogDefeito>();
			
			throw new Exception("Colaborador/Defeito não encontrado ou o mesmo não está abaixo de sua supervisão!");
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<LogDefeito> listarLog(String LoginOuSs) {
		
		
		Query query = this.entityManager.createQuery("FROM LogDefeito l WHERE l.defeito.ss =:param1 OR l.usuario.login =:param1");
		query.setParameter("param1", LoginOuSs);
		return query.getResultList();
		
	}
	

}
