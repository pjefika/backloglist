package entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="backloglist_UsuarioEfika")
public class UsuarioEfika{
	
	@Id
	@NotEmpty
	private String login;
		
	@NotNull
	private Integer nivel;
			
	@OneToMany(mappedBy="usuario")
	private List<Defeito> assumidos;
	
	public UsuarioEfika() {
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}	
	
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public List<Defeito> getAssumidos() {
		return assumidos;
	}

	public void setAssumidos(List<Defeito> assumidos) {
		this.assumidos = assumidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		UsuarioEfika other = (UsuarioEfika) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + "]";
	}
	
}
