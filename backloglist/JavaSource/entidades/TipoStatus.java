package entidades;

public enum TipoStatus {
	
	ABERTO("ABERTO"),
	EMTRATAMENTO("EMTRATAMENTO"),
	ENCERRADO("ENCERRADO"),
	ENVIADOACAMPO("ENVIADOACAMPO"),
	VENCIDOSLA("VENCIDOSLA");
	
	private String status;
	
	private TipoStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
	
	
}
