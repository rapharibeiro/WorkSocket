package persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<E, ID extends Serializable> extends Dao implements IDao<E, ID> {

	E entity;
	public GenericDao(E entity){
		this.entity = entity;
	}
	
	public void save(E obj)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(obj);
		transaction.commit();
		session.close();
	}
	public void update(E obj)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(obj);
		transaction.commit();
		session.close();
	}
	public void delete(E obj)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(obj);
		transaction.commit();
		session.close();
	}
	public List<E> buscarTodos()throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		List<E> lista = new ArrayList<E>();
		criteria =  session.createCriteria(entity.getClass());
		lista = criteria.list();
		session.close();
		return lista;
	}
	public E buscarPorId(ID id)throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		E obj = (E) session.get(entity.getClass(), id);
		session.close();
		return obj;
	}
}
