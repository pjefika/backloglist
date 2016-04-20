package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.UsuarioEfika;
import webservices.EfikaUsersProxy;
import webservices.Usuario;


@Stateless
public class LoginServico {

	@PersistenceContext(unitName="vu")
	private EntityManager entityManager;

	private EfikaUsersProxy efikaUsersProxy;

	public LoginServico() {

		this.efikaUsersProxy = new EfikaUsersProxy();
		
	}


	public Usuario buscaLogin(String login) throws Exception {
						
		Usuario usuarioWS = efikaUsersProxy.consultarUsuario(login);
		
		if (usuarioWS == null){
			
			throw new Exception("Usuário não encontrado, se você não possui login de acesso utilize a opção \"Solicite o seu acesso\" na pagina http://efika/web");
			
		}	
		
		return usuarioWS;		

	}

	public void autenticaLogin(String login, String senha, Usuario usuario) throws Exception {
						
			Boolean auth = efikaUsersProxy.autenticarUsuario(login, senha);
									
			if (!auth) {
								
				throw new Exception("Login e senha incorretos!");
				
			}
			
			this.comparaLogin(login, usuario.getNivel());
			
	}

	public void comparaLogin(String login, Integer nivel) {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM UsuarioEfika u WHERE u.login =:param1");
			query.setParameter("param1", login);		
			query.getSingleResult();
			
		} catch (Exception e) {
			
			this.salvaLogin(login, nivel);
			
		}

	}
	
	public void salvaLogin(String login, Integer nivel) {

		UsuarioEfika usuarioEfika = new UsuarioEfika();

		usuarioEfika.setLogin(login);
		usuarioEfika.setNivel(nivel);

		this.entityManager.persist(usuarioEfika);

	}

}
