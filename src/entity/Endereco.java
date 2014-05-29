package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//Entidade Endereco
//Id(PK), logradouro, cidade, estado(enum)
//Relacionamento (1,1) com funcionario

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer idEndereco;
	@Column
	private String logradouro;
	@Column
	private String cidade;
	@Column(columnDefinition = "enum('RJ','SP','MG','ES','PB','RS','CE','PR','DF','BA')")
	// enumeração desses 4 estados
	private String estado;

	// relacionamento (1,1) com funcionario
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// todas as operações em cascata e EAGER traz informações junto do objeto
	// principal
	@JoinColumn(name = "id_funcionario", unique = true)
	// concatenando tabelas, IDFun como FK
	private Funcionario funcionario;

	// construtor
	public Endereco(Integer idEndereco, String logradouro, String cidade,
			String estado) {
		super();
		this.idEndereco = idEndereco;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Endereco() {
		super();
	}

	// set get

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Endereco [idEndereco=" + idEndereco + ", logradouro="
				+ logradouro + ", cidade=" + cidade + ", estado=" + estado
				+ "]";
	}
}
