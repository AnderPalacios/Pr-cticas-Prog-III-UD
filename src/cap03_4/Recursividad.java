package cap03_4;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cap03_4.Carta.Numeros;

public class Recursividad {
	
	//Las manos se van a poder filtrar de 4 posibles maneras
	public enum CODIGO_EXPLICACION_FILTRO {
		A("Manos que tiene al menos un as"),
		B("Cuatro de las 5 cartas tienen la misma figura"),
		C("3 cartas con la misma figura, 2 cartas con otra figura, en manos de 5 cartas."),
		D("5 cartas consecutivas del mismo palo");
		
		private final String descripcion;
		
		CODIGO_EXPLICACION_FILTRO(String desc){ //Constructor para crear filtros y asignar una descripción.
			this.descripcion = desc;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
	};
	
	//PREGUNTAR A CHAT SI HAGO UN ENUM CON LOS TEXTOS ECPLICANDO CASA FILTRO O FRASES ESTÁTICAS
	
	private static final int n_ParaFiltrosPoker = 5;
	
	
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
//		tres1(Carta.baraja, 2);
		//tres1(Carta.baraja, 3);
//		tres2(new LinkedList<Carta>(), Carta.baraja, 2);
//		tres3(new LinkedList<Carta>(), Carta.baraja, 2);
		
		//ESTOS SON LOS MÉTODOS INTERESANTES:
//		filtrarManos1(new LinkedList<Carta>(), Carta.baraja, 2);
//		filtrarManos2(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker); 
//		filtrarManos3(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
//		filtrarManosEscalera(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
		
		//PRUEBA (NO ES PARTE DE LA PRÁCTICA)
//		int[] precios = {200, 75, 112};
//		prubeaCompra(675, new LinkedList<Integer>(), precios);
//		LinkedList<Carta> cartas = new LinkedList<Carta>();
//		cartas.addLast(Carta.baraja.get(6)); cartas.addLast(Carta.baraja.get(7)); cartas.addLast(Carta.baraja.get(8)); cartas.addLast(Carta.baraja.get(9)); cartas.addLast(Carta.baraja.get(10));
//		System.out.println(cartas);
//		Collections.sort(cartas);
//		System.out.println(cartas);
//		Set<Carta> cart = new HashSet<Carta>(cartas);
//		System.out.println(cart);
//		System.out.println(Carta.baraja);
//		boolean car = comprobarFiltroFull(cartas);
//		System.out.println(car);
//		boolean pok = comprobarFiltroPoker(cartas);
//		System.out.println(pok);
//		boolean c = comprobarFiltroEscalera(cartas);
//		System.out.println(c);
		//System.out.println(CODIGO_EXPLICACION_FILTRO.A.getDescripcion());
		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.D);
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
	
	private static LinkedList<Carta> cartasSel = new LinkedList<Carta>(); //Voy a hacerlo con LinkedList para quitar el último
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
	
	//Esta es la mejor implementación
	private static void tres2(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		  if (n == 0) {
			  System.out.println(cartasCombinadas);
		  } else {
			  for (Carta carta: baraja) {
				  cartasCombinadas.addLast(carta);
				  tres2(cartasCombinadas, baraja, n-1);
				  cartasCombinadas.removeLast();
			  }
		  }
	}
	
	//Así ya funciona (que no haya repeticiones)
	private static void tres3(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		  if (n == 0) {
			  boolean h = false;
			  if (cartasCombinadas.get(0).equals(cartasCombinadas.get(1))) {
				  h = true;
			  }
			  if (!h) {
				  System.out.println(cartasCombinadas);
			  }
		  } else {
			  for (Carta carta: baraja) {
				  cartasCombinadas.addLast(carta);
				  tres3(cartasCombinadas, baraja, n-1);
				  cartasCombinadas.removeLast();
			  }
		  }
	}
	
	
	//CUARTO EJERCICIO
	private static void filtrarManos1(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		  if (n == 0 && comprobarFiltroAs(cartasCombinadas)) {
			  System.out.println(cartasCombinadas);
		  } else if (n > 0) { //Si no llego a poner n > 0 o lo llego a dejar con 'else' salta el StackOverFlow ya que hay algunas combinaciones que no cumplen la condicion de que n sea 0 y que tengan algún AS. Por ello, se siguen haciendo llamadas con n < 0, lo que produce un StackOverFlow (no hay ningún caso para poder parar el programa)
			  for (Carta carta: baraja) {
				  cartasCombinadas.addLast(carta);
				  filtrarManos1(cartasCombinadas, baraja, n-1);
				  cartasCombinadas.removeLast();
			  }
		  }
	}
	
	private static boolean comprobarFiltroAs(LinkedList<Carta> combinaciones) {
		for (Carta carta: combinaciones) {
			if (carta.getNumero() == Numeros.I) {
				return true;
			}
		}
		return false;
	}
	
	
	//Esta es la de Poker. n = 5 siempre
	private static void filtrarManos2(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		if (n == 0 && comprobarFiltroPoker(cartasCombinadas)) {
			System.out.println(cartasCombinadas);
		} else if (n > 0) {
			for (Carta c: baraja) {
				cartasCombinadas.addLast(c);
				filtrarManos2(cartasCombinadas, baraja, n-1);
				cartasCombinadas.removeLast();
		}
		}
	}
	
	private static boolean comprobarFiltroPoker(LinkedList<Carta> combinaciones) {
		int contador = 0; //Mirar por que la tengo que inicializar
		for (Carta carta: combinaciones) {
			for (Carta c: combinaciones) {
				if (carta.getNumero() == c.getNumero()) {
					contador ++;
				}
			}
			//System.out.println(contador);
			if (contador >= 4) {
				return true;
			}
			contador = 0;
		}
		return contador >= 4; // return false
	}
	
	
	private static void filtrarManos3(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		if (n == 0 && comprobarFiltroFull(cartasCombinadas)) {
			System.out.println(cartasCombinadas);
		} else if (n > 0){
			for (Carta carta: baraja) {
				cartasCombinadas.addLast(carta);
				filtrarManos3(cartasCombinadas, baraja, n-1);
				cartasCombinadas.removeLast();
			}
		}
	}
	
	private static boolean comprobarFiltroFull(LinkedList<Carta> combinaciones) {
		int contador = 0;
		ArrayList<Carta> cartasProbar = new ArrayList<Carta>();
		Carta carta = combinaciones.getFirst();
		for (Carta c: combinaciones) {
			if (carta.getNumero() == c.getNumero()) {
				contador ++;
			} else {
				cartasProbar.add(c);
			}
		}
		//System.out.println(cartasProbar);
		if (!(contador == 2 || contador == 3)) {
			return false;
		} 
		contador = 0;
		carta = cartasProbar.get(0);
		for (Carta card: cartasProbar) {
			if (carta.getNumero() == card.getNumero()) {
				contador ++;
			}
		}
		return contador == cartasProbar.size();
	}
	
	private static ArrayList<LinkedList<Carta>> numCartas = new ArrayList<LinkedList<Carta>>();
	private static void filtrarManosEscalera(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		if (n == 0 && comprobarFiltroEscalera(cartasCombinadas)) {
			System.out.println(cartasCombinadas);
			numCartas.add(new LinkedList<Carta>(cartasCombinadas));
			System.out.println(numCartas);
		} else if (n > 0) {
			for (Carta carta: baraja) {
				cartasCombinadas.addLast(carta);
				filtrarManosEscalera(cartasCombinadas, baraja, n-1);
				cartasCombinadas.removeLast();
			}
		}
	}
	
	
	private static boolean comprobarFiltroEscalera(LinkedList<Carta> cartas) {
		//Collections.sort(cartas);
		Carta carta1 = cartas.getFirst();
		//Tienen que ser todas del mismo palo
		for (Carta c: cartas) {
			if (carta1.getPalo() != c.getPalo()) {
				return false;
			}
		}
		//Coger la franja de cartas ya ordenadas y comparar:
		int primeaPos = Carta.baraja.indexOf(carta1);
		if (primeaPos > 35) {
			return false; //Ya no puede haber mas escaleras (esto es así por el modo de hacer las combinaciones -> no se hacen aleatorias)
		}
		ArrayList<Carta> escalera = new ArrayList<Carta>(Carta.baraja.subList(primeaPos, primeaPos+cartas.size()));
		//System.out.println(escalera);
		ArrayList<Carta> misCartas = new ArrayList<Carta>(cartas);
		return misCartas.equals(escalera);
	}
	
	
	private static Connection conn;
	private static Statement st;
	private static ResultSet rs;
	
	
	//Haciendo filtraManos para los 4 casos que voy a hacer
	private static void filtraManosGeneral(CODIGO_EXPLICACION_FILTRO codFiltro) {
		
		ConexionBD conexion = new ConexionBD();
		try {
			conn = conexion.conectarConBD();
			
			st = conn.createStatement();
			
			st.executeUpdate("drop table if exists Carta");
			st.executeUpdate("drop table if exists Filtro");
			st.executeUpdate("drop table if exists ManoFiltro");
			st.executeUpdate("drop table if exists CartaMano");
			
			//Crear las 4 tablas:
			st.executeUpdate("create table Carta (Numero string, Palo string, nombreCarta string)");
			st.executeUpdate("create table Filtro (Codigo char(1), Descripcion string)");
			st.executeUpdate("create table ManoFiltro (CodigoFiltro char(1), numCombinaciones integer)"); //Para esta tabla voy a poner el código del filtro y el número de combinaciones creadas.
			st.executeUpdate("create table CartaMano (CodigoMano integer)"); //Modificar el número de columnas de esta tabla dependiendo n, el número de cartas en cada combinación.
			
			//Inicializar la tabla de cartas si no está creada:
//			rs = st.executeQuery("SHOW TABLES LIKE 'Carta'");
//			if (!rs.next()) {
//				st.executeUpdate("create table Carta (Numero string, Palo string, nombreCarta string)");
//			}
			
			//Insertar datos:
			// - Tabla Carta
			for (Carta carta: Carta.baraja) {
				String sent = "insert into Carta values (' " + carta.getNumero() + " ', ' " + carta.getPalo() + " ', ' " + carta +" ')";
				st.executeUpdate(sent);
			}
			
			// - Tabla Filtro:
			st.executeUpdate("insert into Filtro values (' " + codFiltro + " ', ' " + codFiltro.descripcion + " ')");
			
			
			switch (codFiltro) {
			case A:
				filtrarManos1(new LinkedList<Carta>(), Carta.baraja, new Random().nextInt(4)+1); //Mirar esto
				break;
			case B:
				filtrarManos2(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
				break;
			case C:
				filtrarManos3(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
				break;
			case D:
				filtrarManosEscalera(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
				st.executeUpdate("insert into ManoFiltro values (' " + codFiltro + " ', ' " + numCartas.size() + " ')");
				//Crear las columnas necesarias para añadir cada carta de cada combinación/mano 
				for (int i = 1; i <= n_ParaFiltrosPoker; i ++) {
					st.executeUpdate("alter table CartaMano add Carta" + i + " string");
				}
				int codMano = 0;
				for (LinkedList<Carta> comb: numCartas) {
					//Meto todo a null y después voy actualizando:
					codMano ++;
					System.out.println(comb.size());
					st.executeUpdate("insert into CartaMano values (' " + codMano + " ', null, null, null, null, null)");
					for (int i = 0; i < comb.size(); i ++) {
						st.executeUpdate("update CartaMano set Carta" + (i+1) +" = ' " + comb.get(i) + " '  where CodigoMano = ' " + codMano + " '");
					}
				}
				break;
			default:
				System.err.println("Código del filtro mal introducido");
			}
			
		}
		
		 catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Error en la gestón de la base de datos");
			}
	}
	
}


