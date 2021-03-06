package org.alex.accesodatos.hibernate;

// Generated 28-jun-2014 19:43:06 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Clientes generated by hbm2java
 */
@Entity
@Table(name = "clientes", catalog = "alex_gracia", uniqueConstraints = @UniqueConstraint(columnNames = "DNI"))
public class Clientes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idClientes, telefono;
	private String codigoClientes, nombre, apellidos, dni, direccion;
	private Date fechaNacimiento, fechaCarnet;
	private Set<Siniestros> siniestroses = new HashSet<Siniestros>(0);
	private Set<Polizas> polizases = new HashSet<Polizas>(0);

	public Clientes() {
	}

	public Clientes(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	public Clientes(String codigoClientes, String nombre, String apellidos,
			String dni, Integer telefono, Date fechaNacimiento,
			Date fechaCarnet, String direccion, Set<Siniestros> siniestroses,
			Set<Polizas> polizases) {
		this.codigoClientes = codigoClientes;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaCarnet = fechaCarnet;
		this.direccion = direccion;
		this.siniestroses = siniestroses;
		this.polizases = polizases;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_CLIENTES", unique = true, nullable = false)
	public Integer getIdClientes() {
		return this.idClientes;
	}

	public void setIdClientes(Integer idClientes) {
		this.idClientes = idClientes;
	}

	@Column(name = "CODIGO_CLIENTES", length = 100)
	public String getCodigoClientes() {
		return this.codigoClientes;
	}

	public void setCodigoClientes(String codigoClientes) {
		this.codigoClientes = codigoClientes;
	}

	@Column(name = "NOMBRE", nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "APELLIDOS", length = 100)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "DNI", unique = true, nullable = false, length = 9)
	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Column(name = "TELEFONO")
	public Integer getTelefono() {
		return this.telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO", length = 10)
	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CARNET", length = 10)
	public Date getFechaCarnet() {
		return this.fechaCarnet;
	}

	public void setFechaCarnet(Date fechaCarnet) {
		this.fechaCarnet = fechaCarnet;
	}

	@Column(name = "DIRECCION", length = 200)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clientes")
	public Set<Siniestros> getSiniestroses() {
		return this.siniestroses;
	}

	public void setSiniestroses(Set<Siniestros> siniestroses) {
		this.siniestroses = siniestroses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clientes")
	public Set<Polizas> getPolizases() {
		return this.polizases;
	}

	public void setPolizases(Set<Polizas> polizases) {
		this.polizases = polizases;
	}

}
