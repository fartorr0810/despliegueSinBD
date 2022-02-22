package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.LoginService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Controller
public class LoginController {
	//String con Literales
    static final String LISTAPEDIDOS = "listapedidos";
    static final String MODELLISTAPEDIDOS = "listapedidos";
	static final String USUARIO="usuario";
	static final String REDIRECTLOGIN="redirect:/login";
	//Declaracion del objeto sesion y los servicios.
	@Autowired
	private HttpSession sesion;
	@Autowired
	private LoginService serviciologin;
	@Autowired
	private PedidoService serviciopedido;
	@Autowired
	private ProductoService servicioproducto;
	/**
	 * Nos muestra la pagina login mediante localhost:8080 y las rutas que le indiquemos nos
	 * devolvera la pagina login.
	 * @param model Aqui lo usamos para cargar el repositorio con los usuarios
	 * @return Nos manda al login
	 */
	@GetMapping({"/","/login"})
	public String cargarUsuarios(Model model) {
		model.addAttribute("listausuarios", serviciologin.findAll());
		model.addAttribute(USUARIO,new Usuario());
		return "login";
	}
	/**
	 * Metodo que se lanza una vez completamos el formulario con el PostMapping
	 * y nos comprueba que el usuario y password concuerda, si no, nos manda de nuevo al login
	 * @param usuario Contiene el usuario que declaramos con el usuario y password
	 * @param bindingResult Tiene los errores que se producen en el login
	 * @return Si esta bien, nos mandara a la pantalla de seleccion de opciones. en caso
	 * 	que no, nos mandara al login de nuevo.
	 */
	@PostMapping({"/login/submit"})
	public String comprobarUserConPassword(@Valid @ModelAttribute("usuario") Usuario usuario,
			BindingResult bindingResult) {
		String direccion="";
		boolean error=bindingResult.hasErrors();
		if (serviciologin.findUser(usuario) && !error) {			
			sesion.setAttribute(USUARIO, this.serviciologin.buscarUsuarioEnRep(usuario));
			direccion="seleccion";
		}else {
			return REDIRECTLOGIN;
		}
		return direccion;
		
	}
	/**
	 * Si en la pantalla de seleccion elegimos crear pedido , esto se encargara de cargar los productos.
	 * @return Nos devuelve la pagina de crearpedido 
	 */
	@GetMapping({"/seleccion/crearpedido"})
	public String crearPedido(Model model) {
		if (sesion.getAttribute(USUARIO)==null || sesion.isNew()) {
			return REDIRECTLOGIN;
		}
		
		model.addAttribute("listaproductos",servicioproducto.findAll());
		return "crearpedido";
	}
	/**
	 * Con este metodo recogeremos las cantidades de cada producto , la procesaremos y la asociamos
	 * con cada producto. Si le da a submit y no tiene ninguna cantidad se redigirigue a si misma
	 * @return Nos lleva a la segunda parte para crear el pedido 
	 */
	@PostMapping({"/resumenpedido/submit"})
	public String crearPedidoProcesado(Model model,@RequestParam(name="numeroproducto")Integer[] cantidades) {
		String direccion="";
		boolean vacio=true;
		for (int i = 0; i < cantidades.length; i++) {
			if (cantidades[i]!=null) {
				vacio=false;
			}
		}
		if (vacio) {
			direccion="redirect:/seleccion/crearpedido";
		}else {
			this.servicioproducto.resumenPedido(cantidades);
			this.serviciopedido.anadirProductoUnidades(cantidades);
			model.addAttribute("listaproductounidades",this.servicioproducto.getListaproductoUnidades());
			model.addAttribute(USUARIO,sesion.getAttribute(USUARIO));
			direccion="resumenpedido";
		}
		return direccion;
		}
	/**
	 * Metodo el que resumimos el pedido y mostramos el precio total, ademas de cargar los datos 
	 * para poder editar los datos de envio como la direccion, telefono o correo de contacto
	 * @param model En el pasaremos la lista con los productos y sus unidades y el usuario que tenemos
	 * en sesion.
	 * @return Nos devuelve la pagina de resumenpedido. 
	 */
	@GetMapping({"resumenpedido"})
	public String resumenDelPedido(Model model) {
		if (sesion.getAttribute(USUARIO)==null) {
			return REDIRECTLOGIN;			
		}
		model.addAttribute("listaproductounidades",this.servicioproducto.getListaproductoUnidades());
		model.addAttribute(USUARIO,sesion.getAttribute(USUARIO));
		return "resumenpedido";
	}
	/**
	 * Metodo en el que procesamos los datos y creamos el pedido y se añade el pedido al usuario
	 * @param tipoEnvio Atributo obligatorio con el tipo de envio
	 * @param email Atributo que se carga con el dato por defecto o se modifica si el usuario quiere
	 * @param phone Atributo que se carga con el dato por defecto o se modifica si el usuario quiere
	 * @param direccion Atributo que se carga con el dato por defecto o se modifica si el usuario quiere
	 * @return Te dirije a seleccion
	 */
	@PostMapping({"seleccion/listado"})
	public String listarPedidos(Model model, @RequestParam(required=true,value="tipoenvio") String tipoEnvio,
			@RequestParam(required=false,value="email") String email,
			@RequestParam(required=false,value="telefono") String phone,
			@RequestParam(required=false,value="direccion") String direccion) {
		String direccionreturn="";
		if ("".equals(email) || "".equals(phone) || "".equals(direccion)) {
			direccionreturn="redirect:/resumenpedido";
		}else {
			this.serviciopedido.addPedido((Usuario) sesion.getAttribute(USUARIO), tipoEnvio, this.serviciopedido.getProductos(), direccion,email , phone);
			model.addAttribute(MODELLISTAPEDIDOS,serviciopedido.findPedidos((Usuario) sesion.getAttribute(USUARIO)));
			direccionreturn="redirect:/seleccion/listado";
		}
		return direccionreturn;
	}
	/**
	 * Metodo que mapeamos la lista con los pedidos del usuario que ha realizado que viene
	 * al crear el pedido.
	 * @model En el cargamos la lista de pedidos.
	 * @return Nos devuelve la pagina de listado de pedidos
	 */
	@GetMapping({"seleccion/listado"})
	public String finalizarPedido(Model model) {
		if (sesion.getAttribute(USUARIO)==null) {
			return REDIRECTLOGIN;			
		}
		model.addAttribute(MODELLISTAPEDIDOS,serviciopedido.findPedidos((Usuario) sesion.getAttribute(USUARIO)));
		return LISTAPEDIDOS;
	}
	/**
	 * Metodo que mapeamos la lista con los pedidos del usuario que ha realizado desde el menu
	 * de seleccion.
	 * @model En el cargamos la lista de pedidos.
	 * @return Nos devuelve la pagina de listado de pedidos
	 */
	@GetMapping({"/seleccion/listapedidos"})
	public String listapedidos(Model model) {
		if (sesion.getAttribute(USUARIO)==null || sesion.isNew()) {
			return REDIRECTLOGIN;
		}
		model.addAttribute(MODELLISTAPEDIDOS,this.serviciopedido.findPedidos((Usuario) sesion.getAttribute(USUARIO)));
		return LISTAPEDIDOS;
	}
	/**
	 * Metodo en el que mapeamos el editar pedido donde le pasamos la idpedido
	 * @param idpedido Atributo con la referencia del pedido.
	 * @return Nos manda a la pagina de edicion del pedido en especifico y nos carga
	 * por defecto el catalogo y las cantidades cada uno para que lo modifiquemos.
	 */
	@GetMapping({"/pedido/editarpedido/{idpedido}"})
	public String editarPedido(@PathVariable Integer idpedido,Model model) {
		String direccion="";
		if (sesion.getAttribute(USUARIO)==null) {
			direccion=REDIRECTLOGIN;
		}else {
			Pedido pedido=this.serviciopedido.buscarPedidoPorId(idpedido,(Usuario) sesion.getAttribute(USUARIO));
			model.addAttribute("pedido",pedido);
			model.addAttribute("listaproductos",pedido.getListaproducto());
			direccion="editarpedido";
		}
		return direccion;
	}
	/**
	 * Metodo para editar pedido, al momento de submit llama al metodo edit y recibe los parametros de id
	 * , el usuario y las nuevas cantidades de cada producto
	 * @param cantidades cantidad de productos de cada uno
	 * @param idpedido id del pedido que se va editar
	 * @return te redirecciona a la lista de pedidos del usuario
	 */
	@PostMapping({"/editarpedido/submit"})
	public String editarPedidoFinalizar(@RequestParam(required=false,value="cantidades")Integer[] cantidades,
			@RequestParam(required=false,value="idpedido")Integer idpedido, Model model) {
		
		this.serviciopedido.editarPedido(idpedido, (Usuario) sesion.getAttribute(USUARIO), cantidades);
		return "redirect:/seleccion/listapedidos";
	}
	
	/**
	 * Metodo que se dispara al cerrar sesion y nos invalida la sesio, tras ello nos envia
	 * a al login.
	 * @return te devuelve al login
	 */
	@PostMapping({"/login/logout"})
	public String cerrarSesion(Model model) {
		sesion.invalidate();
		return REDIRECTLOGIN;
	}
}




