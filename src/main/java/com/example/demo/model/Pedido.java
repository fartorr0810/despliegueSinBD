package com.example.demo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pedido {
	//Atributos
	private Integer idpedido;
	private HashMap<Producto,Integer> listaproducto=new HashMap<>();
	private Date fechapedido;
	private String tipopedido;
	private static int incremento=0;
	private String direccionentrega;
	private String emailcontacto;
	private String telefonopedido; 
	//Constructor vacio
	public Pedido() {
		super();
	}
	/**
	 * Constructor del pedido donde le pasamos los datos editables del pedido y otros se asignan por defecto
	 * como es la fecha de creacion del pedido, y la id.
	 */
	public Pedido(String tipopedido,String direccionentrega,String emailcontacto,String telefonopedido) {
		super();
		this.tipopedido=tipopedido;
		this.idpedido=incremento;
		incremento++;
		this.fechapedido=new Date();
		this.direccionentrega=direccionentrega;
		this.emailcontacto=emailcontacto;
		this.telefonopedido=telefonopedido;
	}
	//Constructor con idpedido para que posteriormente podamos buscar por id.
	public Pedido(Integer idpedido) {
		super();
		this.idpedido = idpedido;
	}
	//Getters y Setters
	public Integer getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}
	public Map<Producto, Integer> getListaproducto() {
		return listaproducto;
	}
	public void setListaproducto(Map<Producto, Integer> listaproducto) {
		this.listaproducto = (HashMap<Producto,Integer>)listaproducto;
	}
	public Date getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}
	public String getDireccionentrega() {
		return direccionentrega;
	}
	public void setDireccionentrega(String direccionentrega) {
		this.direccionentrega = direccionentrega;
	}
	public String getEmailcontacto() {
		return emailcontacto;
	}
	public void setEmailcontacto(String emailcontacto) {
		this.emailcontacto = emailcontacto;
	}
	public String getTelefonopedido() {
		return telefonopedido;
	}
	public void setTelefonopedido(String telefonopedido) {
		this.telefonopedido = telefonopedido;
	}
	public String getTipopedido() {
		return tipopedido;
	}
	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}
	public void anadirProductos(Producto producto,Integer unidades) {
		this.listaproducto.put(producto, unidades);
	}
	//Hashcode y equals por idpedido
	@Override
	public int hashCode() {
		return Objects.hash(idpedido);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return idpedido == other.idpedido;
	}
	//ToString por defecto del pedido
	@Override
	public String toString() {
		return "Pedido [idpedido=" + idpedido + ", listaproducto=" + listaproducto + ", fechapedido=" + fechapedido
				+ ", tipopedido=" + tipopedido + ", direccionentrega=" + direccionentrega + ", emailcontacto="
				+ emailcontacto + ", telefonopedido=" + telefonopedido + "]";
	}
}
