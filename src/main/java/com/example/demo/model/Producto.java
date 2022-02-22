package com.example.demo.model;

import java.util.Objects;

public class Producto {
	//Atributos
	private int id;
	private String nameproduct;
	private double price;
	//Constructor vacio
	public Producto() {
		super();
	}
	//Constructor con los atributos para crear un producto.
	public Producto(int id, String nameproduct, double price) {
		super();
		this.id = id;
		this.nameproduct = nameproduct;
		this.price = price;
	}
	//Getters y Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameproduct() {
		return nameproduct;
	}
	public void setNameproduct(String nameproduct) {
		this.nameproduct = nameproduct;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	//Hashcode y equals por id y nombre de producto.
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return id == other.id;
	}

	
}
