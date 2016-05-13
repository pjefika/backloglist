package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Lote_Integracao")
public class Lote {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String nome;
	
	private Date horaIntegrado;
	
	private TipoStatus status;
	
	public Lote() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
