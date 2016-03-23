package webservices;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import entidades.Defeito;
import entidades.LogDefeito;
import entidades.MotivoEncerramento;
import model.AtendimentoServico;
import model.LogServico;
import model.MotivoEncerramentoServico;


@javax.jws.WebService(serviceName = "Backloglist")
public class BackloglistProxy {
	
	private Defeito retorno;
	
	@EJB
	private AtendimentoServico atendimentoServico;
	
	@EJB
	private LogServico logServico;
	
	@EJB
	private MotivoEncerramentoServico motivoServico;	

	@WebMethod(operationName = "ConsultarDetalhesSS")
	public Defeito ConsultarDetalhesSS(@WebParam(name = "ss") String ss) throws Exception {
		
		Defeito def = new Defeito();
				
		this.retorno = this.atendimentoServico.consultarSS(ss);
		
		def = this.retorno;
		
		def.setLogs(null);
		def.setUsuario(null);
		
		return def;
	}
	
	@WebMethod(operationName = "ListarLogSS")
	public List<LogDefeito> ListarLogSS(@WebParam(name = "LoginOuSs") String LoginOuSs) {
		
		
		List<LogDefeito> logs = new ArrayList<LogDefeito>();
		
		logs = this.logServico.listarLog(LoginOuSs);
		
		for (LogDefeito logDefeito : logs) {
			
			logDefeito.setDefeito(null);
			
			logDefeito.setUsuario(null);
			
		}	
				
		return logs;
		
	}
	
	@WebMethod(operationName = "ListarMotivoEncerramento")
	public List<MotivoEncerramento> ListarMotivoEncerramento() {
				
		return this.motivoServico.listarMotivoEncerramento();
		
	}
}