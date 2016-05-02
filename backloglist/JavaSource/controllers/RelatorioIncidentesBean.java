package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import entidades.Defeito;
import model.RelatorioIncidentesServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class RelatorioIncidentesBean {

	@EJB
	private RelatorioIncidentesServico relatorioIncidentesServico;

	private List<Defeito> defeitosNaoEncerrado;

	private List<Defeito> defeitosEncerrados;

	public RelatorioIncidentesBean() {

	}	

	public List<Defeito> listarDefeitosNaoEncerradosAdm() {

		defeitosNaoEncerrado = this.relatorioIncidentesServico.listaDefeitosStatusAdm(false);

		return defeitosNaoEncerrado;

	}

	public List<Defeito> listarDefeitoEncerradosAdm() {

		defeitosEncerrados = this.relatorioIncidentesServico.listaDefeitosStatusAdm(true);

		return defeitosEncerrados;

	}

	public void atualizaDefeitoEncerrado(FileUploadEvent event) {

		UploadedFile file = event.getFile();

		try {

			this.relatorioIncidentesServico.importaDefeitosEncerradosDQTT(file);

			JSFUtil.addInfoMessage("Lote carregado com sucesso");

		} catch (Exception e) {
			
			JSFUtil.addErrorMessage(e.getMessage());
			
		}

	}



}
