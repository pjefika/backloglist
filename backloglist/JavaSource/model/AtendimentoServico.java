package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.ComentariosDefeitos;
import entidades.Defeito;
import entidades.LogDefeito;
import entidades.MotivoEncerramento;
import entidades.TipoLog;
import entidades.TipoStatus;
import entidades.Usuario;

@Stateless
public class AtendimentoServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public AtendimentoServico() {

	}
	/*
	 * Montar lista com todos os defeitos ativos.
	 * */
	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosAtivos() {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1");
			query.setParameter("param1", TipoStatus.ABERTO);
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
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status =:param2");
			query.setParameter("param1", usuario);
			query.setParameter("param2", TipoStatus.EMTRATAMENTO);
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

		defeito.setStatus(TipoStatus.EMTRATAMENTO);
		defeito.setUsuario(usuario);
		this.entityManager.merge(defeito);

		LogDefeito log = new LogDefeito(defeito, TipoLog.ASSUMIR, usuario);

		this.entityManager.persist(log);

		return usuario;

	}


	/*
	 * Finalizar atendimento do defeito.
	 * */	
	public void encerrarDefeito(Defeito defeito, Usuario usuario) throws Exception {


		try {

			defeito.getMotivoEncerramento().getMotivo().isEmpty();

			Date date = new Date();

			defeito.setStatus(TipoStatus.ENCERRADO);
			defeito.setDataEncerrado(date);

			this.entityManager.merge(defeito);			

			LogDefeito log = new LogDefeito(defeito, TipoLog.ENCERRAR, usuario);

			this.entityManager.persist(log);
		} catch (Exception e) {

			throw new Exception("Por favor selecione o motivo, se não existir motivo contate o administrador!");

		}



	}

	public void enviarCampo(Defeito defeito, Usuario usuario) {

		Date date = new Date();

		defeito.setStatus(TipoStatus.ENVIADOACAMPO);
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

			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1 AND d.usuario =:param2 AND d.status =:param3");
			query.setParameter("param1", ss);
			query.setParameter("param2", usuario);
			query.setParameter("param3", TipoStatus.EMTRATAMENTO);

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
		} catch (NoResultException e) {
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

		defeito.setStatus(TipoStatus.ABERTO);
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

			defeito.setStatus(TipoStatus.VENCIDOSLA);
			defeito.setDataEncerrado(date);

			this.entityManager.merge(defeito);

			Usuario usuario = new Usuario();

			usuario = null;

			LogDefeito log = new LogDefeito(defeito, TipoLog.VENCIDO, usuario);

			this.entityManager.persist(log);

		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listaDefeitosAntigos() {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1 AND CURRENT_DATE > d.dataSLATriagem");
			query.setParameter("param1", TipoStatus.ABERTO);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listarRelatorioDoUsuario(Usuario usuario, TipoStatus tipoStatus) {

		try {			
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status =:param2 AND d.dataDeIntegracao > CURRENT_DATE");
			query.setParameter("param1", usuario);
			query.setParameter("param2", tipoStatus);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();			
		}		

	}

	public void inserirComentario(Defeito defeito, String detalhes) throws Exception {


		if (!detalhes.isEmpty()){
			
			ComentariosDefeitos comentariosDefeitos = new ComentariosDefeitos();

			Date dataDeAgr = new Date();

			comentariosDefeitos.setDefeito(defeito);
			comentariosDefeitos.setComentario(detalhes);
			comentariosDefeitos.setUsuario(defeito.getUsuario());
			comentariosDefeitos.setData(dataDeAgr);

			this.entityManager.persist(comentariosDefeitos);


		}else{

			throw new Exception("Por favor preencha o campo comentario!");

		}

	}

}
