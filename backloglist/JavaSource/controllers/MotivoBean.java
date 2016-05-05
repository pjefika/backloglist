package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.MotivoEncerramento;
import model.MotivoServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class MotivoBean {
	
	private MotivoEncerramento motivoEncerramento;
	
	private MotivoEncerramento motivoEncerramento2;
		
	private List<MotivoEncerramento> listaMotivos;
	
	@EJB
	private MotivoServico motivoServico;
	
	public MotivoBean() {
		this.motivoEncerramento = new MotivoEncerramento();
		this.motivoEncerramento2 = new MotivoEncerramento();
	}
	
	public void cadastrarMotivoEncerramento() {
		
		this.motivoServico.cadastrarMotivoEncerramento(this.motivoEncerramento);
		
		this.motivoEncerramento = new MotivoEncerramento();
		
		JSFUtil.addInfoMessage("Motivo cadastrado com sucesso.");
		
	}
	
	public List<MotivoEncerramento> listaMotivoEncerramento() {
				
		return this.motivoServico.listaMotivoEncerramento();
				
	}
	
	public void editarMotivoEncerramento() {
						
		this.motivoServico.editarMotivoEncerramento(this.motivoEncerramento);
		
		JSFUtil.addInfoMessage("Motivo editado com sucesso.");
		
	}

	public MotivoEncerramento getMotivoEncerramento() {
		return motivoEncerramento;
	}

	public void setMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
		this.motivoEncerramento = motivoEncerramento;
	}

	public MotivoEncerramento getMotivoEncerramento2() {
		return motivoEncerramento2;
	}

	public void setMotivoEncerramento2(MotivoEncerramento motivoEncerramento2) {
		this.motivoEncerramento2 = motivoEncerramento2;
	}

	public List<MotivoEncerramento> getListaMotivos() {
		return listaMotivos;
	}

	public void setListaMotivos(List<MotivoEncerramento> listaMotivos) {
		this.listaMotivos = listaMotivos;
	}
	
}
