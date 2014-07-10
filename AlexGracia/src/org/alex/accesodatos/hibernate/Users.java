package org.alex.accesodatos.hibernate;

// Generated 28-jun-2014 19:43:06 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "alex_gracia", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String login, password, rango;

	public Users() {
	}

	public Users(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public Users(String login, String password, String rango) {
		this.login = login;
		this.password = password;
		this.rango = rango;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "login", unique = true, nullable = false, length = 20)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "rango", length = 6)
	public String getRango() {
		return this.rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

}
