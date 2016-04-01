package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import entidades.Defeito;
import entidades.LogDefeito;
import entidades.MotivoEncerramento;
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

	public AtendimentoBean() {

		this.defeito = new Defeito();
		this.logDefeito = new LogDefeito();		

	}

	/*
	 * Aciona o servico para cadastrar o defeito.
	 * */	
	public void CadastrarDefeito() {

		try {
			this.atendimentoServico.CadastrarDefeito(this.defeito);
			JSFUtil.addInfoMessage("Defeito " + this.defeito.getSs() + " cadastrado com sucesso!");

			this.defeito = new Defeito();
		} catch (Exception e) {
			JSFUtil.addInfoMessage(e.getMessage());
		}

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
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
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

	public Integer contagemRelatorioUsuario(Integer status) {

		return this.atendimentoServico.listarRelatorioDoUsuario(this.sessao.getUsuario(), status).size();

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

}
