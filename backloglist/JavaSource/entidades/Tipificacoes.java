package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tipificacoes {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String tipificacao;

	public Tipificacoes() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipificacao() {
		return tipificacao;
	}

	public void setTipificacao(String tipificacao) {
		this.tipificacao = tipificacao;
	}	

}
