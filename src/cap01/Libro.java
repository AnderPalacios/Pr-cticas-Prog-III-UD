package cap01;

import java.util.Objects;

/**
 * Clase Libro: Esta clase representaría un libro en la biblioteca.
 * Atributos: título (String), autor (String), ISBN (String), prestado (boolean).
 */

public class Libro {
	
	private String titulo;
	private String autor;
	private String ISBN;
	private boolean prestado;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public boolean isPrestado() {
		return prestado;
	}
	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}
	
	public Libro() {
		titulo = "";
		autor = "";
		ISBN = "";
		prestado = false;
	}
	public Libro(String titulo, String autor, String iSBN, boolean prestado) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		ISBN = iSBN;
		this.prestado = prestado;
	}
	//Hacer esto por el ISBN:---------------------------------
	@Override
	public int hashCode() {
		return Objects.hash(ISBN, autor, prestado, titulo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(ISBN, other.ISBN) && Objects.equals(autor, other.autor) && prestado == other.prestado
				&& Objects.equals(titulo, other.titulo);
	}
	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", ISBN=" + ISBN + ", prestado=" + prestado + "]";
	}
	
	

}
