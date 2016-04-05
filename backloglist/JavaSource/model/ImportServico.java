package model;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Defeito;
import entidades.LogIntegracao;
import entidades.Tipificacao;
import entidades.TipoLogIntegracao;
import entidades.TipoStatus;

@Stateless
public class ImportServico {


	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	@EJB
	private AtendimentoServico atendimentoServico;

	public ImportServico() {

	}

	public void importarDefeito(Defeito defeito, Tipificacao tipificacao) throws Exception {

		try {

			this.atendimentoServico.consultarSS(defeito.getSs());
			salvaLogIntegracao(defeito, TipoLogIntegracao.DEFEITOEXISTENTE);

		} catch (Exception e) {

			Long diferenca = defeito.getDataVencimento().getTime() - defeito.getDataAbertura().getTime();
					
			Double porcentagem = 0.025;
			diferenca = (long) (diferenca * porcentagem);
						
			diferenca = defeito.getDataAbertura().getTime() + diferenca;
			
			Date sla = new Date(diferenca);

			Date dataIntegracao = new Date();

			defeito.setDataSLATriagem(sla);
			defeito.setDataDeIntegracao(dataIntegracao);
			defeito.setStatus(TipoStatus.ABERTO);

			defeito.setTipificacao(tipificacao);

			this.entityManager.persist(defeito);
			salvaLogIntegracao(defeito, TipoLogIntegracao.INTEGRADO);

		}
	}

	public Tipificacao acaoTipificacao(String nomeTipificacao) {

		Query query = this.entityManager.createQuery("FROM Tipificacao t WHERE t.nomeTipificacao =:param1");
		query.setParameter("param1", nomeTipificacao);

		Tipificacao tipificacao = new Tipificacao();	

		try {

			tipificacao = (Tipificacao) query.getSingleResult();

		} catch (Exception e) {

			tipificacao.setNomeTipificacao(nomeTipificacao);			
			this.entityManager.persist(tipificacao);		

		}

		return tipificacao;		

	}
	
	public void salvaLogIntegracao(Defeito defeito, TipoLogIntegracao tipoLogIntegracao) {
				
		LogIntegracao logIntegracao = new LogIntegracao();
		Date data = new Date();
		
		logIntegracao.setDefeito(defeito);
		logIntegracao.setHoraAcao(data);
		logIntegracao.setTipoLogIntegracao(tipoLogIntegracao);
		
		
		this.entityManager.persist(logIntegracao);
		
	}

}
