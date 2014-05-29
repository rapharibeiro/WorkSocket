package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUsuario")
	private Integer idUsuario;
	@Column
	private String login;
	@Column
	private String senha;
	@Column
	private Integer nivel;
	
	public Usuario(Integer idUsuario, String login, String senha,
			Integer nivel) {
		super();
		this.idUsuario = idUsuario;
		this.login = login;
		this.senha = senha;
		this.nivel = nivel;
	}
	public Usuario() {
		super();
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", login=" + login
				+ ", senha=" + senha + "]";
	}	
}
