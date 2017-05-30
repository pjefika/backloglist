package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="backloglist_MotivoEncerramento_Defeito")
public class MotivoEncerramento{

	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty
	private String motivo;

	private Integer status;
	
	@NotEmpty
	private String codEncerramento;

	public MotivoEncerramento() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {		
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MotivoEncerramento other = (MotivoEncerramento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MotivoEncerramento [id=" + id + "]";
	}

}
