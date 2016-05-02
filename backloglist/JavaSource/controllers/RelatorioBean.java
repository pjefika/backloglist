package controllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import entidades.Defeito;
import entidades.Lote;
import entidades.MotivoEncerramento;
import entidades.TipoStatus;
import model.MotivoEncerramentoServico;
import model.RelatorioServico;

@SuppressWarnings("serial")
@ManagedBean(name="relatorioBean")
@ViewScoped
public class RelatorioBean implements Serializable{

	@ManagedProperty(value="#{loginBean}")
	private LoginBean sessao;

	private List<Defeito> listaDeDefeito;

	private List<MotivoEncerramento> motivos;

	private PieChartModel GraficoStatus;

	private PieChartModel GraficoMotivos;

	private Date dataInicio;

	private Date dataFim;

	@EJB
	private RelatorioServico relatorioServico;

	@EJB
	private MotivoEncerramentoServico motivoEncerramentoServico;

	@PostConstruct
	public void init() {
		criarCharts();
	}

	private void criarCharts() {
		criarGraficoStatus();
		criarGraficoMotivos(this.motivoEncerramentoServico.listarMotivoEncerramento());
	}

	public RelatorioBean() {

	}

	public void listarDefeitosEncerradosPorSupervisor() {

		Calendar cal = Calendar.getInstance();

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

		this.listaDeDefeito = this.relatorioServico.listarDefeitosEncerradosPorSupervisor(this.dataInicio, this.dataFim);

	}


	private void criarGraficoStatus() 
	{
		GraficoStatus = new PieChartModel();

		GraficoStatus.set("Aberto: " + listarStatus(TipoStatus.ABERTO), listarStatus(TipoStatus.ABERTO));
		GraficoStatus.set("Em Tratamento: " + listarStatus(TipoStatus.EMTRATAMENTO), listarStatus(TipoStatus.EMTRATAMENTO));
		GraficoStatus.set("Encerrado: " + listarStatus(TipoStatus.ENCERRADO), listarStatus(TipoStatus.ENCERRADO));
		GraficoStatus.set("Enviado a campo: " + listarStatus(TipoStatus.ENVIADOACAMPO), listarStatus(TipoStatus.ENVIADOACAMPO));		
		GraficoStatus.set("Encerrado DQTT: " + listarDefeitoEncerradosDQTT(), listarDefeitoEncerradosDQTT());

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

	public Integer listarDefeitosPorMotivo(MotivoEncerramento motivo) {			

		return this.relatorioServico.listarDefeitosPorMotivo(motivo).size();

	}
	
	public Integer listarDefeitoEncerradosDQTT() {
		
		return this.relatorioServico.listarDefeitoEncerradosDQTT().size();
		
	}

	public List<Lote> listarLotes() {

		return this.relatorioServico.listarLotes();

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

}
