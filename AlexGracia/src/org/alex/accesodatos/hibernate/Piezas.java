package org.alex.accesodatos.hibernate;

// Generated 17-jun-2014 12:41:48 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Piezas generated by hbm2java
 */
@Entity
@Table(name = "piezas", catalog = "alex_gracia")
public class Piezas implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idPiezas;
	private Integer codigoPiezas;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private Integer precio;
	private String lugarOrigen;
	private Date fechaSolicitud;
	private String marca;
	private Set<Proveedores> proveedoreses = new HashSet<Proveedores>(0);
	private Set<Talleres> tallereses = new HashSet<Talleres>(0);

	public Piezas() {
	}

	public Piezas(String nombre, String lugarOrigen) {
		this.nombre = nombre;
		this.lugarOrigen = lugarOrigen;
	}

	public Piezas(Integer codigoPiezas, String nombre, String descripcion,
			Integer cantidad, Integer precio, String lugarOrigen,
			Date fechaSolicitud, String marca, Set<Proveedores> proveedoreses,
			Set<Talleres> tallereses) {
		this.codigoPiezas = codigoPiezas;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
		this.lugarOrigen = lugarOrigen;
		this.fechaSolicitud = fechaSolicitud;
		this.marca = marca;
		this.proveedoreses = proveedoreses;
		this.tallereses = tallereses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_PIEZAS", unique = true, nullable = false)
	public Integer getIdPiezas() {
		return this.idPiezas;
	}

	public void setIdPiezas(Integer idPiezas) {
		this.idPiezas = idPiezas;
	}

	@Column(name = "CODIGO_PIEZAS")
	public Integer getCodigoPiezas() {
		return this.codigoPiezas;
	}

	public void setCodigoPiezas(Integer codigoPiezas) {
		this.codigoPiezas = codigoPiezas;
	}

	@Column(name = "NOMBRE", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "CANTIDAD")
	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "PRECIO")
	public Integer getPrecio() {
		return this.precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	@Column(name = "LUGAR_ORIGEN", nullable = false, length = 200)
	public String getLugarOrigen() {
		return this.lugarOrigen;
	}

	public void setLugarOrigen(String lugarOrigen) {
		this.lugarOrigen = lugarOrigen;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_SOLICITUD", length = 10)
	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	@Column(name = "MARCA", length = 100)
	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "piezas_proveedores", catalog = "alex_gracia", joinColumns = { @JoinColumn(name = "ID_PIEZA", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_PROVEEDOR", nullable = false, updatable = false) })
	public Set<Proveedores> getProveedoreses() {
		return this.proveedoreses;
	}

	public void setProveedoreses(Set<Proveedores> proveedoreses) {
		this.proveedoreses = proveedoreses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "piezas_talleres", catalog = "alex_gracia", joinColumns = { @JoinColumn(name = "ID_PIEZA", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_TALLER", nullable = false, updatable = false) })
	public Set<Talleres> getTallereses() {
		return this.tallereses;
	}

	public void setTallereses(Set<Talleres> tallereses) {
		this.tallereses = tallereses;
	}

}
