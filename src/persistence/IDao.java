package persistence;

import java.util.List;

public interface IDao<E, ID> {

	void save(E obj)throws Exception;
	void update(E obj)throws Exception;
	void delete(E obj)throws Exception;
	
	List<E> buscarTodos()throws Exception;
	E buscarPorId(ID cod)throws Exception;
}
