package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="backloglist_Comentarios_Defeitos")
public class ComentariosDefeitos {
	
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Lob
	private String comentario;
	
	@ManyToOne
	private Defeito defeito;
	
	@ManyToOne
	private UsuarioEfika usuario;
	
	private Date data;

	public ComentariosDefeitos() {
		
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

	public Defeito getDefeito() {
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
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
