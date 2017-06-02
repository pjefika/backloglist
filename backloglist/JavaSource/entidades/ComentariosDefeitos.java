package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_Comentarios_Defeitos_2")
public class ComentariosDefeitos extends AbstractEnit {
		
	@Lob
	private String comentario;
	
	@ManyToOne
	private Defeito defeito;
	
	@ManyToOne
	private UsuarioEfika usuario;
	
	private Date data;

	public ComentariosDefeitos() {
		
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
	
	public String dataFormatada() {
		
		SimpleDateFormat formmater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		String dataFormatada = formmater.format(getData());

		return dataFormatada;
		
	}

	public void setData(Date data) {
		this.data = data;
	}	

}
