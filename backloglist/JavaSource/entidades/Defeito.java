package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="backloglist_Defeito")
public class Defeito{

	@Id
	@NotEmpty
	@Length(min=8, max=10, message="Padr�o: 8-AAAAAA")
	private String ss;
	
	@ManyToOne
	private Tipificacao tipificacao;
	
	private String instancia;
	
	private Date dataAbertura;

	private Date dataVencimento;
	
	private Date dataDeIntegracao;

	private Date dataEncerrado;
	
	@Enumerated(EnumType.STRING)
	private TipoStatus status;
	
	@ManyToOne
	private UsuarioEfika usuario;

	@OneToOne
	private MotivoEncerramento motivoEncerramento;

	@OneToMany(mappedBy="defeito")
	private List<LogDefeito> logs;
	
	@OneToMany(mappedBy="defeito", fetch=FetchType.EAGER)
	private List<ComentariosDefeitos> comentarios;
	
	private Boolean encerradoAdm;
	
	private Boolean encerradoDQTT;
	
	private String resultadoFulltest;
	
	private Date dataDQTT;
	
	public Defeito() {

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

	public UsuarioEfika getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEfika usuario) {
		this.usuario = usuario;
	}

	public MotivoEncerramento getMotivoEncerramento() {
		return motivoEncerramento;
	}

	public void setMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
		this.motivoEncerramento = motivoEncerramento;
	}

	public List<LogDefeito> getLogs() {
		return logs;
	}

	public void setLogs(List<LogDefeito> logs) {
		this.logs = logs;
	}	

	public Date getDataEncerrado() {
		return dataEncerrado;
	}

	public void setDataEncerrado(Date dataEncerrado) {
		this.dataEncerrado = dataEncerrado;
	}	

	public Date getDataDeIntegracao() {
		return dataDeIntegracao;
	}

	public void setDataDeIntegracao(Date dataDeIntegracao) {
		this.dataDeIntegracao = dataDeIntegracao;
	}	

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public List<ComentariosDefeitos> getComentarios() {
		return comentarios;
	}


	public void setComentarios(List<ComentariosDefeitos> comentarios) {
		this.comentarios = comentarios;
	}

	public Boolean getEncerradoAdm() {
		return encerradoAdm;
	}


	public void setEncerradoAdm(Boolean encerradoAdm) {
		this.encerradoAdm = encerradoAdm;
	}

	public Boolean getEncerradoDQTT() {
		return encerradoDQTT;
	}


	public void setEncerradoDQTT(Boolean encerradoDQTT) {
		this.encerradoDQTT = encerradoDQTT;
	}	

	public String getResultadoFulltest() {
		return resultadoFulltest;
	}

	public void setResultadoFulltest(String resultadoFulltest) {
		this.resultadoFulltest = resultadoFulltest;
	}

	public Date getDataDQTT() {
		return dataDQTT;
	}
	
	public String dataDQTTFormatada() {
		
		SimpleDateFormat formmater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
		String dataFormatada;

		if (getDataDQTT() == null) {
			
			dataFormatada = "";			
			
		}else{
			
			dataFormatada = formmater.format(getDataDQTT());
			
		}

		return dataFormatada;
		
	}

	public void setDataDQTT(Date dataDQTT) {
		this.dataDQTT = dataDQTT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ss == null) ? 0 : ss.hashCode());
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
		Defeito other = (Defeito) obj;
		if (ss == null) {
			if (other.ss != null)
				return false;
		} else if (!ss.equals(other.ss))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Defeito [ss=" + ss + "]";
	}

}
