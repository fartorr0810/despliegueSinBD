package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
@Service
public class PedidoService {
	//Atributos y inyecto el servicio de producto
	private List<Pedido> repositoriopedidos = new ArrayList<>();
	@Autowired
	private ProductoService servicioproducto;
	private Map<Producto, Integer> productos=new HashMap<>();
	/**
	 * Metodo que recibe un pedido y lo mete en el repositorio de pedidos
	 * @return Devuelve el pedido introducido	
	 */
	public Pedido add(Pedido pedido) {
		repositoriopedidos.add(pedido);
		return pedido;
	}
	/**
	 * Metodo que busca todos los pedidos
	 * @return devuelve todos los pedidos
	 */
	public List<Pedido> findAll() {
		return repositoriopedidos;
	}
	/**
	 *  Metodo que busca los pedidos de un usuario en especifico
	 * 
	 * @return Devuelve la lista de sus pedidos
	 */
	public List<Pedido> findPedidos(Usuario usuario){
		List<Pedido> pedidos;
		pedidos=usuario.getListapedidos();
		return pedidos; 
	}
	/**
	 * Metodo en el que anadimos el pedido que recibe todos los atributos necesarios para construir
	 * el pedido
	 * @param usuario El usuario al que pertenece el pedidp
	 * @param tipopedido El tipo de pedido
	 * @param map Mapa auxiliar que se introducira
	 * @param direccionentrega Direccion de entrega del pedido
	 * @param emailcontacto Email de contacto del pedido
	 * @param telefonocontacto Telfono de contacto del pedido
	 * 
	 */
	public void addPedido(Usuario usuario, String tipopedido,Map<Producto, Integer> map
			,String direccionentrega,String emailcontacto,String telefonocontacto) {
		Pedido pedido=new Pedido(tipopedido,direccionentrega,emailcontacto,telefonocontacto);
		pedido.setListaproducto(map);
		usuario.addPedido(pedido);
	}
	/**
	 * Metodo que anade la cantidad de cada producto recorriendo la lista de productos y metiendo en el mapa
	 * los productos que tienen cantidades.
	 * @param unidades Unidades de cada producto
	 */
	public void anadirProductoUnidades(Integer[] unidades) {
		Map<Producto,Integer> aux=new HashMap<>(); 
		List<Producto> listaproductos=this.servicioproducto.findAll();
		for (int i = 0; i < unidades.length; i++) {
			aux.put(listaproductos.get(i), unidades[i]);
		}
		this.productos=aux;
	}
	/**
	 * Metodo que nos devuelve el mapa de los productos 
	 */
	public Map<Producto, Integer> buscarProductosUd(){
		return this.productos;
	}
	//Get del mapa de productos
	public Map<Producto, Integer> getProductos() {
		return productos;
	}
	/**
	 * Metodo para buscar el pedido por la id
	 * @param idpedido id del pedido del que buscamos
	 * @param usuario usuario de quien buscamos el pedido
	 * @return Nos devuelve el pedido que buscamos si lo encuentra
	 */
	public Pedido buscarPedidoPorId(Integer idpedido,Usuario usuario) {
		Pedido pedido=new Pedido(idpedido);
		int pos=usuario.getListapedidos().indexOf(pedido);
		return usuario.getListapedidos().get(pos);
	}
	/**
	 * Metodo para editar el pedido , recibe el pedido , el usuario y cantidades
	 * con el usuario aconseguimos su lista de pedidos y creamos un iterador,
	 * Una vez eso buscamos el pedido por id con el while, una vez encontrado
	 * Creamos un mapa auxliar y una nueva lista con productos, recorremos el for 
	 * y va asignando cada producto su cantidad correspondiente y por ultimo hacemos un
	 * set pedido
	 * @param idpedido
	 * @param usuario
	 * @param cantidades
	 */
	public void editarPedido(Integer idpedido,Usuario usuario,Integer[] cantidades) {
		Iterator<Pedido> sig = usuario.getListapedidos().iterator();
		boolean encontrado=false;
		while (sig.hasNext( ) && !encontrado) {
			Pedido pedido = sig.next();
			if (pedido.getIdpedido()==idpedido) {
				encontrado=true;
				Map<Producto,Integer> aux=new HashMap<>(); 
				List<Producto> productosnuevos=this.servicioproducto.findAll();
				for (int i = 0; i < cantidades.length; i++) {
					aux.put(productosnuevos.get(i), cantidades[i]);
				}
				pedido.setListaproducto(aux);
			}
		}
	}
}
