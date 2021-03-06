package controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import entidades.ComentariosDefeitos;
import entidades.ComentariosDefeitosTv;
import entidades.Defeito;
import entidades.DefeitoTv;
import entidades.Lote;
import entidades.LoteTv;
import entidades.MotivoEncerramento;
import entidades.TipoStatus;
import model.AtendimentoServico;
import model.MotivoServico;
import model.RelatorioServico;

@SuppressWarnings("serial")
@ManagedBean(name="relatorioBean")
@ViewScoped
public class RelatorioBean implements Serializable{

	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;

	private List<Defeito> listaDeDefeito;

	private List<DefeitoTv> listaDeDefeitosTv;

	private List<MotivoEncerramento> motivos;

	private PieChartModel GraficoStatus;

	private PieChartModel GraficoMotivos;

	private Date dataInicio;

	private Date dataFim;

	@EJB
	private RelatorioServico relatorioServico;

	@EJB
	private MotivoServico motivoEncerramentoServico;

	@EJB
	private AtendimentoServico atendimentoServico;

	@PostConstruct
	public void init() {
		criarCharts();
	}

	private void criarCharts() {
		criarGraficoStatus();
		criarGraficoMotivos(this.motivoEncerramentoServico.listaMotivoEncerramentoPorStatus());
	}

	public RelatorioBean() {

	}

	public void listarDefeitosEncerrados() {

		Calendar cal = Calendar.getInstance();

		if (this.dataInicio == null) {

			Date date = new Date();

			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, -24);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);

			this.dataInicio = cal.getTime();

		}

		if (this.dataFim == null) {

			this.dataFim = this.dataInicio;

			cal.setTime(this.dataFim);
			cal.add(Calendar.DATE, 1);

			this.dataFim = cal.getTime();

		}else{

			cal.setTime(this.dataFim);
			cal.add(Calendar.DATE, 1);

			this.dataFim = cal.getTime();

		}

		this.listaDeDefeito = this.relatorioServico.listarDefeitosEncerrado(this.dataInicio, this.dataFim);	

	}

	public String listarComentariosDefeito(Defeito defeito){

		List<ComentariosDefeitos> listaComents = this.atendimentoServico.listarComentariosDefeito(defeito);

		String comentsConcat = "";

		for (ComentariosDefeitos comentariosDefeitos : listaComents) {

			comentsConcat = comentariosDefeitos.dataFormatada() + " " + comentariosDefeitos.getUsuario().getLogin() + ": " + comentariosDefeitos.getComentario() + "\n " + comentsConcat;

		}

		return comentsConcat;

	}

	public String listarComentariosDefeitoTv(DefeitoTv defeito){

		List<ComentariosDefeitosTv> listaComents = this.atendimentoServico.listarComentariosDefeitoTv(defeito);		

		String comentsConcat = "";

		for (ComentariosDefeitosTv comentariosDefeitos : listaComents) {

			comentsConcat = comentariosDefeitos.dataFormatada() + " " + comentariosDefeitos.getUsuario().getLogin() + ": " + comentariosDefeitos.getComentario() + "\n " + comentsConcat;

		}

		return comentsConcat;

	}




	public void listarDefeitosEncerradosTv() {

		Calendar cal = Calendar.getInstance();

		if (this.dataInicio == null) {

			Date date = new Date();

			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, -23);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, -1);

			this.dataInicio = cal.getTime();

		}

		if (this.dataFim == null) {

			this.dataFim = this.dataInicio;

			cal.setTime(this.dataFim);
			cal.add(Calendar.DATE, 1);

			this.dataFim = cal.getTime();

		}else{

			cal.setTime(this.dataFim);
			cal.add(Calendar.DATE, 1);

			this.dataFim = cal.getTime();

		}

		this.listaDeDefeitosTv = this.relatorioServico.listarDefeitosEncerradoTv(this.dataInicio, this.dataFim);

	}


	private void criarGraficoStatus() 
	{
		GraficoStatus = new PieChartModel();

		GraficoStatus.set("Aberto: " + listarStatus(TipoStatus.ABERTO), listarStatus(TipoStatus.ABERTO));
		GraficoStatus.set("Aberto Tv: " + listarStatusTv(TipoStatus.ABERTO), listarStatusTv(TipoStatus.ABERTO));
		GraficoStatus.set("Em Tratamento: " + listarStatus(TipoStatus.EMTRATAMENTO), listarStatus(TipoStatus.EMTRATAMENTO));
		GraficoStatus.set("Encerrado Operador/Sistema: " + listarStatus(TipoStatus.ENCERRADO), listarStatus(TipoStatus.ENCERRADO));
		GraficoStatus.set("Encerrado Operador/Sistema Tv: " + listarStatusTv(TipoStatus.ENCERRADO), listarStatusTv(TipoStatus.ENCERRADO));
		GraficoStatus.set("Enviado a campo: " + listarStatus(TipoStatus.ENVIADOACAMPO), listarStatus(TipoStatus.ENVIADOACAMPO));		
		GraficoStatus.set("Encerrado DQTT: " + listarDefeitoEncerradosDQTT(), listarDefeitoEncerradosDQTT());		
		GraficoStatus.set("Encerrado DQTT Tv: " + listarDefeitoEncerradosDQTTtv(), listarDefeitoEncerradosDQTTtv());

		GraficoStatus.setTitle("Monitoramento");
		GraficoStatus.setLegendPosition("w");
		GraficoStatus.setSeriesColors("003245, 005466, 007486, 0095A7, 00B6C7");

	}

	private void criarGraficoMotivos(List<MotivoEncerramento> motivos) {
		GraficoMotivos = new PieChartModel();

		for (MotivoEncerramento motivoEncerramento : motivos) {

			GraficoMotivos.set(motivoEncerramento.getMotivo() + ": " + this.listarDefeitosPorMotivo(motivoEncerramento), this.listarDefeitosPorMotivo(motivoEncerramento));

		}	

		GraficoMotivos.setTitle("Encerrados por Motivos");
		GraficoMotivos.setLegendPosition("w");
		GraficoMotivos.setSeriesColors("003245, 004356, 005466, 006476, 007486, 008597, 0095A7, 005B7, 0086C7, 00C6D7");

	}

	public Integer listarStatus(TipoStatus tipoStatus) {

		return this.relatorioServico.ListarTodoOsDefeitos(tipoStatus).size();

	}	

	public Integer listarStatusTv(TipoStatus tipoStatus) {

		return this.relatorioServico.ListarTodoOsDefeitosTv(tipoStatus).size();

	}

	public Integer listarDefeitosPorMotivo(MotivoEncerramento motivo) {			

		return this.relatorioServico.listarDefeitosPorMotivo(motivo).size() + this.relatorioServico.listarDefeitosPorMotivoTv(motivo).size();

	}

	public Integer listarDefeitoEncerradosDQTT() {

		return this.relatorioServico.listarDefeitoEncerradosDQTT().size();

	}

	public Integer listarDefeitoEncerradosDQTTtv() {

		return this.relatorioServico.listarDefeitoEncerradosDQTTtv().size();

	}

	public List<Lote> listarLotes() {

		return this.relatorioServico.listarLotes();

	}

	public List<LoteTv> listarLotesTv() {

		return this.relatorioServico.listarLotesTv();

	}

	public Integer listarDefeitoIntegracaoPorLote(String nomeLote, String acao) {		

		return this.relatorioServico.listarDefeitoIntegracaoPorLote(nomeLote, acao).size();

	}

	public Integer listarLogsDefeitosIntegrados(String lote, String acao) {

		return this.relatorioServico.listarLogsDefeitosIntegrados(lote, acao).size();

	}

	public Integer listarFulltestDefeitoEmTratamento(String nomeLote) {

		return this.relatorioServico.listarFulltestDefeitoEmTratamento(nomeLote).size();

	}

	public String formatarData(Date data) {	

		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		return formatar.format(data);

	}

	/**
	 * Getters 'n Setters
	 * */
	public List<Defeito> getListaDeDefeito() {
		return listaDeDefeito;
	}

	public void setListaDeDefeito(List<Defeito> listaDeDefeito) {
		this.listaDeDefeito = listaDeDefeito;
	}

	public List<MotivoEncerramento> getMotivos() {
		return motivos;
	}

	public void setMotivos(List<MotivoEncerramento> motivos) {
		this.motivos = motivos;
	}

	public PieChartModel getGraficoStatus() {
		return GraficoStatus;
	}

	public void setGraficoStatus(PieChartModel graficoStatus) {
		GraficoStatus = graficoStatus;
	}

	public PieChartModel getGraficoMotivos() {
		return GraficoMotivos;
	}

	public void setGraficoMotivos(PieChartModel graficoMotivos) {
		GraficoMotivos = graficoMotivos;
	}

	public LoginBean getSessao() {
		return sessao;
	}

	public void setSessao(LoginBean sessao) {
		this.sessao = sessao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<DefeitoTv> getListaDeDefeitosTv() {
		return listaDeDefeitosTv;
	}

	public void setListaDeDefeitosTv(List<DefeitoTv> listaDeDefeitosTv) {
		this.listaDeDefeitosTv = listaDeDefeitosTv;
	}

}
