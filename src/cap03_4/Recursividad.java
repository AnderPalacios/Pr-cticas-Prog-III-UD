package cap03_4;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class Recursividad {
	
	public static void main(String[] args) {
		
		System.out.println("Pruebas ejercicio 1:");
		String frase = invertirFrase1("Holaaaaa");
		System.out.println(frase);
		frase = invertirFrase2("Holaa12 2123");
		System.out.println(frase);
		frase = invertirFrase3("Realizando práctica sobre recursividad");
		System.out.println(frase);
		
		
		System.out.println();
		System.out.println("Pruebas ejercicio 2:");
		String invertido;
		invertido = invertirPalabra3("Hola Ander");
		System.out.println(invertido);
		invertido = invertirPalabra3("Hola Ander y Mikel");
		System.out.println(invertido);
		
		
		//Cargo la baraja (el ArrayList) y después llamo al método
		Carta.cargarCartas();
		tres(Carta.baraja, 2, "");
		System.out.println("-----------------------------");
		tres1(Carta.baraja, 2);
		//tres1(Carta.baraja, 3);
		
	}
	
	//PRIMER EJERCICIO:
	private static String invertirFrase1(String frase) {
		if (frase.length() <= 1) {
			return frase; // En el caso base devuelvo esa última letra del string
		}
		char charActual = frase.charAt(0);
		char ultimoChar = frase.charAt(frase.length()-1);
		frase = frase.substring(1, frase.length()-1);
		//System.out.println(frase);
		return ultimoChar + invertirFrase1(frase) + charActual;
		
	}
	
	private static String invertirFrase2(String frase) {
		if (frase.length() == 0) {
			return "";
		}
		char ultimaLetra = frase.charAt(frase.length()-1);
		frase = frase.substring(0,frase.length()-1);
		return ultimaLetra + invertirFrase2(frase);
	}
	
	private static String invertirFrase3(String frase) {
		if (frase.length() == 0) {
			return "";
		}
		return frase.charAt(frase.length()-1) + invertirFrase3(frase.substring(0,frase.length()-1));
	}
	
	//SEGUNDO EJERCICIO:
	private static String invertirPalabra3(String frase) {
		//Con como tengo el código esto lo puedo quitar. SEGUIR AQUI
//		if (frase.length() == 0) { 
//			return frase;
//		}
		int i = frase.length() - 1;
		while (i >= 0 && frase.charAt(i) != ' ') {
			i --;
		}
		String primeraPalabra;
		if (i < 0) {
			i = 0;
			primeraPalabra = frase.substring(i); //Desde 0 hasta el final
			return primeraPalabra; //Me ahorro una llamada
			
		} else {
			primeraPalabra = frase.substring(i+1);
			frase = frase.substring(0,i); //Aquí hago hasta i porque todavía no es la última palabra
		}
		/**
		 * frase = frase.substring(0,i); Al principio estaba aquí, ahora la he puesto arriba
		 * el else de arriba se ejecuta en todas las llamadas menos en la última SEGUIR AQUI
		 */
//		System.out.println(primeraPalabra);
//		System.out.println(frase);
		return primeraPalabra + " " + invertirPalabra3(frase);
	}
	
	//TERCER EJERCICIO:
	private static void tres(ArrayList<Carta> baraja, int n, String concat) {
		if (n == 0) {
			System.out.println(concat);
		} else {
			for (Carta c: baraja) {
				tres(baraja, n-1, concat + " y " + c); //Cambiar este string
			}
		}
		
	}
	
	private static LinkedList<Carta> cartasSel = new LinkedList<Carta>(); //Voy a hacerlo con linkedList para quitar el último
	private static String aConsola = "";
	
	private static void tres1(ArrayList<Carta> baraja, int n) {
		if (n == 0) {
			return;
		} else {
			for (Carta c: baraja) {
				cartasSel.addLast(c);
				tres1(baraja, n-1);
				//Aquí:
				for (int i = 0; i < cartasSel.size(); i ++) {
					aConsola += cartasSel.get(i) + " ";
				}
				//Esta condición MIRARLA para que no imprima ninguna combinación que no sea de n cartas.
				//cartas.size()-1 (para simular el removeLast) (probarlo sin el -1 para entenderlo)
				if (cartasSel.size()-1 > n-1) { //Para no imprimir cuando solo tengo una carta (esto pasa al llegar a la llamada que hace la combinación de una carta con todas las demás)
					System.out.println(aConsola);
				}
				aConsola = "";
				//System.out.println(cartasSel);
//				aConsola += cartasSel.getLast() + " ";
				cartasSel.removeLast();
			}
//			cartasSel.clear();
//			System.out.println(cartasSel + "Este es al arrayList");
			return;
		}
		
	}
	
}
