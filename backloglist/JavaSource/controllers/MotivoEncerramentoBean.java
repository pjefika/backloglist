package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.MotivoEncerramento;
import model.MotivoEncerramentoServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class MotivoEncerramentoBean {
	
	private MotivoEncerramento motivoEncerramento;
	
	private List<MotivoEncerramento> listaDeMotivos;
	
	@EJB
	private MotivoEncerramentoServico motivoEncerramentoServico;

	public MotivoEncerramentoBean() {
		
		this.motivoEncerramento = new MotivoEncerramento();	
		
	}
	
	public void listarMotivosEncerramentos() {
		
		this.listaDeMotivos = this.motivoEncerramentoServico.listarMotivoEncerramento();		
		
	}
	
	public void cadastrarMotivosEncerramento() {
		
		try {			
			this.motivoEncerramentoServico.cadastrarMotivosEncerramento(this.motivoEncerramento);
			JSFUtil.addInfoMessage("Motivo cadastrado com sucesso!");
			
			this.motivoEncerramento = new MotivoEncerramento();
			
		} catch (Exception e) {
			
			JSFUtil.addErrorMessage(e.getMessage());
			
		}
		
	}
	
	public void editarMotivoEncerramento() {
		
		try {
			
			this.motivoEncerramentoServico.editarMotivoEncerramento(this.motivoEncerramento);
			
			JSFUtil.addInfoMessage("Motivo alterado com sucesso!");
			
			//this.motivoEncerramento = new MotivoEncerramento();
			
		} catch (Exception e) {
			
			JSFUtil.addErrorMessage(e.getMessage());
			
		}
		
	}

	public MotivoEncerramento getMotivoEncerramento() {
		return motivoEncerramento;
	}

	public void setMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
		this.motivoEncerramento = motivoEncerramento;
	}

	public List<MotivoEncerramento> getListaDeMotivos() {
		return listaDeMotivos;
	}

	public void setListaDeMotivos(List<MotivoEncerramento> listaDeMotivos) {
		this.listaDeMotivos = listaDeMotivos;
	}

}
