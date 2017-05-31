package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Lote_Integracao")
public class Lote extends AbstractEnit{
	
	private String nome;
	
	private Date horaIntegrado;
	
	private TipoStatus status;
	
	public Lote() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getHoraIntegrado() {
		return horaIntegrado;
	}

	public void setHoraIntegrado(Date horaIntegrado) {
		this.horaIntegrado = horaIntegrado;
	}

	public TipoStatus getStatus() {
		return status;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}	

}
