package controllers;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.Defeito;
import model.ImportServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class ImportBean {
	
	private Defeito defeito;	
	
	@EJB
	private ImportServico importServico;

	public ImportBean() {
		
		this.defeito = new Defeito();
		
	}
	
	public void importarDefeito() {
		
		try {
			this.importServico.importarDefeito(this.defeito);
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}		
		
	}
	
	public void ronaldo(String oi){
		System.out.println(oi);
	}
	
	public Defeito getDefeito() {
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
		this.defeito = defeito;
	}

}
