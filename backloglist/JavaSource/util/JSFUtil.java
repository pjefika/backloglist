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

}
