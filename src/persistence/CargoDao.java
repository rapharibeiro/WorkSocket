package persistence;

import entity.Cargo;

public class CargoDao extends GenericDao<Cargo, Integer> {

	public CargoDao() {
		super(new Cargo());
	} 
	
//	public void save(Cargo c) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession(); // abre
//																	// conexao
//		transaction = session.beginTransaction(); // inicia transição de dados
//		session.save(c); // salva
//		transaction.commit(); // confirma
//		session.close(); // fecha
//	}
//
//	// metodo para listar os cargos que serão cadastrados
//	public List<Cargo> listar() throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		List<Cargo> lista = session.getNamedQuery("cargoAll").list();
//		session.close();
//		return lista;
//	}
//
//	// lista os cargos cadastrados por id
//	public Cargo listarPorId(Integer id) throws Exception {
//		session = HibernateUtil.getSessionFactory().openSession();
//		Cargo c = (Cargo) session.get(Cargo.class, id);
//		session.close();
//		return c;
//	}

}
