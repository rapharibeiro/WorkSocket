package config;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import persistence.CargoDao;

import entity.Cargo;

public class Main {

	public static void main(String[] args) {

		// conexão e criação do banco
		Configuration cfg = new AnnotationConfiguration()
				.configure("config/mysql_hibernate.cfg.xml");
		new SchemaExport(cfg).create(true, true);

		try {
			// criando cargos
			Cargo c1 = new Cargo(null, "Estagiário Desenvolvimento",800.);
			Cargo c2 = new Cargo(null, "Estagiário Suporte", 800.);
			Cargo c3 = new Cargo(null, "DBA",7544.84);
			Cargo c4 = new Cargo(null, "Programador Jr",1634.99);
			Cargo c5 = new Cargo(null, "Analista Java Sênior",6074.23);
			Cargo c6 = new Cargo(null, "Gerente de Projeto",9041.53);
			Cargo c7 = new Cargo(null, "Web Designer",1926.08);
			Cargo c8 = new Cargo(null, "Consultor de Redes",5439.95);
			Cargo c9 = new Cargo(null, "Analista de Infraestrutura",3121.13);
			Cargo c10= new Cargo(null, "Analista de Segurança Jr",2338.32);
			
			// salvando cargos
			CargoDao cd = new CargoDao();
			cd.save(c1);
			cd.save(c2);
			cd.save(c3);
			cd.save(c4);
			cd.save(c5);
			cd.save(c6);
			cd.save(c7);
			cd.save(c8);
			cd.save(c9);
			cd.save(c10);

			System.out.println("Cadastrado com sucesso!!");
			// listando
			for (Cargo c : cd.buscarTodos()) {
				System.out.println(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
