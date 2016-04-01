package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Supervisor;
import entidades.Usuario;

@Stateless
public class UsuarioServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public UsuarioServico() {
		
	}
	
	public void cadastrarOperador(Usuario usuario, Supervisor supervisor) throws Exception {
		
		try {			
			this.entityManager.persist(usuario);
			
			if(usuario.getNivel()==2){
				
				supervisor.setMatricula(usuario.getLogin());
				
				supervisor.setNomeSupervisor(usuario.getNome());
				
				this.entityManager.persist(supervisor);
			}else{
				
			}
			
		} catch (Exception e) {
			throw new Exception("Erro ao cadastrar operador!");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Supervisor> listarSupervisor() {
		
		try {			
			Query query = this.entityManager.createQuery("FROM Supervisor s");
			return query.getResultList();			
		} catch (Exception e) {
			return new ArrayList<Supervisor>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios() {	
		
		try {
			Query query = this.entityManager.createQuery("FROM Usuario u");
			return query.getResultList();			
		} catch (Exception e) {
			return new ArrayList<Usuario>();
		}
		
	}
	
	public void mudarSenha(String senha1, String senha2, Usuario usuario) throws Exception {

		System.out.println(senha1);
		System.out.println(senha2);
		
		if (senha1.equals(senha2)){		
			
			usuario.setSenha(senha1);			
			this.entityManager.merge(usuario);
			
		}else{
			
			throw new Exception("As senhas digitadas não se coincidem.");
			
		}
			
		
	}

}
