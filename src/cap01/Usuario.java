package cap01;

import java.util.ArrayList;

//Clase Usuario: Esta clase representaría a un usuario de la biblioteca.
//Atributos: nombre (String), ID de usuario (String), librosPrestados (Lista de Libro).

public class Usuario {

	private String nombre;
	private String idUsuario;
	private ArrayList<Libro> librosPrestados;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public ArrayList<Libro> getLibrosPrestados() {
		return librosPrestados;
	}
	public void setLibrosPrestados(ArrayList<Libro> librosPrestados) {
		this.librosPrestados = librosPrestados;
	}
	
	public Usuario() {
		nombre = "";
		idUsuario = "";
		librosPrestados = new ArrayList<Libro>();
	}
	
	
	public Usuario(String nombre, String idUsuario, ArrayList<Libro> librosPrestados) {
		super();
		this.nombre = nombre;
		this.idUsuario = idUsuario;
		this.librosPrestados = librosPrestados;
	}
	
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", idUsuario=" + idUsuario + ", librosPrestados=" + librosPrestados + "]";
	}
	
}
