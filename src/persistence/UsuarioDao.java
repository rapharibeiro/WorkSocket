package persistence;

import org.hibernate.criterion.Restrictions;

import entity.Usuario;

public class UsuarioDao extends GenericDao<Usuario, Integer> {

	public UsuarioDao(){
		super(new Usuario());
	}
	
	// public void cadastrar(Usuario user)throws Exception {
	//
	// transaction = session.beginTransaction();
	// session.saveOrUpdate(user);
	// transaction.commit();
	// session.clear();
	//
	// // session = HibernateUtil.getSessionFactory().openSession();
	// // session.beginTransaction();
	// // session.saveOrUpdate(user);
	// //// transaction.commit();
	// // session.close();
	// }

	public void cadastrar(Usuario user) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = null;

		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
			session.clear();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}

	}

//	public void atualizar(Usuario user) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		session.update(user);
//		transaction.commit();
//		session.close();
//	}
//
//	public Usuario listarPorId(Integer id) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		Usuario user = (Usuario) session.get(Usuario.class, id);
//		session.close();
//		return user;
//	}

	public Usuario buscaUsuario(String login, String senha) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("senha", senha));
		Usuario user = (Usuario) criteria.uniqueResult();
		session.close();
		return user;
	}

	public Usuario buscarlogin(String login) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		Usuario user = (Usuario) criteria.uniqueResult();
		session.close();
		return user;
	}

	public Usuario buscarSenha(String senha) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("senha", senha));
		Usuario user = (Usuario) criteria.uniqueResult();
		session.close();
		return user;
	}
}
