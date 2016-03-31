package model;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Defeito;

@Stateless
public class ImportServico {


	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public ImportServico() {

	}

	public void importarDefeito(Defeito defeito) throws Exception {
		
			try {
				
				AtendimentoServico atServico = new AtendimentoServico();
				atServico.consultarSS(defeito.getSs());
								
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
				
				this.entityManager.persist(defeito);
				
			}
	}


	public Date testeSLA(Date inicio, Date fim) {		

		Long dif = fim.getTime() - inicio.getTime();		

		Double percent = 0.25;

		dif = (long) (dif * percent);

		dif = inicio.getTime() + dif;

		Date sla = new Date(dif);

		System.out.println(sla);

		return sla;

	}

}
