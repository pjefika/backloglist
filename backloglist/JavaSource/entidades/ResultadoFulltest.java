package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="backloglist_ResultadoFulltest_Defeito")
public class ResultadoFulltest {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String rede;
	
	public ResultadoFulltest() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRede() {
		return rede;
	}

	public void setRede(String rede) {
		this.rede = rede;
	}
}
