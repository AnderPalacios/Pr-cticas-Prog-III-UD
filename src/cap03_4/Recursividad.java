package cap03_4;

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
		if (frase.length() == 0) {
			return frase;
		}
		int i = frase.length() - 1;
		while (i >= 0 && frase.charAt(i) != ' ') {
			i --;
		}
		String primeraPalabra;
		if (i < 0) {
			i = 0;
			primeraPalabra = frase.substring(i);
			
		} else {
			primeraPalabra = frase.substring(i+1);
		}
		frase = frase.substring(0,i);
//		System.out.println(primeraPalabra);
//		System.out.println(frase);
		return primeraPalabra + " " + invertirPalabra3(frase);
	}
	
}
