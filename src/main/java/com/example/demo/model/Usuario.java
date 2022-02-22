package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	//Atributos del usuario
	private String username;
	private String password;
	private String email;
	private String name;
	private String phone;
	private String direccion;
	private List<Pedido> listapedidos=new ArrayList<>();
	private int id;
	//Constructor vacio
	public Usuario() {
		super();
	}
	//Constructor con usuario y password para cuando nos logeamos se construya un objeto solo con esos parametros
	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	//Constructor con atributos.
	public Usuario(int id,String username, String password, String email, String name, String phone,String direccion) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.direccion=direccion;
		this.id=id;
	}
	/**
	 * Metodo para anadir pedido a su lista de pedidos del usuario 
	 */
	public void addPedido(Pedido pedido) {
		listapedidos.add(pedido);
	}
	//Getters y Setters
	public List<Pedido> getListapedidos() {
		return listapedidos;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setListapedidos(List<Pedido> listapedidos) {
		this.listapedidos = listapedidos;
	}
	public void addPedidoUsuario(Pedido pedido) {
		this.listapedidos.add(pedido);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	//Hashcode y equals mediante password y username
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	//ToString por defecto
	@Override
	public String toString() {
		return "Usuario [username=" + username + ", password=" + password + ", email=" + email + ", name=" + name
				+ ", phone=" + phone + ", direccion=" + direccion + ", listapedidos=" + listapedidos + ", id=" + id
				+ "]";
	}
	
	
}
