package persistence;

import org.hibernate.criterion.Restrictions;

import entity.Funcionario;

public class FuncionarioDao extends GenericDao<Funcionario, Integer> {
	
	public	FuncionarioDao(){
		super(new Funcionario());
	}
	
/*//
//	// metodo para salvar funcionario(create)
//	public void salvar(Funcionario f) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		transaction = session.beginTransaction();
//		session.saveOrUpdate(f);
//		transaction.commit();
//		session.close();
//	}
//
//	// metodo para editar funcionario (update)
//	public void alterar(Funcionario f) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		transaction = session.beginTransaction();
//		session.update(f);
//		transaction.commit();
//		session.close();
//	}
//
//	// metodo para deletar funcionario(remove)
//	public void deletar(Funcionario f) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		transaction = session.beginTransaction();
//		session.delete(f);
//		transaction.commit();
//		session.close();
//	}
//
//	// metodo para listar os funcionarios, usando query
//	public List<Funcionario> listagem() throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		List<Funcionario> lista = new ArrayList<Funcionario>();
//		query = session.createQuery("SELECT f FROM Funcionario f");
//		lista = query.list();
//		session.close();
//		return lista;
//	}
//
//	// metodo para listar os funcionarios por id
//	public Funcionario listarPorId(Integer id) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		Funcionario f = (Funcionario) session.get(Funcionario.class, id);
//		session.close();
//		return f;
//	}
//

	}*/
	// metodo para buscar o email cadastrado no banco, usando criteria
	public Funcionario buscarEmail(String email) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		criteria = session.createCriteria(Funcionario.class);
		criteria.add(Restrictions.eq("email", email));
		Funcionario f = (Funcionario) criteria.uniqueResult();
		session.close();
		return f;
	}
}
