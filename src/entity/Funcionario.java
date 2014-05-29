package entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Entidade Funcionário
//Id(PK), nome, email(único), salario(double) e admissão(data)
//Relacionamento (1,1) com endereço -> 1 funcionario só pode ter 1 cargo 
//Relacionamento (1,N) com cargo -> 1 cargo pode ter vários funcionarios, mas 1 funcionario so pode estar em 1 cargo

@Entity
public class Funcionario {

	@Id
	// PK
	@GeneratedValue(strategy = GenerationType.AUTO)
	// auto encremento
	@Column
	private Integer idFuncionario;
	@Column(length = 50)
	private String nome;
	@Column(unique = true)
	private String cpf;
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	@Column(length = 50, unique = true)
	// campo único
	private String email;
	@Column(columnDefinition = "char(1)")
	private String sexo;
	@Column
	@Temporal(TemporalType.DATE)
	private Date admissao;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "funcionario")
	// mapeado por funcionario, e excetuda todas as operações em cascata
	private Endereco endereco;
	@OneToOne
	// (relacionamento com cargo (1,N)
	@JoinColumn(name = "id_cargo")
	// concatenando tabelas
	private Cargo cargo;
	
	@ManyToOne(cascade=CascadeType.ALL, optional=false, fetch = FetchType.LAZY)
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	public Funcionario(Integer idFuncionario, String nome, String cpf,
			Date nascimento, String email, String sexo, Date admissao) {
		super();
		this.idFuncionario = idFuncionario;
		this.nome = nome;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.email = email;
		this.sexo = sexo;
		this.admissao = admissao;
	}

	public Funcionario() {
		super();
	}

	// set e get

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getAdmissao() {
		return admissao;
	}

	public void setAdmissao(Date admissao) {
		this.admissao = admissao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	@Override
	public String toString() {
		return "Funcionario [idFuncionario=" + idFuncionario + ", nome=" + nome
				+ ", cpf=" + cpf + ", nascimento=" + nascimento + ", email="
				+ email + ", sexo=" + sexo 	+ ", admissao=" + admissao + "]";
	}
}
