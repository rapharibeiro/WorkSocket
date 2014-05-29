package persistence;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Dao {

	Session session; // gerencia conexão com o banco
	Transaction transaction; // manipula as transações com o banco
	Query query; // consulta query
	Criteria criteria; // consulta metodos
}
