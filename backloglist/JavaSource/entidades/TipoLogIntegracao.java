package entidades;

public enum TipoLogIntegracao {
	
	INTEGRADO("Defeito integrado com sucesso"),
	DEFEITOEXISTENTE("Defeito ja existe na base"),
	ERROINTEGRADO("Erro ao integrar defeito"),
	NEGATIVAFULLTEST("Full test negativo");
	
	private String acao;
	
	private TipoLogIntegracao(String acao) {		
		this.setAcao(acao);		
	}
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
