package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.CargoDao;
import persistence.FuncionarioDao;
import entity.Cargo;
import entity.Endereco;
import entity.Funcionario;

@WebServlet("/Controler")
public class Controler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Controler() {
        super();
     }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String q = request.getParameter("q");
			
			if(q.equalsIgnoreCase("cadastrar")){//entrar na pagina de cadastro
				
				request.setAttribute("listaCargo", new CargoDao().buscarTodos());
				request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
			
			}else if(q.equalsIgnoreCase("inserir")){//inserir um novo funcionario

				String nome = request.getParameter("nome");
				String email = request.getParameter("email");
				String sexo = request.getParameter("sexo");
				Double salario = new Double(request.getParameter("salario"));
				String logradouro = request.getParameter("logradouro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String cpf = request.getParameter("cpf");
				
				String adm = request.getParameter("admissao");			
				String admissao[] = adm.split("/");
				GregorianCalendar cal = new GregorianCalendar(new Integer(admissao[2]), new Integer(admissao[1]) - 1,
						new Integer(admissao[0]));
				
				String nasc = request.getParameter("nascimento");			
				String nascimento[] = nasc.split("/");
				GregorianCalendar caln = new GregorianCalendar(new Integer(nascimento[2]), new Integer(nascimento[1]) - 1,
						new Integer(nascimento[0]));
				
				Funcionario f = new Funcionario(null, nome, cpf, caln.getTime(), email, sexo, cal.getTime());
				Endereco e = new Endereco(null, logradouro, cidade, estado);
				Cargo c = new Cargo(new Integer(request.getParameter("cargo")), null,salario);
				
				f.setCargo(c);
				f.setEndereco(e);
				e.setFuncionario(f);
				
				new FuncionarioDao().save(f);
				
				request.setAttribute("msg", "Cadastrado com sucesso!");
				request.setAttribute("listaCargo", new CargoDao().buscarTodos());
				request.getRequestDispatcher("index.jsp").forward(request, response);
				
			}else if(q.equalsIgnoreCase("consultar")){//listar os funcionarios
				
				FuncionarioDao fd = new FuncionarioDao();
				List<Funcionario> listafun = fd.buscarTodos();
				request.setAttribute("listafuncionario", listafun);
				request.getRequestDispatcher("consultar.jsp").forward(request, response);
				
			}else if(q.equalsIgnoreCase("editar")){//entrar na pagia para editar os dados, retornando os dados do funcionario
				
				Integer id = new Integer(request.getParameter("id"));
				FuncionarioDao fd = new FuncionarioDao();
				Funcionario f =  fd.buscarPorId(id);
				if(f == null){
					throw new Exception("Funcionario não encontrado!!");
				}
				List<Cargo> lista = new ArrayList<Cargo>();
				lista = new CargoDao().buscarTodos();
				
				List<String> listaUfs = new ArrayList<String>();
                listaUfs.add("RJ");
                listaUfs.add("SP");
                listaUfs.add("MG");
                listaUfs.add("ES");
                request.setAttribute( "lista", listaUfs);
				
				request.setAttribute("funcionario", f);
				request.setAttribute("listaCargo", lista);
				request.getRequestDispatcher("atualizar.jsp").forward(request, response);		
			
			}else if(q.equalsIgnoreCase("atualizar")){//atualizar os dados	
				
						String nome = request.getParameter("nome");
						String email = request.getParameter("email");
						String sexo = request.getParameter("sexo");
						Double salario = new Double(request.getParameter("salario"));
						String logradouro = request.getParameter("logradouro");
						String cidade = request.getParameter("cidade");
						String estado = request.getParameter("estado");
						String adm = request.getParameter("admissao");
						String cpf = request.getParameter("cpf");
						String admissao[] = adm.split("/");
						GregorianCalendar cal = new GregorianCalendar(new Integer(admissao[2]), new Integer(admissao[1]) - 1,
								new Integer(admissao[0]));
						String nasc = request.getParameter("admissao");			
						String nascimento[] = nasc.split("/");
						GregorianCalendar caln = new GregorianCalendar(new Integer(nascimento[2]), new Integer(nascimento[1]) - 1,
								new Integer(nascimento[0]));
								
						Integer id = new Integer(request.getParameter("id"));
						
						Funcionario f = new Funcionario(id, nome, cpf, caln.getTime(), email, sexo, cal.getTime());
						Endereco e = new Endereco(id, logradouro, cidade, estado);
						Cargo c = new Cargo(new Integer(request.getParameter("cargo")), null,salario);

						f.setCargo(c);
						f.setEndereco(e);
						e.setFuncionario(f);
						
						new FuncionarioDao().update(f);
						
						request.setAttribute("msg", "Funcionário atualizado com sucesso!");
						request.getRequestDispatcher("consultar.jsp").forward(request, response);
				
			}else if(q.equalsIgnoreCase("excluir")){//deletar um funcionario
				Integer id = new Integer( request.getParameter( "id" ) );
                FuncionarioDao fd = new FuncionarioDao();
                Funcionario f  = fd.buscarPorId(id);
                fd.delete(f);
				request.setAttribute("msg", "Funcionário deletado com sucesso!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else{
			
				throw new Exception("Ação invalida!");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
