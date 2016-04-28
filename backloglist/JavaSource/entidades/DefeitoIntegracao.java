package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DefeitoIntegracao {
	
	@Id
	private String ss;
	
	@ManyToOne
	private Tipificacao tipificacao;
	
	private String instancia;
	
	private Date dataAbertura;
	
	private Date dataVencimento;
	
	@Enumerated(EnumType.STRING)
	private TipoStatus status;

	public DefeitoIntegracao() {
		
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}	

	public Tipificacao getTipificacao() {
		return tipificacao;
	}

	public void setTipificacao(Tipificacao tipificacao) {
		this.tipificacao = tipificacao;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public TipoStatus getStatus() {
		return status;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}	
}
