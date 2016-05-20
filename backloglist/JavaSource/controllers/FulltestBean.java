package controllers;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import entidades.DefeitoIntegracao;
import entidades.TipoLogIntegracao;
import model.AtendimentoServico;
import model.ImportServicoNew;

@ManagedBean
@ApplicationScoped
@Singleton
public class FulltestBean {

	Timer timerFulltestDefeitosImportados1 = new Timer();
	Timer timerFulltestDefeitosImportados2 = new Timer();
	Timer timerExcluiDefeitosParados = new Timer();

	TimerTask FulltestDefeitosImportados1 = new TimerTask() {

		@Override
		public void run() {

			try {

				consultaSS1();

			} catch (NullPointerException e) {

				//System.out.println(e.getMessage());

			}

		}
	};

	TimerTask FulltestDefeitosImportados2 = new TimerTask() {

		@Override
		public void run() {

			try {

				consultaSS2();

			} catch (NullPointerException e) {

				//System.out.println(e.getMessage());

			}

		}
	};
	
	TimerTask ExcluiDefeitosParados = new TimerTask() {

		@Override
		public void run() {

			try {

				excluirDefeitoParado();

			} catch (NullPointerException e) {

				//System.out.println(e.getMessage());

			}

		}
	};

	@EJB
	private AtendimentoServico atendimentoServico;	

	@EJB
	private ImportServicoNew importServicoNew;

	@PostConstruct
	public void init() {

		timerFulltestDefeitosImportados1.scheduleAtFixedRate(FulltestDefeitosImportados1, 1000, 1000);
		timerFulltestDefeitosImportados2.scheduleAtFixedRate(FulltestDefeitosImportados2, 1200, 1200);
		timerExcluiDefeitosParados.schedule(ExcluiDefeitosParados, 12000, 12000);

	}

	public FulltestBean() {

	}

	public void iniciaBean() {		

	}	
	
	/**
	 * Primeira Task
	 **/

	public void consultaSS1() throws NullPointerException{
		
		DefeitoIntegracao defeitosIntegracao1 = new DefeitoIntegracao();

		try {

			defeitosIntegracao1 = this.atendimentoServico.consultarSSIntegracao().get(0);
						
			this.atendimentoServico.consultarSS(defeitosIntegracao1.getSs());

			this.importServicoNew.salvaLogIntegracao(defeitosIntegracao1, TipoLogIntegracao.DEFEITOEXISTENTE);

		} catch (Exception e) {
			
			if (!defeitosIntegracao1.getInstancia().isEmpty()) {
				
				realizarFulltest1(defeitosIntegracao1);

			}

		}

	}

	public void realizarFulltest1(DefeitoIntegracao defeitoIntegracao) {

		try {
			this.importServicoNew.trocaStatusDefeitoIntegracao(defeitoIntegracao);
			this.importServicoNew.fulltest(defeitoIntegracao);

		} catch (Exception e) {			

			e.printStackTrace();

		}

	}
	
	
	
	/**
	 * Segunda Task
	 **/
	public void consultaSS2() throws NullPointerException{
		
		DefeitoIntegracao defeitosIntegracao2 = new DefeitoIntegracao();

		try {

			defeitosIntegracao2 = atendimentoServico.consultarSSIntegracao().get(0);
						
			this.atendimentoServico.consultarSS(defeitosIntegracao2.getSs());

			this.importServicoNew.salvaLogIntegracao(defeitosIntegracao2, TipoLogIntegracao.DEFEITOEXISTENTE);

		} catch (Exception e) {
			
			if (!defeitosIntegracao2.getInstancia().isEmpty()) {
								
				realizarFulltest2(defeitosIntegracao2);

			}
			
		}

	}

	public void realizarFulltest2(DefeitoIntegracao defeitoIntegracao) {

		try {
			this.importServicoNew.trocaStatusDefeitoIntegracao(defeitoIntegracao);
			this.importServicoNew.fulltest(defeitoIntegracao);

		} catch (Exception e) {			

			e.printStackTrace();

		}

	}
	
	public void excluirDefeitoParado() {
		
		this.importServicoNew.removeDefeitosParados(this.importServicoNew.listaDefeitosParados());
		
	}

}
