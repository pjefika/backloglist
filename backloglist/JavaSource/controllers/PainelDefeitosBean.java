package controllers;

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
	
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			
			buscarDefeitosAtivos();
			
		}
	};
	
	
	
	@EJB
	private AtendimentoServico atendimentoServico;	
			
	public PainelDefeitosBean() {

	}
	
	@PostConstruct	
	public void init() {
		
		buscarDefeitosAtivos();
		
		timer.scheduleAtFixedRate(task, 60000, 60000);
		
	}	
	
	
	public void buscarDefeitosAtivos() {
		
		this.listaDefeitos = this.atendimentoServico.listarDefeitosAtivos();
											
		System.out.println(this.listaDefeitos.size());
		
	}
	

	public void removeDefeito(Defeito defeito) {

		Defeito status = new Defeito();		

		try {
			status = this.atendimentoServico.consultarSS(defeito.getSs());

			int statusValue = status.getStatus();

			if (statusValue != 0) {

				buscarDefeitosAtivos();
				
			}

		} catch (Exception e) {
			// TODO: handle exception
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}

	public List<Defeito> getListaDefeitos() {
		return listaDefeitos;
	}

	public void setListaDefeitos(List<Defeito> listaDefeitos) {
		this.listaDefeitos = listaDefeitos;
	}

}
