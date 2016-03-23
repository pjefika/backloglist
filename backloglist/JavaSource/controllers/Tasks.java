package controllers;

import javax.faces.bean.ManagedProperty;

public class Tasks implements Runnable{
	
	@ManagedProperty(value="#{painelDefeitosBean}")
	private PainelDefeitosBean painel;
	
	
	@Override
	public void run() {		
		
		this.painel.buscarDefeitosAtivos();
		
	}

}
