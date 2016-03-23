package entidades;

public enum TipoLog{

	ASSUMIR("Assumir Defeito"), 
	ENCERRAR("Encerramento Defeito"),
	VOLTOUFILA("Voltar Defeito a Fila"),
	SEMCONTATO("Sem contato"),
	FULLTEST("Comando Fulltest"),
	ENVIADOCAMPO("Enviado a Campo");

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
