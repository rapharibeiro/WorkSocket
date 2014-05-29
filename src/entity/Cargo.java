package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ // consultas nomeadas para executar operações de banco, neste
// cargo listar em ordem os cargos que serao
@NamedQuery(name = "cargoAll", query = "SELECT c FROM Cargo c ORDER BY c.nomeCargo") // cadastrados.
})
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// autoincremento e PK
	@Column
	private Integer idCargo;
	@Column
	private String nomeCargo;
	@Column(precision = 10, scale = 2)
	// (10,2) -> 0000000000,00
	private Double salario;

	@OneToMany(mappedBy = "cargo")
	// relacionamento de (1,N) com funcionario mapeado por cargo
	private List<Funcionario> funcionario;

	// construtor

	public Cargo(Integer idCargo, String nomeCargo, Double salario) {
		super();
		this.idCargo = idCargo;
		this.nomeCargo = nomeCargo;
		this.salario = salario;
	}

	public Cargo() {
		super();
	}

	// set get

	public List<Funcionario> getFuncionarios() {
		return funcionario;
	}

	public void setFuncionarios(List<Funcionario> funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public String getNomeCargo() {
		return nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "Cargo [idCargo=" + idCargo + ", nomeCargo=" + nomeCargo
				+ ", salario=" + salario + "]";
	}
}
