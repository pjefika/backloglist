package entidades;

public enum TipoStatus {
	
	ABERTO("0 - Defeito aberto"),
	EMTRATAMENTO("1 - Sendo tratado pelo colaborador"),
	ENCERRADO("2 - Defeito encerrado"),
	ENVIADOACAMPO("3 - Defeito enviado a campo"),
	VENCIDOSLA("4 - Vencido SLA de triagem");
	
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
