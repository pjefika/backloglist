package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Defeito;
import entidades.LogDefeito;
import entidades.MotivoEncerramento;
import entidades.TipoLog;
import entidades.Usuario;

@Stateless
public class AtendimentoServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public AtendimentoServico() {

	}

	/*
	 * Cadastrar defeito na tabela.
	 * */
	public void CadastrarDefeito(Defeito defeito) throws Exception {

		try {

			defeito.setStatus(0);
			this.entityManager.persist(defeito);

		} catch (Exception e) {

			throw new Exception("Defeito não cadastrado!");

		}

	}

	/*
	 * Montar lista com todos os defeitos ativos.
	 * */
	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosAtivos() {
		
		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status = 0");
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	/*
	 * Montar lista com todos os defeitos ativos que estão assumidos pelo colaborador.
	 * */
	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosColaborador(Usuario usuario) {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status = 1");
			query.setParameter("param1", usuario);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	/*
	 * Assumir defeito da lista
	 * */	
	public Usuario assumirDefeito(Defeito defeito, Usuario usuario) throws Exception {

		usuario.setAssumidos(this.listarDefeitosColaborador(usuario));

		if (usuario.getAssumidos().size() >= 2) {
			throw new Exception("Não é permitido assumir mais de 2 (dois) defeitos por Usuário!");
		}

		defeito.setStatus(1);
		defeito.setUsuario(usuario);
		this.entityManager.merge(defeito);

		LogDefeito log = new LogDefeito(defeito, TipoLog.ASSUMIR, usuario);

		this.entityManager.persist(log);


		return usuario;

	}


	/*
	 * Finalizar atendimento do defeito.
	 * */	
	public void encerrarDefeito(Defeito defeito, Usuario usuario) {

		Date date = new Date();

		defeito.setStatus(2);
		defeito.setDataEncerrado(date);

		this.entityManager.merge(defeito);			

		LogDefeito log = new LogDefeito(defeito, TipoLog.ENCERRAR, usuario);

		this.entityManager.persist(log);

	}

	public void enviarCampo(Defeito defeito, Usuario usuario) {

		Date date = new Date();

		defeito.setStatus(3);
		defeito.setDataEncerrado(date);
		defeito.setMotivoEncerramento(null);

		this.entityManager.merge(defeito);

		LogDefeito log = new LogDefeito(defeito, TipoLog.ENVIADOCAMPO, usuario);

		this.entityManager.persist(log);

	}


	/*
	 * Consulta defeito por operador ss especifica
	 * */	
	public Defeito consultarDefeitoOperadorPorSS(String ss, Usuario usuario) throws Exception {

		try {

			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1 AND d.usuario =:param2 AND d.status = 1");
			query.setParameter("param1", ss);
			query.setParameter("param2", usuario);		

			return (Defeito) query.getSingleResult();			

		} catch (Exception e) {

			throw new Exception("Este defeito não exite ou não está atribuido a sua matricula.");

		}

	}

	
	/**
	 * Consulta Entidade Defeito por id SS (sem critérios);
	 * @param ss
	 * @return Defeito
	 * @author G0034481
	 * @throws Exception
	 */
	public Defeito consultarSS(String ss) throws Exception {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1");
			query.setParameter("param1", ss);
			return (Defeito) query.getSingleResult();			
		} catch (Exception e) {
			throw new Exception("Este defeito não foi integrado na ferramenta.");
		}

	}	

	/*
	 * Lista os motivos de encerramentos.
	 * */	
	@SuppressWarnings("unchecked")
	public List<MotivoEncerramento> listarMotivoEncerramento() {

		try {

			Query query = this.entityManager.createQuery("FROM MotivoEncerramento m WHERE m.status = 1");
			return query.getResultList();

		} catch (Exception e) {

			return new ArrayList<MotivoEncerramento>();

		}

	}

	public void voltarDefeitoParaFila(Defeito defeito, Usuario usuario) {		

		LogDefeito log = new LogDefeito(defeito, TipoLog.VOLTOUFILA, usuario);

		this.entityManager.persist(log);

		defeito.setStatus(0);
		defeito.setUsuario(null);
		defeito.setMotivoEncerramento(null);
		this.entityManager.merge(defeito);		

	}

	public void realizarFulltest(Defeito defeito, Usuario usuario) {

		LogDefeito log = new LogDefeito(defeito, TipoLog.FULLTEST, usuario);

		this.entityManager.persist(log);

	}
	
	public void removeDefeitoAntigo(List<Defeito> defeitos) {
						
		for (Defeito defeito : defeitos) {			
				
			Date date = new Date();
			
			defeito.setStatus(4);
			defeito.setDataEncerrado(date);
			
			this.entityManager.merge(defeito);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Defeito> listaDefeitosAntigos() {
		
		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.dataAbertura < CURRENT_DATE AND d.status = 0");
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}
		
	}

}
