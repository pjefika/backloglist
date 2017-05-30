package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Tipificacao_Defeito")
public class Tipificacao {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String nomeTipificacao;
	
	public Tipificacao() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeTipificacao() {
		return nomeTipificacao;
	}

	public void setNomeTipificacao(String nomeTipificacao) {
		this.nomeTipificacao = nomeTipificacao;
	}

}
