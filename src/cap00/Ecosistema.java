package cap00;

import java.util.*;

import cap00.ElementoEcosistema;

public class Ecosistema {
	
	//Parte static
	private static Ecosistema ecosistema = new Ecosistema();
	
	public static Ecosistema getMundo() {
		return ecosistema;
	}

	//Parte no static
	protected ArrayList<ElementoEcosistema> listaEEs;

	public ArrayList<ElementoEcosistema> getElementos() {
		return listaEEs;
	}

	public void setListaEEs(ArrayList<ElementoEcosistema> listaEEs) {
		this.listaEEs = listaEEs;
	}
	
	public Ecosistema() {
		this.listaEEs = new ArrayList<ElementoEcosistema>();
	}
	
	//Cálculo de la distancia para el método evolucionar
	public static double distancia(ElementoEcosistema e1, ElementoEcosistema e2) {
		double distanciaX = e1.getPunto().getX() - e2.getPunto().getX();
		double distanciaY = e1.getPunto().getY() - e2.getPunto().getY();
		return Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));
	}
}
