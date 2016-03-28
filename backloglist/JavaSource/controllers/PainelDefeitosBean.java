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
import model.AtendimentoServico;
import util.JSFUtil;

@ManagedBean
@ApplicationScoped
@Singleton
public class PainelDefeitosBean {
	
	public List<Defeito> listaDefeitos;	
	
	
	Timer timer = new Timer();
	Timer timerR = new Timer();
	
	TimerTask task = new TimerTask() {
		
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
	
	
	
	@EJB
	private AtendimentoServico atendimentoServico;	
			
	public PainelDefeitosBean() {

	}
	
	@PostConstruct	
	public void init() {
		
		buscarDefeitosAtivos();
		
		timer.scheduleAtFixedRate(task, 65000, 65000);
		timerR.scheduleAtFixedRate(removeDefeitoAntigo, 648000, 648000);
	}	
	
	
	public void buscarDefeitosAtivos() {
		
		this.listaDefeitos = this.atendimentoServico.listarDefeitosAtivos();											
		
	}
	

	public void removeDefeito(Defeito defeito) {

		Defeito status = new Defeito();		

		try {
			status = this.atendimentoServico.consultarSS(defeito.getSs());

			int statusValue = status.getStatus();

			if (statusValue != 0) {

				listaDefeitos.remove(defeito);
				
			}

		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}
	
	
	public void removeDefeitoAntigo() {
				
		List<Defeito> listaDefeitos = new ArrayList<Defeito>();
		
		listaDefeitos = this.atendimentoServico.listaDefeitosAntigos();
		
		this.atendimentoServico.removeDefeitoAntigo(listaDefeitos);
				
	}

	public List<Defeito> getListaDefeitos() {
		return listaDefeitos;
	}

	public void setListaDefeitos(List<Defeito> listaDefeitos) {
		this.listaDefeitos = listaDefeitos;
	}

}
