package entidades;

public enum TipoLog{

	ASSUMIR("ASSUMIR"), 
	ENCERRAR("ENCERRAR"),
	VOLTOUFILA("VOLTOUFILA"),
	SEMCONTATO("SEMCONTATO"),
	FULLTEST("FULLTEST"),
	ENVIADOCAMPO("ENVIADOCAMPO"),
	VENCIDO("VENCIDO");

	private String acao;

	private TipoLog(String acao) {
		this.setAcao(acao);
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
	
	@Override
	public String toString() {
		return acao;
	}
	
}
