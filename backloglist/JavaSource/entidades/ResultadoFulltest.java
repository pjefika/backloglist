package entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_ResultadoFulltest_Defeito")
public class ResultadoFulltest {
	
	@Id	
	private String id;
	
	private String rede;
	
	public ResultadoFulltest() {
		
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRede() {
		return rede;
	}

	public void setRede(String rede) {
		this.rede = rede;
	}
}
