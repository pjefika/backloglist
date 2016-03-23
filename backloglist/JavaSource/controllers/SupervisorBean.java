package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.Supervisor;
import model.SupervisorServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class SupervisorBean {
	
	private Supervisor supervisor;
	
	@EJB
	private SupervisorServico supervisorServico;

	public SupervisorBean() {
		
		this.supervisor = new Supervisor();
		
	}
	
	public void cadastrarSupervisor() {
		
		try {
			this.supervisorServico.cadastrarSupervisor(this.supervisor);
			JSFUtil.addInfoMessage("Cadastrado com sucesso");
			this.supervisor = new Supervisor();		
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	public List<Supervisor> listarSupervisores() {
		
		return this.supervisorServico.listarSupervisores();		
		
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
}
