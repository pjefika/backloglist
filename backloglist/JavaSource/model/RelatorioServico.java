package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import entidades.Defeito;
import entidades.DefeitoIntegracao;
import entidades.LogIntegracao;
import entidades.Lote;
import entidades.MotivoEncerramento;
import entidades.TipoLogIntegracao;
import entidades.TipoStatus;

@Stateless
public class RelatorioServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public RelatorioServico() {

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosEncerrado(Date dataInicio, Date dataFim) {	

		try {			
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.dataEncerrado BETWEEN :param1 AND :param2 AND D.status !=:param3");
			query.setParameter("param1", dataInicio, TemporalType.DATE);
			query.setParameter("param2", dataFim, TemporalType.DATE);
			query.setParameter("param3", TipoStatus.ENVIADOACAMPO);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosPorMotivo(MotivoEncerramento motivo) {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.motivoEncerramento =:param1 AND d.dataEncerrado > CURRENT_DATE");
			query.setParameter("param1", motivo);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> ListarTodoOsDefeitos(TipoStatus tipoStatus) {

		try {

			if (tipoStatus.equals(TipoStatus.ENCERRADO) || tipoStatus.equals(TipoStatus.ENVIADOACAMPO) || tipoStatus.equals(TipoStatus.VENCIDOSLA)){

				Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1 AND d.dataEncerrado > CURRENT_DATE");
				query.setParameter("param1", tipoStatus);
				return query.getResultList();

			}else{

				Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1");
				query.setParameter("param1", tipoStatus);
				return query.getResultList();

			}			

		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitoEncerradosDQTT() {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.encerradoAdm =:param1 AND d.encerradoDQTT =:param1 AND d.dataEncerrado > CURRENT_DATE");
			query.setParameter("param1", true);
			return query.getResultList();
			
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<DefeitoIntegracao> listarDefeitoIntegracaoPorLote(String nomeLote, String acao) {

		try {

			Query query = this.entityManager.createQuery("FROM DefeitoIntegracao d WHERE d.lote.nome =:param1 AND d.status =:param2");
			query.setParameter("param1", nomeLote);

			if (acao.equalsIgnoreCase("ABERTO")){

				query.setParameter("param2", TipoStatus.ABERTO);

			}else{
				
				query.setParameter("param2", TipoStatus.ENCERRADO);
				
			}
			
			return query.getResultList();

		} catch (Exception e) {

			return new ArrayList<DefeitoIntegracao>();

		}

	}

	@SuppressWarnings("unchecked")
	public List<Lote> listarLotes() {

		try {

			Query query = this.entityManager.createQuery("FROM Lote l ORDER BY l.horaIntegrado DESC");
			query.setMaxResults(5);
			return query.getResultList();

		} catch (Exception e) {

			return new ArrayList<Lote>();

		}

	}
	
	@SuppressWarnings("unchecked")
	public List<LogIntegracao> listarLogsDefeitosIntegrados(String lote, String acao) {		
		
		try {
			
			Query query = this.entityManager.createQuery("FROM LogIntegracao l WHERE l.defeitoIntegracao.lote.nome =:param1 AND l.tipoLogIntegracao =:param2");
			
			query.setParameter("param1", lote);
			
			if (acao.equalsIgnoreCase("INTEGRADO")){
				
				query.setParameter("param2", TipoLogIntegracao.INTEGRADO);
				
			}else if(acao.equalsIgnoreCase("NEGATIVAFULLTEST")){
				
				query.setParameter("param2", TipoLogIntegracao.NEGATIVAFULLTEST);
				
			}
			
			return query.getResultList();
			
		} catch (Exception e) {
			
			return new ArrayList<LogIntegracao>();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DefeitoIntegracao> listarFulltestDefeitoEmTratamento(String lote) {
		
		try {
			
			Query query = this.entityManager.createQuery("FROM DefeitoIntegracao d WHERE d.lote =:param1 AND d.status =:param2");
			query.setParameter("param1", lote);
			query.setParameter("param2", TipoStatus.EMTRATAMENTO);
			return query.getResultList(); 
			
		} catch (Exception e) {
			
			return new ArrayList<DefeitoIntegracao>();
			
		}
		
	}

}
