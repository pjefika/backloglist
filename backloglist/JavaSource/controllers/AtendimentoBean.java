package controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import entidades.Defeito;
import entidades.LogDefeito;
import entidades.MotivoEncerramento;
import entidades.TipoStatus;
import model.AtendimentoServico;
import model.LogDefeitoServico;
import util.JSFUtil;

@ManagedBean
@ViewScoped
public class AtendimentoBean {

	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;

	private Defeito defeito;

	private List<Defeito> defeitosDisponiveis;

	private LogDefeito logDefeito;

	@EJB
	private AtendimentoServico atendimentoServico;

	@EJB
	private LogDefeitoServico logDefeitoServico;

	private String detalhesDefeito;

	public AtendimentoBean() {

		this.defeito = new Defeito();
		this.logDefeito = new LogDefeito();		

	}	

	/*
	 * Lista todos os defeitos ativos.
	 * */
	public List<Defeito> listarDefeitosAtivos() {

		List<Defeito> lista = this.atendimentoServico.listarDefeitosAtivos();

		this.defeitosDisponiveis = lista;

		return lista;

	}

	/*
	 * Lista defeitos atribuidos ao colaborador.
	 * */
	public List<Defeito> listarDefeitosColaborador() {

		return this.atendimentoServico.listarDefeitosColaborador(this.sessao.getUsuario());

	}

	/*
	 * Assumi o defeito para a matricula.
	 * */
	public void assumirDefeito(Defeito defeito){

		try {
			this.sessao.setUsuario(this.atendimentoServico.assumirDefeito(defeito, sessao.getUsuario()));
			JSFUtil.addInfoMessage("Defeito " + defeito.getSs() + " associado com sucesso!");
			this.redirecionaDetalhesDefeito(defeito);
						
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}

	}
	
	public void redirecionaDetalhesDefeito(Defeito defeito) {

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + "/detalhe_defeito.jsf?ss=" + defeito.getSs());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	/*
	 * Finalizar atendimento do defeito.
	 * */	
	public String encerrarDefeito() {		

		try {		

			this.atendimentoServico.encerrarDefeito(this.defeito, this.sessao.getUsuario());
			JSFUtil.addInfoMessage("Defeito " + this.defeito.getSs() + " encerrado com sucesso!");
			return "busca_defeitos_carrinho.jsf";
		} catch (Exception e) {		
			JSFUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}

	/*
	 * Envia defeito para campo.
	 * */
	public String enviarCampo() {

		try {
			this.atendimentoServico.enviarCampo(this.defeito, this.sessao.getUsuario());
			JSFUtil.addInfoMessage("Defeito " + this.defeito.getSs() + " enviado a campo.");
			return "busca_defeitos_carrinho.jsf";
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
			return null;
		}

	}

	public Defeito consultarDefeitoOperadorPorSS() {

		try {

			this.defeito = atendimentoServico.consultarDefeitoOperadorPorSS(this.defeito.getSs(), this.sessao.getUsuario());
			return this.defeito;

		} catch (Exception e) {

			JSFUtil.addErrorMessage(e.getMessage());
			return null;

		}

	}

	public List<MotivoEncerramento> listarMotivosEncerramentos() {

		return this.atendimentoServico.listarMotivoEncerramento();

	}

	public String voltarDefeitoParaFila() {

		this.atendimentoServico.voltarDefeitoParaFila(this.defeito, this.sessao.getUsuario());
		JSFUtil.addInfoMessage("Defeito retornou a fila!");
		return "busca_defeitos_carrinho.jsf";

	}

	public void realizarFulltest() {

		this.atendimentoServico.realizarFulltest(this.defeito, this.sessao.getUsuario());
		JSFUtil.addInfoMessage("Comando acionado, por favor aguarde!");

	}	

	public Integer contagemRelatorioUsuario(TipoStatus tipoStatus) {

		return this.atendimentoServico.listarRelatorioDoUsuario(this.sessao.getUsuario(), tipoStatus).size();

	}

	public Integer contagemDefeitoEncerradoDQTT() {

		return this.atendimentoServico.listarDefeitosEncerradosDQTT(this.sessao.getUsuario()).size();

	}

	public void inserirComentario() {

		try {

			this.atendimentoServico.inserirComentario(this.defeito, this.detalhesDefeito);
			this.detalhesDefeito = null;

		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}		

	}

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}

	public Defeito getDefeito() {
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
		this.defeito = defeito;
	}

	public LogDefeito getLogDefeito() {
		return logDefeito;
	}

	public void setLogDefeito(LogDefeito logDefeito) {
		this.logDefeito = logDefeito;
	}

	public List<Defeito> getDefeitosDisponiveis() {
		return defeitosDisponiveis;
	}

	public void setDefeitosDisponiveis(List<Defeito> defeitosDisponiveis) {
		this.defeitosDisponiveis = defeitosDisponiveis;
	}

	public String getDetalhesDefeito() {
		return detalhesDefeito;
	}

	public void setDetalhesDefeito(String detalhesDefeito) {
		this.detalhesDefeito = detalhesDefeito;
	}

}
