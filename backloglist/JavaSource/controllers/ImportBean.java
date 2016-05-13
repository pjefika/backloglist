package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import entidades.Defeito;
import entidades.Lote;
import entidades.Tipificacao;
import model.ImportServico;
import model.ImportServicoNew;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class ImportBean {
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;
	
	private Defeito defeito;
	
	private Tipificacao tipificacao;
	
	private String nomeTipificacao;
	
	@EJB
	private ImportServico importServico;
	
	@EJB
	private ImportServicoNew importServicoNew;

	public ImportBean() {
		
		this.defeito = new Defeito();
		this.tipificacao = new Tipificacao();
		
	}
		
	public void importarDefeito() {
		
		try {
			
			this.defeito.setTipificacao(this.tipificacao);
			
			this.importServico.importarDefeito(this.defeito);
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}		
		
	}
	
	public void validaTipificacao() {
		
		this.tipificacao = this.importServico.acaoTipificacao(this.nomeTipificacao);
				
	}
	
	public void uploadFile(FileUploadEvent event) {
		
		UploadedFile file = event.getFile();
				
		try {
			
			this.importServicoNew.salvaLote(file, this.sessao.getUsuario());
			
			JSFUtil.addInfoMessage("Lote carregado com sucesso");
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	public void pararLote(Lote lote) {
		
		this.importServicoNew.pararLote(this.importServicoNew.listaLoteEspecifico(lote));
				
		JSFUtil.addInfoMessage("Lote parado com sucesso.");
		
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

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}
}
