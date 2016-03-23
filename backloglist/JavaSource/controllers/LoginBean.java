package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Usuario;
import model.LoginServico;
import util.JSFUtil;

import java.io.Serializable;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoginBean implements Serializable{
		
	private Usuario usuario;
	
	@EJB
	private LoginServico servicoLogin;
	
	private boolean logado;
	
	public LoginBean() {
		this.usuario = new Usuario();
		this.logado = false;
	}
	
	public void validarLogin() {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if (!this.logado){
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler)
					fc.getApplication().getNavigationHandler();
			nav.performNavigation("index.jsf");
		}
	}
	
	public Boolean is_Admin(){
								
		return this.usuario.getNivel() > 1;
		
	}
	
	public void validaAdmin() {
		try {
			this.validarLogin();
			
			FacesContext fc = FacesContext.getCurrentInstance();
						
			if(!this.is_Admin()){
				ConfigurableNavigationHandler nav  = (ConfigurableNavigationHandler) 
						fc.getApplication().getNavigationHandler();
				nav.performNavigation("restrito.jsf");
			}
		} catch (Exception e) {
			this.usuario = new Usuario();
		}
	}
	
	public String logar() {
		
		try {
			this.servicoLogin.usuarioExiste(this.usuario.getLogin());
			this.usuario = servicoLogin.consultarLogin(usuario.getLogin(), usuario.getSenha());
			this.logado = true;
			return "index.jsf";
		} catch (Exception e) {
			JSFUtil.addWarnMessage(e.getMessage());
			this.usuario = new Usuario();
			return "";
		}
		
	}
	
	public void deslogar() {
		
		this.usuario = new Usuario();
		this.logado = false;
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
