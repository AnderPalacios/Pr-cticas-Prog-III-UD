package cap01;

import java.util.ArrayList;

//Clase Biblioteca: Esta clase representaría la biblioteca en sí y contendría la lista de todos los 
//libros y usuarios.
//Atributos: libros (Lista de Libro), usuarios (Lista de Usuario).

public class Biblioteca {
	
	private ArrayList<Libro> listaLibros;
	private ArrayList<Usuario> listaUsuarios;
	
	
	public ArrayList<Libro> getListaLibros() {
		return listaLibros;
	}
	public void setListaLibros(ArrayList<Libro> listaLibros) {
		this.listaLibros = listaLibros;
	}
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	public Biblioteca() {
		listaLibros = new ArrayList<Libro>();
		listaUsuarios = new ArrayList<Usuario>();
	}
	
	public Biblioteca(ArrayList<Libro> listaLibros, ArrayList<Usuario> listaUsuarios) {
		super();
		this.listaLibros = listaLibros;
		this.listaUsuarios = listaUsuarios;
	}

}
