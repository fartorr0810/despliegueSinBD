package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;


@Service
public class LoginService {	
	//Atributo con la lista de usuarios que existen simulando una base de datos
	private List<Usuario> repositorio = new ArrayList<>();
	
	//Metodo para anadir usuario
	public Usuario add(Usuario user) {
		repositorio.add(user);
		return user;
	}
	/**
	 * Metodo para buscar usuarios
	 * @return devuelve la lista con los usuarios 
	 */
	public List<Usuario> findAll() {
		return repositorio;
	}
	/**
	 * Metodo que busca el usuario dentro de la lista de usuarios
	 * @return devuelve true o false segun si encuentra el usuario o no en la lista.
	 */
	public boolean findUser(Usuario usuario) {
		boolean encontrado=false;
		if (repositorio.contains(usuario)) {
			encontrado=true;
		}
		return encontrado;
	}
	/**
	 * Metodo que busca el usuario dentro de la lista de usuarios pero
	 * obtiene la posicion del usuario para despues cuando se anada un pedido , se le haga al usuario
	 * que esta en la lista.
	 * @return devuelve true o false segun si encuentra el usuario o no en la lista.
	 */
	public Usuario buscarUsuarioEnRep(Usuario usuario) {
		int posicion=this.repositorio.indexOf(usuario);
		if (posicion!=-1) {
			return this.repositorio.get(posicion);
		}
		return usuario;
	}
	/**
	 * Creo por defecto unos usuarios.
	 */
	@PostConstruct
	public void init() {
		repositorio.addAll(Arrays.asList(new Usuario(0,"fartorr0810", "Usuario", "frankarroyop@gmail.com"
				, "Fran", "608839866","C/ San Isidro")
				,new Usuario(1,"Invitado", "invitado", "invitado@gmail.com"
				, "Spring", "612234432","C/ San Pedro")
				,new Usuario(2,"Jorge", "profe12", "jorgerodriguez@gmail.com"
						, "Jorge", "43239891","Jacarandalandia")));
	}

	
}
