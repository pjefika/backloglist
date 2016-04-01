package controllers;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.Defeito;
import entidades.Tipificacao;
import model.ImportServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class ImportBean {
	
	private Defeito defeito;
	
	private Tipificacao tipificacao;
	
	private String nomeTipificacao;
	
	@EJB
	private ImportServico importServico;

	public ImportBean() {
		
		this.defeito = new Defeito();
		this.tipificacao = new Tipificacao();
		
	}
		
	public void importarDefeito() {
		
		try {
			this.importServico.importarDefeito(this.defeito, this.tipificacao);
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}		
		
	}
	
	public void validaTipificacao() {
		
		this.tipificacao = this.importServico.acaoTipificacao(this.nomeTipificacao);
				
	}
	
	public Defeito getDefeito() {
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
		this.defeito = defeito;
	}

	public String getNomeTipificacao() {
		return nomeTipificacao;
	}

	public void setNomeTipificacao(String nomeTipificacao) {
		this.nomeTipificacao = nomeTipificacao;
	}

}
