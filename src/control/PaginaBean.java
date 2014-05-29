package control;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "pagina")
@SessionScoped
public class PaginaBean {

	public String paginaFramework() throws Exception {

		return "/navegacao/framework.xhtml?faces-redirect=true";
	}
	public String paginaHibernate() throws Exception {
		
		return "/navegacao/hibernate.xhtml?faces-redirect=true";
	}
	public String paginaJsf() throws Exception {
		
		return "/navegacao/jsf.xhtml?faces-redirect=true";
	}
	public String paginaPrimeface() throws Exception {
		
		return "primeface.xhtml?faces-redirect=true";
	}
	public String paginaAnalista() throws Exception {
		
		return "/navegacao/analista.xhtml?faces-redirect=true";
	}
	public String paginaCss() throws Exception {
		
		return "/navegacao/analista.xhtml?faces-redirect=true";
	}
	
}
