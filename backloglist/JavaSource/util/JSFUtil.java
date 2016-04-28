package util;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static void addInfoMessage(String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary(msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void addErrorMessage(String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary(msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void addWarnMessage(String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		message.setSummary(msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static String gerarStringAleatoria(Integer nrStrings){


		java.util.Random r = new java.util.Random();
		
		char[] goodChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h','i', 'j', 'k','l', 'm', 'n','o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x','w',
				'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I','J', 'K','L',
				'M', 'N','O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','1',
				'2', '3', '4', '5', '6', '7', '8', '9'};
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < nrStrings; i++) {
			sb.append(goodChar[r.nextInt(goodChar.length)]);
		}

		return sb.toString(); 
	}
	
	public static void tratamentoString() {
		
		
		
	}
	
}
