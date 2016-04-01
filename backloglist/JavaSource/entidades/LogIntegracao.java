package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LogIntegracao {
	
	@Id
	@GeneratedValue
	private Integer id;	
		
	private Date horaAcao;
	
	@ManyToOne
	private Defeito defeito;
	
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

	public Defeito getDefeito() {
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
		this.defeito = defeito;
	}

	public TipoLogIntegracao getTipoLogIntegracao() {
		return tipoLogIntegracao;
	}

	public void setTipoLogIntegracao(TipoLogIntegracao tipoLogIntegracao) {
		this.tipoLogIntegracao = tipoLogIntegracao;
	}

}
