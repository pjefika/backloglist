package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import entidades.Defeito;
import entidades.DefeitoIntegracao;
import entidades.TipoLogIntegracao;
import entidades.TipoStatus;
import model.AtendimentoServico;
import model.ImportServicoNew;
import model.RotinasServicos;
import util.JSFUtil;

@ManagedBean
@ApplicationScoped
@Singleton
public class PainelDefeitosBean {


	public List<Defeito> listaDefeitos;	

	Timer timerBuscaDefeitosAtivos = new Timer();
	Timer timerRemoveDefeitoAntigo = new Timer();
	Timer timerVoltaDefeitoParaFila = new Timer();
	Timer timerFulltestDefeitosImportados = new Timer();

	TimerTask buscaDefeitoAtivos = new TimerTask() {

		@Override
		public void run() {

			buscarDefeitosAtivos();

		}
	};

	TimerTask removeDefeitoAntigo = new TimerTask() {

		@Override
		public void run() {

			removeDefeitoAntigo();

		}
	};

	TimerTask voltaDefeitoParaFila = new TimerTask() {

		@Override
		public void run() {

			voltaDefeito();

		}
	};

	TimerTask FulltestDefeitosImportados = new TimerTask() {

		@Override
		public void run() {

			consultaSS();

		}
	};

	@EJB
	private AtendimentoServico atendimentoServico;	

	@EJB
	private RotinasServicos rotinasServicos;

	@EJB
	private ImportServicoNew importServicoNew;

	public PainelDefeitosBean() {

	}

	@PostConstruct	
	public void init() {

		buscarDefeitosAtivos();

		timerBuscaDefeitosAtivos.scheduleAtFixedRate(buscaDefeitoAtivos, 65000, 65000);
		timerRemoveDefeitoAntigo.scheduleAtFixedRate(removeDefeitoAntigo, 30000, 30000);
		timerVoltaDefeitoParaFila.scheduleAtFixedRate(voltaDefeitoParaFila, 5000, 5000);
		timerFulltestDefeitosImportados.scheduleAtFixedRate(FulltestDefeitosImportados, 30000, 30000);

	}	


	public void buscarDefeitosAtivos() {

		this.listaDefeitos = this.atendimentoServico.listarDefeitosAtivos();											

	}	

	public void removeDefeitoAntigo() {

		List<Defeito> listaDefeitosAntigos = new ArrayList<Defeito>();

		listaDefeitosAntigos = this.atendimentoServico.listaDefeitosAntigos();

		this.atendimentoServico.removeDefeitoAntigo(listaDefeitosAntigos);

		for (Defeito defeito : listaDefeitosAntigos) {

			this.listaDefeitos.remove(defeito);

		}

	}

	public void voltaDefeito() {

		this.rotinasServicos.voltarDefeitoParaFila(this.rotinasServicos.listarDefeitosAssumidos());		

	}	

	public void removeDefeitoAoAssumir(Defeito defeito) {

		Defeito status = new Defeito();		

		try {

			status = this.atendimentoServico.consultarSS(defeito.getSs());

			TipoStatus statusValue = status.getStatus();

			if (statusValue.equals(TipoStatus.EMTRATAMENTO)) {

				this.listaDefeitos.remove(defeito);

			}

		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}	

	public void consultaSS() {

		DefeitoIntegracao defeitosIntegracao = new DefeitoIntegracao();

		try {

			defeitosIntegracao = this.atendimentoServico.consultarSSIntegracao().get(0);

			this.atendimentoServico.consultarSS(defeitosIntegracao.getSs());

			this.importServicoNew.salvaLogIntegracao(defeitosIntegracao, TipoLogIntegracao.DEFEITOEXISTENTE);

		} catch (Exception e) {

			if (!defeitosIntegracao.getInstancia().isEmpty()) {
				
				realizarFulltest(defeitosIntegracao);
				
			}
			
		}	

	}

	public void realizarFulltest(DefeitoIntegracao defeitoIntegracao) {

		try {
			//O mesmo está fazendo de um em um em threads, não há necessidade de trocar de status ao realizar fulltest;
			//this.importServicoNew.trocaStatusDefeitoIntegracao(defeitoIntegracao);
			this.importServicoNew.fulltest(defeitoIntegracao);

		} catch (Exception e) {			

			e.printStackTrace();

		}

	}

	public List<Defeito> getListaDefeitos() {
		return listaDefeitos;
	}

	public void setListaDefeitos(List<Defeito> listaDefeitos) {
		this.listaDefeitos = listaDefeitos;
	}

}
