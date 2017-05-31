package entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Tipificacao_Defeito")
public class Tipificacao extends AbstractEnit{
	
	private String nomeTipificacao;
	
	public Tipificacao() {

	}

	public String getNomeTipificacao() {
		return nomeTipificacao;
	}

	public void setNomeTipificacao(String nomeTipificacao) {
		this.nomeTipificacao = nomeTipificacao;
	}

}
