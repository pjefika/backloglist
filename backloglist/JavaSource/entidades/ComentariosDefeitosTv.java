package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Comentarios_Defeitos_Tv")
public class ComentariosDefeitosTv {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Lob
	private String comentario;
	
	@ManyToOne
	private DefeitoTv defeito;
	
	@ManyToOne
	private UsuarioEfika usuario;
	
	private Date data;

	public ComentariosDefeitosTv() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}	

}
