package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Log_Integracao")
public class LogIntegracao {
	
	@Id
	@GeneratedValue
	private Integer id;	
		
	private Date horaAcao;
		
	@ManyToOne
	private DefeitoIntegracao defeitoIntegracao;
	
	@Enumerated(EnumType.STRING)
	private TipoLogIntegracao tipoLogIntegracao;

	public LogIntegracao() {
		this.horaAcao = new Date();
	}

	public Date getHoraAcao() {
		return horaAcao;
	}

	public void setHoraAcao(Date horaAcao) {
		this.horaAcao = horaAcao;
	}

	public DefeitoIntegracao getDefeitoIntegracao() {
		return defeitoIntegracao;
	}

	public void setDefeitoIntegracao(DefeitoIntegracao defeitoIntegracao) {
		this.defeitoIntegracao = defeitoIntegracao;
	}

	public TipoLogIntegracao getTipoLogIntegracao() {
		return tipoLogIntegracao;
	}

	public void setTipoLogIntegracao(TipoLogIntegracao tipoLogIntegracao) {
		this.tipoLogIntegracao = tipoLogIntegracao;
	}

}
