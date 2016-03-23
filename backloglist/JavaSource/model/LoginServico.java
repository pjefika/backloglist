package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Usuario;

@Stateless
public class LoginServico {
	
	@PersistenceContext(unitName="vu")
	private EntityManager entityManager;
	
	public LoginServico() {
		
	}
	
	public Usuario consultarLogin(String login, String senha) throws Exception{
		try {
			Query query = this.entityManager.createQuery("FROM Usuario u WHERE u.login =:param1 AND u.senha =:param2");
			query.setParameter("param1", login);
			query.setParameter("param2", senha);
			return (Usuario) query.getSingleResult();
		}  catch (NoResultException e) {		
			throw new Exception("Usuário ou Senha incorretos!");
		}
	}
	
	public Usuario usuarioExiste(String login) throws Exception {
		try {
			Query query = this.entityManager.createQuery("FROM Usuario u WHERE u.login =:param1");
			query.setParameter("param1", login);
			return (Usuario) query.getSingleResult();
		}  catch (NoResultException e) {
			throw new Exception("Usuário não cadastrado!");
		}
	}
}
