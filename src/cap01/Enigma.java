package cap01;

import java.io.Serializable;

public class Enigma implements Serializable { //Es el objeto que voy a enviar, implementa Serializable para desmenuzarse en bytes para poder ser enviados
	
	private static String ABECEDARIO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	private static String abecedario = "abcdefghijklmnñopqrstuvwxyz";
	private String destino;
	protected String mensaje;
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public Enigma() { 
		this.destino = "";
		this.mensaje = "";
	}
	
	public Enigma(String destino, String mensaje) {
		super();
		this.destino = destino;
		this.mensaje = mensaje;
	}
	
	@Override
	public String toString() {
		return " - " + destino + ": " + mensaje;
	}
	
	/**
	 * Es un método para simular los mensajes encriptados de Alemania en la segunda guerra mundial.
	 * Este método de encriptación consiste en sustituir cada letra (mayúscula o minúscula y sin tener en cuenta las tildes).
	 * Por el modo de operar de la funcion cualquier letra con tilde se sustituye por la letra c en este método.
	 * El resto de letras se sustituyen por la letra que en el abecedario ocupa la posición de esa letra a reemplzar mas tres posiciones. 
	 * De las letras X, Y, Z se vuelve a pasar al inicio del abecedario.
	 * Además, el método sustituye las comas por los puntos para que sea mas complicado poder descifrarlo.
	 * Como en los mensajes eran importatntes las fechas, también se reemplazan los números: Si el numero
	 * es mayor o igual que 5 se le suma dos y si es menor se resta 1 (de 9 se pasa a 0 y de 0 a 9 al restar 1). 
	 */
	protected static String encriptar(String mensajeAEnviar) {
		
	    StringBuilder mensajeEncriptado = new StringBuilder();

	    for (int i = 0; i < mensajeAEnviar.length(); i++) {
	        char caracterActual = mensajeAEnviar.charAt(i);

	        if (Character.isUpperCase(caracterActual)) {
	            mensajeEncriptado.append(ABECEDARIO.charAt((ABECEDARIO.indexOf(mensajeAEnviar.charAt(i)) + 3) % ABECEDARIO.length()));
	        } else if (Character.isLowerCase(caracterActual)) {
	            mensajeEncriptado.append(abecedario.charAt((abecedario.indexOf(mensajeAEnviar.charAt(i)) + 3) % abecedario.length()));
	        } else if (caracterActual == ',') {
	            mensajeEncriptado.append(".");
	        } else if (Character.isDigit(caracterActual)){
	            int numero = Character.getNumericValue(caracterActual);
	            if (numero >= 5) {
	                mensajeEncriptado.append((numero + 2) % 10);
	            } else {
	                mensajeEncriptado.append((numero - 1 + 10) % 10);
	            }
	        } else {
	        	mensajeEncriptado.append(caracterActual);
	        }
	    }

	    return mensajeEncriptado.toString();
	}
	
}
