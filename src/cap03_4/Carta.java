package cap03_4;

import java.util.ArrayList;

public class Carta implements Comparable<Carta>{
	
	enum Numeros {I, II, III, IV, V, VI, VII, X, XI, XII};
	enum Palo {OROS, BASTOS, ESPADAS, CORONAS};
	
	protected static final ArrayList<Carta> baraja = new ArrayList<Carta>();
	
	private Numeros numero;
	private Palo palo;
	
	
	
	public Numeros getNumero() {
		return numero;
	}

	public void setNumero(Numeros numero) {
		this.numero = numero;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public static ArrayList<Carta> getBaraja() {
		return baraja;
	}
	
	public Carta() {
		cargarCartas();
	}
	
	public Carta(Numeros numero, Palo palo) {
		this.numero = numero;
		this.palo = palo;
	}
	
	protected static void cargarCartas() {
		for (Palo p: Palo.values()) {
			for (Numeros n: Numeros.values()) {
				Carta c = new Carta(n, p);
				baraja.add(c);
			}
		}
	}
	
	@Override
	public String toString() {
		return numero + " - " + palo;
	}

	public static void main(String[] args) {
		Carta.cargarCartas();
		System.out.println(Carta.baraja);
	}

	@Override
	public int compareTo(Carta o) {
		int comparaPalo = this.palo.compareTo(o.palo);
		if (comparaPalo != 0) {
			return comparaPalo;
		}
		return this.numero.compareTo(o.numero);
	}
	
	
}


