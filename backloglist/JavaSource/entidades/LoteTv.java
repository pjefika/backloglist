package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Lote_Integracao_Tv_3")
public class LoteTv extends AbstractEnit{
	
	private String nome;
	
	private Date horaIntegrado;
	
	private TipoStatus status;
	
	private Integer qntdeIntegrado;

	public LoteTv() {
		// TODO Auto-generated constructor stub
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

	public Integer getQntdeIntegrado() {
		return qntdeIntegrado;
	}

	public void setQntdeIntegrado(Integer qntdeIntegrado) {
		this.qntdeIntegrado = qntdeIntegrado;
	}	

}
