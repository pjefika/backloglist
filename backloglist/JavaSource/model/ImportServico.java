package model;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Defeito;
import entidades.Tipificacao;

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

		} catch (Exception e) {

			Long diferenca = defeito.getDataVencimento().getTime() - defeito.getDataAbertura().getTime();
			Double porcentagem = 0.25;
			diferenca = (long) (diferenca * porcentagem);
			diferenca = defeito.getDataAbertura().getTime() + diferenca;
			Date sla = new Date(diferenca);	

			Date dataIntegracao = new Date();	

			defeito.setDataSLATriagem(sla);
			defeito.setDataDeIntegracao(dataIntegracao);
			defeito.setStatus(0);

			defeito.setTipificacao(tipificacao);

			this.entityManager.persist(defeito);

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

}
