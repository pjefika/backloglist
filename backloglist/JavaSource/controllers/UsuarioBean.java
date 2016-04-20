package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import entidades.UsuarioEfika;
import model.UsuarioServico;

@ManagedBean
@RequestScoped
public class UsuarioBean {
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;
		
	private UsuarioEfika usuario;
	
		
	@EJB
	private UsuarioServico usuarioServico;

	public UsuarioBean() {
		
		this.usuario = new UsuarioEfika();
	}	
		
	public List<UsuarioEfika> listarUsuarios() {
		
		return this.usuarioServico.listarUsuarios();
		
	}	

	public UsuarioEfika getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEfika usuario) {
		this.usuario = usuario;
	}

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}

}
