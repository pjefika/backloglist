package entidades;

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
@Table(name="backloglist_Defeito_Tv")
public class DefeitoTv {
	
	@Id
	@NotEmpty
	@Length(min=8, max=10, message="Padrão: 8-AAAAAA")
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
	private List<ComentariosDefeitosTv> comentarios;
	
	private Boolean encerradoAdm = false;
	
	private Boolean encerradoDQTT;
	
	public DefeitoTv() {
		
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

	public Date getDataDeIntegracao() {
		return dataDeIntegracao;
	}

	public void setDataDeIntegracao(Date dataDeIntegracao) {
		this.dataDeIntegracao = dataDeIntegracao;
	}

	public Date getDataEncerrado() {
		return dataEncerrado;
	}

	public void setDataEncerrado(Date dataEncerrado) {
		this.dataEncerrado = dataEncerrado;
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

	public List<ComentariosDefeitosTv> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentariosDefeitosTv> comentarios) {
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

}
