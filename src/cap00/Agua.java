package cap00;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cap00.ElementoEcosistema;

public class Agua extends ElementoEcosistema {
	
	protected Long cantidad;
	protected JPanel panelAgua;

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}


	@Override
	public String toString() {
		return "Agua: " + getTitulo() + " - " + getCantidad() + " metros cúbicos - " + "Coord (" + getPunto().x 
				+ "," + getPunto().y + ") - Tamaño ("+ getDimension().width + "," + getDimension().height + ")";
	}
	
	public Agua() {
		this.cantidad = (long) 0;
		this.panelAgua = null;
	}

	public Agua(String titulo, Point punto, Dimension dimension, Long cantidad) {
		super(titulo, punto, dimension);
		this.cantidad = cantidad;
	}

	@Override
	public JPanel getPanel() {
		int contador = 0;
		if (panelAgua == null) {
			contador ++;
			panelAgua = new JPanel();
			panelAgua.setLayout(new GridLayout(3,1));
//			JLabel lblNombre = new JLabel("Lago"+contador);
//			JLabel lblPoblacion = new JLabel(this.cantidad+"");
//			JLabel lblTipo = new JLabel("Abejas");
//			lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
//			lblPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
//			lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
//			panelAgua.add(lblNombre);
//			panelAgua.add(lblPoblacion);
//			panelAgua.add(lblTipo);
		}
		return panelAgua;
	}

	@Override
	public void evoluciona(int dia) {
	}
	
	
	
}
