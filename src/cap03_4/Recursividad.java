package cap03_4;

public class Recursividad {
	
	public static void main(String[] args) {
		invertirFrase1("Hola", "", 3);
		String frase = invertirFrase2("Holaaaaa");
		System.out.println(frase);
		
		System.out.println("dhhd");
		
		invertirPalabra("Hola Ander");
		
		
	}
	
	private static void invertirFrase1(String stringInicial, String stringInvertido, int n) {
		if (n < 0) {
			System.out.println(stringInvertido);
			return;
		}
		stringInvertido += stringInicial.charAt(n);
		invertirFrase1(stringInicial, stringInvertido, n-1);
		//System.out.println(stringInvertido); Para ver el stack de como se van añadiendo las letras
	}
	
	private static String invertirFrase2(String frase) {
		if (frase.length() <= 1) {
			return frase; // En el caso base devuelvo esa última letra del string
		}
		char charActual = frase.charAt(0);
		char ultimoChar = frase.charAt(frase.length()-1); //Empieza desde 0
		//System.out.println(frase.length());
		frase = frase.substring(1, frase.length()-1);
		//System.out.println(frase);
		return ultimoChar + invertirFrase2(frase) + charActual;
		
	}
	
	private static void invertirPalabra(String frase) {
		System.out.println("juashcdhf");
		int i = 0;
		while (frase.charAt(i) != ' ') {
			i ++;
			//System.out.println(i+"jasndjn");
		}
		String palabraActual = frase.substring(0,i);
		System.out.println(palabraActual);
	}
	
}
