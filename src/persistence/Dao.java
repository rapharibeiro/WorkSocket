package persistence;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Dao {

	Session session; // gerencia conex�o com o banco
	Transaction transaction; // manipula as transa��es com o banco
	Query query; // consulta query
	Criteria criteria; // consulta metodos
}
