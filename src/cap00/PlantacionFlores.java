package cap00;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlantacionFlores extends ElementoEcosistema {
	
	protected Long cantidad;
	protected JPanel panelFlores;

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	public PlantacionFlores() {
		this.cantidad = (long)0;
		this.panelFlores = null;
	}
	
	public PlantacionFlores(String titulo, Point punto, Dimension dimension, Long cantidad) {
		super(titulo, punto, dimension);
		this.cantidad = cantidad;
	}

	
	@Override
	public String toString() {
		return "Flores: " + getTitulo() + " - " + getCantidad() + " uds. - " + "Coord (" + getPunto().x 
				+ "," + getPunto().y + ") - Tamaño ("+ getDimension().width + "," + getDimension().height + ")";
	}

	@Override
	public void evoluciona(int dias) {
		double factorCrecimiento = 0.75;
		 for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
		 int dist = (int) Ecosistema.distancia( this, ee );
		 if (ee instanceof ColoniaAbejas) { // La cercanía de abejas beneficia
		 if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
		 } else if (ee instanceof Agua) { // La cercanía de agua beneficia
		 if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
		 }
		 }
		 cantidad = (long) (cantidad * factorCrecimiento * dias);
		 if (cantidad > 5000) cantidad = (long) 5000; 
		
	}

	@Override
	public JPanel getPanel() {
		if (panelFlores == null) {
			panelFlores = new JPanel();
			panelFlores.setLayout(new GridLayout(3,1));
			// Todas las líneas comentadas abajo las voy a hacer al crear elementos y sus JPanels en la clase VentanaPrincipal
			// Se me hace más fácil ya que tengo que cambiar el texto de un JLabel. Para hacer eso, 
			// relaciono en un mapa un objeto del ecosistema y un JLabel creado en esa misma clase.
			// Esto pasa en las tres clases que heredan de ElementoEcosistema.
//			JLabel lblNombre = new JLabel("Bosque");
//			JLabel lblPoblacion = new JLabel(this.cantidad+"");
//			JLabel lblTipo = new JLabel("Abejas");
//			panelFlores.add(lblNombre);
//			panelFlores.add(lblPoblacion);
//			panelFlores.add(lblTipo);
		}
		return panelFlores;
	}
	
	
}
