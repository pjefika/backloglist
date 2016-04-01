package entidades;

public enum StatusDefeito {
	
	ABERTO("Defeito aberto"),
	EMTRATAMENTO("Sendo tratada pelo colaborador"),
	ENCERRADO("Defeito encerrado"),
	ENVIADOACAMPO("Defeito enviado a campo pelo sistema");
	
	private String status;
	
	private StatusDefeito(String status) {
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
