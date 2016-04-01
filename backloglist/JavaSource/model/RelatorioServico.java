package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import entidades.Defeito;
import entidades.MotivoEncerramento;
import entidades.TipoStatus;

@Stateless
public class RelatorioServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public RelatorioServico() {

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosEncerradosPorSupervisor(Date dataInicio, Date dataFim) {	

		try {			
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.dataEncerrado BETWEEN :param1 AND :param2");
			query.setParameter("param1", dataInicio, TemporalType.DATE);
			query.setParameter("param2", dataFim, TemporalType.DATE);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listarDefeitosPorMotivo(MotivoEncerramento motivo) {

		try {
			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.motivoEncerramento =:param1 AND d.dataEncerrado > CURRENT_DATE");
			query.setParameter("param1", motivo);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> ListarTodoOsDefeitos(TipoStatus tipoStatus) {

		try {

			if (tipoStatus.equals(TipoStatus.ENCERRADO) || tipoStatus.equals(TipoStatus.ENVIADOACAMPO) || tipoStatus.equals(TipoStatus.VENCIDOSLA)){

				Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1 AND d.dataEncerrado > CURRENT_DATE");
				query.setParameter("param1", tipoStatus);
				return query.getResultList();
				
			}else{

				Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1");
				query.setParameter("param1", tipoStatus);
				return query.getResultList();
				
			}			
			
		} catch (Exception e) {
			return new ArrayList<Defeito>();
		}

	}

}
