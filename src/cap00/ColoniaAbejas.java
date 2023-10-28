package cap00;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cap00.Ecosistema;
import cap00.ElementoEcosistema;
import cap00.PlantacionFlores;

public class ColoniaAbejas extends ElementoEcosistema {
	
	protected Long poblacion;
	protected JPanel panelAbejas;
	
	public Long getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Long poblacion) {
		this.poblacion = poblacion;
	}
	
	public ColoniaAbejas() {
		this.poblacion = (long)100;
		this.panelAbejas = null;
	}
	
	public ColoniaAbejas(String titulo, Point punto, Dimension dimension, Long poblacion) {
		super(titulo, punto, dimension);
		this.poblacion = poblacion;
	}

	@Override
	public String toString() {
		return "Abejas: " + getTitulo() + " - " + getPoblacion() + " indivs. - " + "Coord (" + getPunto().x 
				+ "," + getPunto().y + ") - Tamaño ("+ getDimension().width + "," + getDimension().height + ")";
	}

	@Override
	public void evoluciona(int dias) {
		double factorCrecimiento = 1.0;
		 int numFlores = 0;
		 for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
		 int dist = (int) Ecosistema.distancia( this, ee );
		 if (ee instanceof ColoniaAbejas && ee!=this) { // Otra colonia de abejas perjudica
		 if (dist < 500) factorCrecimiento = factorCrecimiento * dist / 500;
		 } else if (ee instanceof PlantacionFlores) { // La cercanía de flores beneficia
		 if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
		 numFlores += ((PlantacionFlores)ee).getCantidad();
		 }
		 }
		 if (numFlores < 50) factorCrecimiento *= 0.1; // Insuficientes flores mata
		 poblacion = (long) (poblacion * factorCrecimiento * dias);
		 if (poblacion > 5000) poblacion = (long) 5000; // Límite de población
		
	}

	@Override
	public JPanel getPanel() {
		if (panelAbejas == null) {
			panelAbejas = new JPanel();
			panelAbejas.setLayout(new GridLayout(3,1));
			/*
			JLabel lblNombre = new JLabel("Colonia");
			JLabel lblPoblacion = new JLabel(this.poblacion+"");
			JLabel lblTipo = new JLabel("Abejas");
			panelAbejas.add(lblNombre);
			panelAbejas.add(lblPoblacion);
			panelAbejas.add(lblTipo);
			*/
		}
		return panelAbejas;
	}

	public static void main(String[] args) {
		System.out.println(Ecosistema.distancia(null, null));
	}
	
	
}
