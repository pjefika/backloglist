package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Log_Defeito_Tv_3")
public class LogDefeitoTv extends AbstractEnit{

	private Date horaAcao;

	@Enumerated(EnumType.STRING)
	private TipoLog acao;

	@ManyToOne
	private DefeitoTv defeito;	

	@ManyToOne
	private UsuarioEfika usuario;

	public LogDefeitoTv() {

		this.horaAcao = new Date();

	}

	public LogDefeitoTv(DefeitoTv defeito, TipoLog acao, UsuarioEfika usuario) {
		
		this.defeito = defeito;
		this.acao = acao;
		this.usuario = usuario;
		this.horaAcao = new Date();
		
	}

	public Date getHoraAcao() {
		return horaAcao;
	}

	public void setHoraAcao(Date horaAcao) {
		this.horaAcao = horaAcao;
	}

	public TipoLog getAcao() {
		return acao;
	}

	public void setAcao(TipoLog acao) {
		this.acao = acao;
	}

	public DefeitoTv getDefeito() {
		return defeito;
	}

	public void setDefeito(DefeitoTv defeito) {
		this.defeito = defeito;
	}

	public UsuarioEfika getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEfika usuario) {
		this.usuario = usuario;
	}	

}
