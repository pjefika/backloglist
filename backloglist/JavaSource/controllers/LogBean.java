package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import entidades.LogDefeito;
import entidades.LogDefeitoTv;
import model.LogServico;
import model.UsuarioServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class LogBean {

	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;

	private List<LogDefeito> listaLogDefeito;
	
	private List<LogDefeitoTv> listaLogDefeitoTv;

	private String loginOUss;
	
	private String loginOUssTv;

	@EJB
	private LogServico logServico;

	@EJB
	private UsuarioServico usuarioServico;

	public LogBean() {

	}

	public void listarLogDefeitoSS() {		

		try {
			
			this.listaLogDefeito = this.logServico.listarLogDefeitoSS(this.loginOUss, this.sessao.getUsuario());
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}

	public void listarLogDefeitoSSTv() {		

		try {
			this.listaLogDefeitoTv = this.logServico.listarLogDefeitoSSTv(this.loginOUssTv, this.sessao.getUsuario());
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}

	public List<LogDefeito> getListaLogDefeito() {
		return listaLogDefeito;
	}

	public void setListaLogDefeito(List<LogDefeito> listaLogDefeito) {
		this.listaLogDefeito = listaLogDefeito;
	}

	public String getLoginOUss() {
		return loginOUss;
	}

	public void setLoginOUss(String loginOUss) {
		this.loginOUss = loginOUss;
	}

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}

	public List<LogDefeitoTv> getListaLogDefeitoTv() {
		return listaLogDefeitoTv;
	}

	public void setListaLogDefeitoTv(List<LogDefeitoTv> listaLogDefeitoTv) {
		this.listaLogDefeitoTv = listaLogDefeitoTv;
	}

	public String getLoginOUssTv() {
		return loginOUssTv;
	}

	public void setLoginOUssTv(String loginOUssTv) {
		this.loginOUssTv = loginOUssTv;
	}

}
