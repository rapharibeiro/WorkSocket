package control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import persistence.CargoDao;
import persistence.FuncionarioDao;
import persistence.UsuarioDao;
import entity.Cargo;
import entity.Endereco;
import entity.Funcionario;
import entity.Usuario;

@ManagedBean(name = "controle")
@SessionScoped
public class Controle {

	private Funcionario funcionario;
	private Cargo cargo;
	private Endereco endereco;
	private Usuario usuario;
	private String novaSenha = "";
	private String confSenha = "";

	private List<SelectItem> listaCargo;
	private List<Funcionario> listaFun;

public Controle() {

		funcionario = new Funcionario();
		cargo = new Cargo();
		endereco = new Endereco();
		usuario = new Usuario();
	}

	public String paginaCadastrar() throws Exception {

		setFuncionario(new Funcionario());
		setEndereco(new Endereco());
		setCargo(new Cargo());

		new CargoDao().buscarTodos();

		return "cadastrar.xhtml?faces-redirect=true";
	}

	public String paginaConsultar() throws Exception {

		setFuncionario(new Funcionario());
		setEndereco(new Endereco());
		setCargo(new Cargo());
		setListaFun(new ArrayList<Funcionario>());
		setUsuario(new Usuario());

		listaFun = new FuncionarioDao().buscarTodos();

		return "consultar.xhtml?faces-redirect=true";
	}

	public String paginaIndex()throws Exception {
		
		setUsuario(new Usuario());
		
		return "/index.xhtml?faces-redirect=true"; 
	}
	
	public String paginaMudarSenha()throws Exception {

		setUsuario(new Usuario());
		FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String login = session.getAttribute("login").toString();
		usuario.setLogin(login);
        
        
		return "mudarsenha.xhtml?faces-redirect=true"; 
	}
	
	public String paginaEditar(Funcionario fun) throws Exception {

		funcionario = new FuncionarioDao().buscarPorId(fun.getIdFuncionario());
		endereco = funcionario.getEndereco();
		cargo = funcionario.getCargo();

		return "atualizar.xhtml?faces-redirect=true";

	}

	public String salvar() throws Exception {
		try {

			boolean flag = true; // para fazer teste de validação
			Pattern letra = Pattern.compile("[0-9]");// permite apenas letras
			Pattern vaEmail = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");// permite
			Matcher nome = letra.matcher(funcionario.getNome());
			Matcher cidade = letra.matcher(endereco.getCidade());
			Matcher email = vaEmail.matcher(funcionario.getEmail());

			if (!email.find()) {// validação da escrita do email
				FacesContext.getCurrentInstance().addMessage(
						"msg:inEmail",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema", "E-mail inválido!"));
				flag = false;
			}// validação para testar se o email já existe no banco
			if (new FuncionarioDao().buscarEmail(funcionario.getEmail()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						"msg:inEmail",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"E-mail já cadastrado!"));
				flag = false;
			}
			if (nome.find()) {// validação da escrita do nome
				FacesContext.getCurrentInstance().addMessage(
						"msg:inNome",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Campo nome não pode conter números!"));
				flag = false;
			}
			if (cidade.find()) {// validação da escrita da cidade
				FacesContext.getCurrentInstance().addMessage(
						"msg:inCidade",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Campo cidade não pode conter números!"));
				flag = false;
			}
		
			if (funcionario.getAdmissao().after(new Date())) {// validação da
																// data
				FacesContext.getCurrentInstance().addMessage(
						"msg:inAdmissao",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Data não pode ser maior que a atual!"));
				flag = false;
			}
			if (funcionario.getNascimento().after(new Date())) {// validação da
				// data
				FacesContext.getCurrentInstance().addMessage(
						"msg:inNascimento",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Data não pode ser maior que a atual!"));
				flag = false;
			}
			if (flag) {// validações ok, pode ser cadastrado
				// relacionamentos
				
				usuario = new Usuario();
				usuario.setLogin(funcionario.getEmail());
				usuario.setSenha(md5("adm"));
				usuario.setNivel(cargo.getIdCargo());
//				new UsuarioDao().cadastrar(usuario);
				
				funcionario.setUsuario(usuario);
				funcionario.setCargo(cargo);
				funcionario.setEndereco(endereco);
				endereco.setFuncionario(funcionario);
				
				new FuncionarioDao().save(funcionario);
				
				setFuncionario(new Funcionario());// zerar a pagina do cadastrado
				setEndereco(new Endereco());
				setCargo(new Cargo());
				setUsuario(new Usuario());
				
				FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação do Sistema",
								"Funcionario cadastrado com sucesso!"));
				return "cadastro.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Informação do Sistema","Erro ao salvar Funcionario!"));
		}
		return "cadastro.xhtml?faces-redirect=true";
	}
	

	public String atualizar() throws Exception {
		try {

			boolean flag = true;
			Pattern letra = Pattern.compile("[0-9]");
			Pattern vaEmail = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
			Matcher nome = letra.matcher(funcionario.getNome());
			Matcher cidade = letra.matcher(endereco.getCidade());
			Matcher email = vaEmail.matcher(funcionario.getEmail());

			if (!email.find()) {
				FacesContext.getCurrentInstance().addMessage("msg:inEmail",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema", "E-mail inválido!"));
				flag = false;
			}

			if (funcionario.getIdFuncionario() == null) {

				if (new FuncionarioDao().buscarEmail(funcionario.getEmail()) != null) {
					FacesContext.getCurrentInstance().addMessage("msg:inEmail",
							new FacesMessage(FacesMessage.SEVERITY_WARN,"Informação do Sistema",
									"E-mail já cadastrado!"));
					flag = false;
				}
			} else {

				Funcionario fuc = new FuncionarioDao().buscarPorId(funcionario.getIdFuncionario());

				if (funcionario.getEmail().equals(fuc.getEmail())) {
					flag = true;
				} else {
					if (new FuncionarioDao().buscarEmail(funcionario.getEmail()) != null) {
						FacesContext.getCurrentInstance().addMessage("msg:inEmail",
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Informação do Sistema","E-mail já cadastrado!"));
						flag = false;
					}
				}
			}
			if (nome.find()) {
				FacesContext.getCurrentInstance().addMessage(
						"msg:inNome",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Campo nome não pode conter números!"));
				flag = false;
			}
			if (cidade.find()) {
				FacesContext.getCurrentInstance().addMessage(
						"msg:inCidade",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Campo cidade não pode conter números!"));
				flag = false;
			}
			if (funcionario.getAdmissao().after(new Date())) {
				FacesContext.getCurrentInstance().addMessage(
						"msg:inAdmissao",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Informação do Sistema",
								"Data não pode ser maior que a atual!"));
				flag = false;
			}
			if (flag) {
				funcionario.setCargo(cargo);
				funcionario.setEndereco(endereco);
				endereco.setFuncionario(funcionario);

				new FuncionarioDao().update(funcionario);

				setFuncionario(new Funcionario());
				setEndereco(new Endereco());
				setCargo(new Cargo());
				setUsuario(new Usuario());

				FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Informação do Sistema","Funcionario atualizado com sucesso!"));
				return "atualizar.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Informação do Sistema","Erro ao atualizar Funcionario!"));
		}
		return "consultar.xhtml?faces-redirect=true";
	}
	
	
	public String deletar(Funcionario fun) throws Exception {
		
		try {
			if (fun != null) {// caso o funcionario exista, é excluido
				new FuncionarioDao().delete(fun);
				
				setListaFun(new ArrayList<Funcionario>());// lista de novo para
				
				listaFun = new FuncionarioDao().buscarTodos();
				FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Informação do Sistema","Funcionário excluído com sucesso!"));
				return "consultar.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Informação do Sistema","Nenhum Funcionário selecionado"));
		}	
		return "consultar.xhtml?faces-redirect=true";
	}
	
	public static String md5(String senha){  
		String sen = "";  
		MessageDigest md = null;  
		try {  
			md = MessageDigest.getInstance("MD5");  
		} catch (NoSuchAlgorithmException e) {  
			e.printStackTrace();  
		}  
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
		sen = hash.toString(16); 
		
		return sen;  
	}  
	
	public String logar()throws Exception {
		
		String senha = md5(usuario.getSenha());
		Usuario user = new Usuario();
		usuario.setSenha(senha);
		user = new UsuarioDao().buscaUsuario(usuario.getLogin(), usuario.getSenha());
		
		try {
			if(user != null ){ 
				if(user.getSenha().equals(senha))
				{
				
				 FacesContext facesContext = FacesContext.getCurrentInstance();
		         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		         session.setAttribute("nivel", user.getNivel());// session is always NULL
		         session.setAttribute("login", user.getLogin());
		         String nivel = session.getAttribute("nivel").toString();
	
				 if(nivel.equals("5")){
					FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação do Sistema","Usuário cadastrado com sucesso!"));
			   	 	return "/navegacao/analista.xhtml?faces-redirect=true";
				 }
				 if(nivel.equals("6")){
					 FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação do Sistema","Usuário cadastrado com sucesso!"));
					 return "/navegacao/jsf.xhtml?faces-redirect=true";
				 }
			}else{
				FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Informação do Sistema","Senha inválida!"));
			}
			}} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Informação do Sistema","Login ou senha inválida!"));
			}
		return  "index.xhtml?faces-redirect=true";
	}
	
public String mudarSenha()throws Exception {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String login = "";
        login = session.getAttribute("login").toString();
        
        Usuario user = new Usuario();
        
        try {
			if(login != null)
			{
        	   	user = new UsuarioDao().buscarlogin(login);
        	   	
        	   	String senhaEsq = md5(usuario.getSenha());
        	   	usuario.setSenha(senhaEsq);
        	
				if(user.getSenha().equals(usuario.getSenha()))
				{
					if(getNovaSenha().equals(getConfSenha()))
					{
						usuario = new Usuario();
						
						usuario.setIdUsuario(user.getIdUsuario());
						usuario.setLogin(user.getLogin());
						usuario.setNivel(user.getNivel());
						usuario.setSenha(md5(getNovaSenha()));
						
						new UsuarioDao().cadastrar(usuario);
						
						FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Informação do Sistema","Senha alterada com sucesso!"));
					}else{
						FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_WARN,
    						"Informação do Sistema","Senha incompatível!"));
        			}
        		}else{
        			FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_WARN,
    						"Informação do Sistema","Senha incorreta!"));
					}
				}
       	}catch (Exception e) {
        		e.printStackTrace();	
				FacesContext.getCurrentInstance().addMessage("msg",	new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Informação do Sistema","Usuário inválido!"));
       	}
    	return "index.xhtml?faces-redirect=true";
	}	
	
	public String logout()throws Exception
	{
		  FacesContext fc = FacesContext.getCurrentInstance();    
		  HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);    
		  session.invalidate();     
		 return "index.xhtml?faces-redirect=true";
	}


	public List<String> getlistaEstado() {
		List<String> listaUfs = new ArrayList<String>();
		listaUfs.add("RJ");
		listaUfs.add("SP");
		listaUfs.add("MG");
		listaUfs.add("ES");
		listaUfs.add("BA");
		listaUfs.add("RS");
		listaUfs.add("CE");
		listaUfs.add("PB");
		listaUfs.add("PR");
		listaUfs.add("DF");

		return listaUfs;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	// add e listando os cargos registados, e passando para a web
	public List<SelectItem> getListaCargo() {
		listaCargo = new ArrayList<SelectItem>();
		try {
			for (Cargo c : new CargoDao().buscarTodos()) {
				SelectItem si = new SelectItem(c.getIdCargo(), c.getNomeCargo());
				listaCargo.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCargo;
	}

	public void setListaCargo(List<SelectItem> listaCargo) {
		this.listaCargo = listaCargo;
	}

	public List<Funcionario> getListaFun() {
		return listaFun;
	}

	public void setListaFun(List<Funcionario> listaFun) {
		this.listaFun = listaFun;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfSenha() {
		return confSenha;
	}

	public void setConfSenha(String confSenha) {
		this.confSenha = confSenha;
	}
	
	
	
	public String paginaFramework() throws Exception {

		return "navegacao/framework.xhtml?faces-redirect=true";
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
