package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import entidades.Supervisor;
import entidades.Usuario;
import model.UsuarioServico;
import util.JSFUtil;

@ManagedBean
@RequestScoped
public class UsuarioBean {
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;
	
	private String senha1;
	private String senha2;
	
	private Usuario usuario;
	
	private Supervisor supervisor;
		
	@EJB
	private UsuarioServico usuarioServico;

	public UsuarioBean() {
		
		this.usuario = new Usuario();
		this.supervisor = new Supervisor();
	}
	
	public void cadastrarOperador() {
		
		try {
			this.usuario.setSenha("12345");
			this.usuarioServico.cadastrarOperador(this.usuario, this.supervisor);
			JSFUtil.addInfoMessage("Usuário criado com sucesso, senha padrão: 1234");
			
			this.usuario = new Usuario();
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	public List<Usuario> listarUsuarios() {
		
		return this.usuarioServico.listarUsuarios();
		
	}
	
	public List<Supervisor> listarSupervisor() {
		
		return this.usuarioServico.listarSupervisor();
		
	}
	
	public void mudarSenha() {		
		
		try {
			this.usuarioServico.mudarSenha(this.senha1, this.senha2, this.sessao.getUsuario());
			JSFUtil.addInfoMessage("Senha trocada com sucesso, utilize-a em seu proximo Logon");
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}
		
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}

}
