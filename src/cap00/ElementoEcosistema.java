package cap00;

import java.awt.*;
import javax.swing.*;

public abstract class ElementoEcosistema {
	
	private String titulo;
	private Point punto;
	private Dimension dimension;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Point getPunto() {
		return punto;
	}

	public void setPunto(Point punto) {
		this.punto = punto;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public ElementoEcosistema() {
		this.titulo = "";
		this.punto = null;
		this.dimension = null;
	}
	
	public ElementoEcosistema(String titulo, Point punto, Dimension dimension) {
		super();
		this.titulo = titulo;
		this.punto = punto;
		this.dimension = dimension;
	}

	@Override
	public String toString() {
		return "ElementoEcosistema [titulo=" + titulo + ", punto=" + punto + ", dimension=" + dimension + "]";
	}
	
	//Métodos a usar por las clases herederas:
	public abstract JPanel getPanel();
	
	public abstract void evoluciona(int dia);
	
}
