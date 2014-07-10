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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Vehiculos generated by hbm2java
 */
@Entity
@Table(name = "vehiculos", catalog = "alex_gracia", uniqueConstraints = @UniqueConstraint(columnNames = "MATRICULA"))
public class Vehiculos implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idVehiculos, potencia, numeroPuertas, kilometros;
	private String numeroChasis, matricula, marca, modelo, color;
	private Date anoFabricacion;
	private Set<Polizas> polizases = new HashSet<Polizas>(0);
	private Set<Extras> extrases = new HashSet<Extras>(0);

	public Vehiculos() {
	}

	public Vehiculos(String matricula) {
		this.matricula = matricula;
	}

	public Vehiculos(String numeroChasis, String matricula, String marca,
			String modelo, Integer potencia, Date anoFabricacion, String color,
			Integer numeroPuertas, Integer kilometros, Set<Polizas> polizases,
			Set<Extras> extrases) {
		this.numeroChasis = numeroChasis;
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.potencia = potencia;
		this.anoFabricacion = anoFabricacion;
		this.color = color;
		this.numeroPuertas = numeroPuertas;
		this.kilometros = kilometros;
		this.polizases = polizases;
		this.extrases = extrases;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_VEHICULOS", unique = true, nullable = false)
	public Integer getIdVehiculos() {
		return this.idVehiculos;
	}

	public void setIdVehiculos(Integer idVehiculos) {
		this.idVehiculos = idVehiculos;
	}

	@Column(name = "NUMERO_CHASIS", length = 17)
	public String getNumeroChasis() {
		return this.numeroChasis;
	}

	public void setNumeroChasis(String numeroChasis) {
		this.numeroChasis = numeroChasis;
	}

	@Column(name = "MATRICULA", unique = true, nullable = false, length = 100)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Column(name = "MARCA", length = 100)
	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Column(name = "MODELO", length = 100)
	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Column(name = "POTENCIA")
	public Integer getPotencia() {
		return this.potencia;
	}

	public void setPotencia(Integer potencia) {
		this.potencia = potencia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ANO_FABRICACION", length = 10)
	public Date getAnoFabricacion() {
		return this.anoFabricacion;
	}

	public void setAnoFabricacion(Date anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}

	@Column(name = "COLOR", length = 10)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "NUMERO_PUERTAS")
	public Integer getNumeroPuertas() {
		return this.numeroPuertas;
	}

	public void setNumeroPuertas(Integer numeroPuertas) {
		this.numeroPuertas = numeroPuertas;
	}

	@Column(name = "KILOMETROS")
	public Integer getKilometros() {
		return this.kilometros;
	}

	public void setKilometros(Integer kilometros) {
		this.kilometros = kilometros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vehiculos")
	public Set<Polizas> getPolizases() {
		return this.polizases;
	}

	public void setPolizases(Set<Polizas> polizases) {
		this.polizases = polizases;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "extras_vehiculos", catalog = "alex_gracia", joinColumns = { @JoinColumn(name = "ID_VEHICULO", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_EXTRA", nullable = false, updatable = false) })
	public Set<Extras> getExtrases() {
		return this.extrases;
	}

	public void setExtrases(Set<Extras> extrases) {
		this.extrases = extrases;
	}

}
