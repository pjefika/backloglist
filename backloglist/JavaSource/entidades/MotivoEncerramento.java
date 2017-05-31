package entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="backloglist_MotivoEncerramento_Defeito")
public class MotivoEncerramento extends AbstractEnit{

	@NotEmpty
	private String motivo;

	private Integer status;
	
	@NotEmpty
	private String codEncerramento;

	public MotivoEncerramento() {

	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCodEncerramento() {
		return codEncerramento;
	}

	public void setCodEncerramento(String codEncerramento) {
		this.codEncerramento = codEncerramento;
	}

}
