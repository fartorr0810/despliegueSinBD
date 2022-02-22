package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;

@Service
public class ProductoService {
	//Lista de producto que existen
	private ArrayList<Producto> listaproductos=new ArrayList<>();
	//Mapa con las unidades de los productos y el producto
	private Map<Producto,Integer> listaproductoUnidades = new HashMap<>();

	
	/**
	 * Metodo get que devuelve un mapa con la lista de los productos y unidades
	 * @return la lista de productos.
	 */
	public Map<Producto, Integer> getListaproductoUnidades() {
		return listaproductoUnidades;
	}
	/**
	 * Cargamos en la lista los productos.
	 */
	@PostConstruct
	public void init() {
		listaproductos.addAll(Arrays.asList(new Producto(1,"White Label",15.00),
				new Producto(2,"Ron Barcelo",19.00),
				new Producto(3,"Larios",10.00),
				new Producto(4,"Jagermaister",19.00),
				new Producto(5,"Ron Pampero",12.00),
				new Producto(6,"Negrita",7.00),
				new Producto(7,"Vodka Absolut",17.00)));	
	}
	/**
	 * Metodo que nos devuelve la lista con los productos que existen
	 * @return nos devuelve la lista de productos.
	 */
	public List<Producto> findAll(){
		return listaproductos;
	}
	/**
	 * Metodo que recibe las cantidades de cada producto y asigna cada cantidad a cada producto.
	 * tras ello asignamos la lista de productos unidades al mapa nuevo que hemos creado. 
	 * @param cantidades cantidad de cada producto
	 */
	//Aqui esta el fallo
	public void resumenPedido(Integer[] cantidades) {
		Map<Producto,Integer> crearpedidoconroducto = new HashMap<>();
		for (int i = 0; i < cantidades.length; i++) {
			crearpedidoconroducto.put(listaproductos.get(i),cantidades[i]);
			
		}
		this.listaproductoUnidades=crearpedidoconroducto;
	}
}
