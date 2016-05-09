package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.UsuarioEfika;
import model.LoginServico;
import util.JSFUtil;
import webservices.Usuario;

import java.io.Serializable;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoginBean implements Serializable{
		
	private UsuarioEfika usuario;
	
	private Usuario usuarioWS;
	
	private String senha;
	
	@EJB
	private LoginServico servicoLogin;
	
	private boolean logado;
	
	public LoginBean() {
		this.usuario = new UsuarioEfika();
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
								
		return this.usuarioWS.getNivel() > 5;
		
	}
	
	public Boolean is_SubAdmin(){
		
		return this.usuarioWS.getNivel() > 4;
		
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
			this.usuario = new UsuarioEfika();
		}
	}
	
	public void validaSubAdmin() {
		try {
			this.validarLogin();
			
			FacesContext fc = FacesContext.getCurrentInstance();
						
			if(!this.is_SubAdmin()){
				ConfigurableNavigationHandler nav  = (ConfigurableNavigationHandler) 
						fc.getApplication().getNavigationHandler();
				nav.performNavigation("restrito.jsf");
			}
		} catch (Exception e) {
			this.usuario = new UsuarioEfika();
		}
	}
	
	public String logar() {
		
		try {		
			
			this.usuarioWS = this.servicoLogin.buscaLoginWS(this.usuario.getLogin());
			this.servicoLogin.autenticaLogin(this.usuarioWS, this.senha);
			
			this.logado = true;			
			return "index.jsf"; 
			
		} catch (Exception e) {
			
			JSFUtil.addErrorMessage(e.getMessage());
			this.usuario = new UsuarioEfika();
			return "";
			
		}
		
	}
	
	public void deslogar() {
		
		this.usuario = new UsuarioEfika();
		this.logado = false;
		
	}

	public UsuarioEfika getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEfika usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuarioWS() {
		return usuarioWS;
	}

	public void setUsuarioWS(Usuario usuarioWS) {
		this.usuarioWS = usuarioWS;
	}

}
