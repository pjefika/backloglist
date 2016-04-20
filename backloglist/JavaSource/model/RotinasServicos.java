package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Defeito;
import entidades.LogDefeito;
import entidades.TipoLog;
import entidades.TipoStatus;
import entidades.UsuarioEfika;

@Stateless
public class RotinasServicos {
	
	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;
	
	public RotinasServicos() {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosAssumidos(){
		
		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1");
			query.setParameter("param1", TipoStatus.EMTRATAMENTO);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<LogDefeito> listarLogsDefeitoAssumido(Defeito defeito) {

		try {			
			Query query = this.entityManager.createQuery("FROM LogDefeito l WHERE l.defeito =:param1 AND l.acao =:param2 ORDER BY l.horaAcao ASC");
			query.setParameter("param1", defeito);
			query.setParameter("param2", TipoLog.ASSUMIR);			
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<LogDefeito>();
		}
		
	}
	
	public void voltarDefeitoParaFila(List<Defeito> defeitos) {	
				
		Date hraAtual = new Date();
		
		List<LogDefeito> listaLogsDefeito;
		
		for (Defeito defeito : defeitos) {
			
			listaLogsDefeito = listarLogsDefeitoAssumido(defeito);
			
			LogDefeito ultimoDefeito = listaLogsDefeito.get(listaLogsDefeito.size()-1);
			
			Long umaHraMseg = (long) 3600000;
			
			Long cal = hraAtual.getTime() - ultimoDefeito.getHoraAcao().getTime();
						
			if (cal > umaHraMseg) {
				
				UsuarioEfika usuario = new UsuarioEfika();
				usuario = null;
				
				LogDefeito log = new LogDefeito(defeito, TipoLog.VOLTOUFILA, usuario);
				
				this.entityManager.persist(log);
				
				defeito.setStatus(TipoStatus.ABERTO);
				defeito.setUsuario(null);
				defeito.setMotivoEncerramento(null);
				this.entityManager.merge(defeito);
				
			}
			
		}

	}

}
