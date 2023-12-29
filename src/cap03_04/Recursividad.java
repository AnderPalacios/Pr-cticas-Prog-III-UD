package cap03_04;

import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cap03_04.Carta.Numeros;

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
	
	private static final int n_ParaFiltrosPoker = 5;
	private static final int sleep = 1500;
	private static HashSet<LinkedList<Carta>> setCartasEnBD = new HashSet<LinkedList<Carta>>();
	
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("----------------------------- Pruebas ejercicio 1:");
		String frase = invertirFrase1("Acabando 2023");
		System.out.println(frase);
		frase = invertirFrase1("Práctica de los temas 3 y 4");
		System.out.println(frase);
		frase = invertirFrase2("Ander Palacios autor de esta práctica");
		System.out.println(frase);
		frase = invertirFrase2("Primer ejercicio");
		System.out.println(frase);
		frase = invertirFrase3("Realizando práctica sobre recursividad");
		System.out.println(frase);
		frase = invertirFrase3("Método número \n tres del primer ejercicio");
		System.out.println(frase);
		
		Thread.sleep(sleep);
		System.out.println();
		System.out.println("----------------------------- Pruebas ejercicio 2:");
		String invertido;
		invertido = invertirPalabra("Realizando el segundo ejercicio de la práctica");
		System.out.println(invertido);
		invertido = invertirPalabra("Hola Ander y Mikel");
		System.out.println(invertido);
		invertido = invertirPalabra("FELIZ \t \n NAVIDAD");
		System.out.println(invertido);
		invertido = invertirPalabra(" [[ á ]] \n");
		System.out.println(invertido);
		
		Thread.sleep(sleep);
		//Cargo la baraja (el ArrayList) y después llamo a los métodos
		Carta.cargarCartas();
		System.out.println("----------------------------- Pruebas ejercicio 3");
		posiblesManos1(Carta.baraja, 3);
		Thread.sleep(500);
		posiblesManos2(new LinkedList<Carta>(), Carta.baraja, 2);
		Thread.sleep(500);
		posiblesManos2(new LinkedList<Carta>(), Carta.baraja, 4);
		
		Thread.sleep(sleep);
		System.out.println("----------------------------- Pruebas ejercicio 4");
		filtrarManosAs(new LinkedList<Carta>(), Carta.baraja, 2);
		Thread.sleep(500);
		filtrarManosPoker(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker); 
		Thread.sleep(500);
		filtrarManosFull(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
		Thread.sleep(500);
		filtrarManosEscalera(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
		
		Thread.sleep(sleep); //Cargar datos en la BD:
		System.out.println("----------------------------- Cargar BD, ejercicio 5");
		Thread.sleep(500);
		cargarBD();
		/**
		 * Hay varios métodos comentados ya que como son muchas combinaciones, tardan mucho en cargarse en la BD.
		 */
		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.D); //Como solo hay 24 combinaciones se cargan rápido en la BD
//		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.A); //Probar manualmente poniendo n = 1 o n = 2 en el método filtraManosGeneral cuando se llama a filtrarManosAs para poder ver las combinaciones en la BD 
//		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.D); //Para comprobar que no se insertan combinaciones que ya están en la BD.
//		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.B);
//		filtraManosGeneral(CODIGO_EXPLICACION_FILTRO.C);
		
	}
	
	
	//PRIMER EJERCICIO:
	private static String invertirFrase1(String frase) {
		if (frase.length() <= 1) {
			return frase; // En el caso base devuelvo esa última letra del string
		}
		char charActual = frase.charAt(0);
		char ultimoChar = frase.charAt(frase.length()-1);
		frase = frase.substring(1, frase.length()-1);
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
	private static String invertirPalabra(String frase) {
		//Con como tengo el código esta parte la puedo quitar. El caso base va a ser el de abajo
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
			primeraPalabra = frase.substring(i); //Desde i = 0 hasta el final (toda la frase, que resulta ser una palabra, la última)
			return primeraPalabra; //Me ahorro una llamada y este es el caso base.
			
		} else {
			primeraPalabra = frase.substring(i+1); //Desde i+1 hasta el final (la primera palabra)
			frase = frase.substring(0,i); //Aquí hago hasta i porque todavía no es la última palabra
		}
		return primeraPalabra + " " + invertirPalabra(frase);
	}
	
	
	//TERCER EJERCICIO:	
	private static LinkedList<Carta> cartasSel = new LinkedList<Carta>(); //Voy a hacerlo con LinkedList para quitar y meter por el final
	private static String aConsola = "";
	
	private static void posiblesManos1(ArrayList<Carta> baraja, int n) { //Visualizando las combinaciones sin usar una estructura de datos. (No es una muy buena solución)
		if (n == 0) {
			for (int i = 0; i < cartasSel.size(); i ++) {
				aConsola += cartasSel.get(i) + " ";
			}
			System.out.println(aConsola);
			aConsola = "";
		} else {
			for (Carta c: baraja) {
				if (!cartasSel.contains(c)) {
					cartasSel.addLast(c);
					posiblesManos1(baraja, n-1);
					cartasSel.removeLast();
				}
			}
		}
	}
	
	//Esta es una mejor solución:
	private static void posiblesManos2(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		  if (n == 0) {
			  System.out.println(cartasCombinadas);
		  } else {
			  for (Carta carta: baraja) {
				  if (!cartasCombinadas.contains(carta)) { //Las cartas no se pueden repetir
					  cartasCombinadas.addLast(carta);
					  posiblesManos2(cartasCombinadas, baraja, n-1);
					  cartasCombinadas.removeLast();
				  }
			  }
		  }
	}
	
	
	//CUARTO EJERCICIO
	private static void filtrarManosAs(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		  if (n == 0 && comprobarFiltroAs(cartasCombinadas)) {
			  System.out.println(cartasCombinadas);
			  numCartas.add(new LinkedList<Carta>(cartasCombinadas));
		  } else if (n > 0) { //Si no llego a poner n > 0 o lo llego a dejar con 'else' salta StackOverFlow ya que hay algunas combinaciones que no cumplen la condicion de que n sea 0 y que tengan algún AS. Por ello, se siguen haciendo llamadas con n < 0, lo que produce un StackOverFlow (no hay ningún caso para poder parar el programa)
			  for (Carta carta: baraja) {
				  if (!cartasCombinadas.contains(carta)) {
					  cartasCombinadas.addLast(carta);
					  filtrarManosAs(cartasCombinadas, baraja, n-1);
					  cartasCombinadas.removeLast();
				  }
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
	
	
	//Este y los siguientes filtros son de 'Poker'. n = 5 siempre
	private static void filtrarManosPoker(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		if (n == 0 && comprobarFiltroPoker(cartasCombinadas)) {
			System.out.println(cartasCombinadas);
			numCartas.add(new LinkedList<Carta>(cartasCombinadas));
		} else if (n > 0) {
			for (Carta c: baraja) {
				if (!cartasCombinadas.contains(c)) {
					cartasCombinadas.addLast(c);
					filtrarManosPoker(cartasCombinadas, baraja, n-1);
					cartasCombinadas.removeLast();
				}
		}
		}
	}
	
	private static boolean comprobarFiltroPoker(LinkedList<Carta> combinaciones) {
		int contador = 0;
		for (Carta carta: combinaciones) {
			for (Carta c: combinaciones) {
				if (carta.getNumero() == c.getNumero()) {
					contador ++;
				}
			}
			if (contador >= 4) {
				return true;
			}
			contador = 0;
		}
		return contador >= 4; // return false
	}
	
	
	private static void filtrarManosFull(LinkedList<Carta> cartasCombinadas, ArrayList<Carta> baraja, int n) {
		if (n == 0 && comprobarFiltroFull(cartasCombinadas)) {
			System.out.println(cartasCombinadas);
			numCartas.add(new LinkedList<Carta>(cartasCombinadas));
		} else if (n > 0){
			for (Carta carta: baraja) {
				if (!cartasCombinadas.contains(carta)) {
					cartasCombinadas.addLast(carta);
					filtrarManosFull(cartasCombinadas, baraja, n-1);
					cartasCombinadas.removeLast();
				}
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
			numCartas.add(new LinkedList<Carta>(cartasCombinadas)); //tengo que crear un nueva LinkedList por que sino, añado la Lista pero la sigo midificando después. Para prevenir esto, hago una 'copia' de como está la LinkedList en ese momento y la meto en mi ArrayList
		} else if (n > 0) {
			for (Carta carta: baraja) {
				if (!cartasCombinadas.contains(carta)) {
					cartasCombinadas.addLast(carta);
					filtrarManosEscalera(cartasCombinadas, baraja, n-1);
					cartasCombinadas.removeLast();
				}
			}
		}
	}
	
	
	private static boolean comprobarFiltroEscalera(LinkedList<Carta> cartas) {
		Carta carta1 = cartas.getFirst();
		//Tienen que ser todas del mismo palo
		for (Carta c: cartas) {
			if (carta1.getPalo() != c.getPalo()) {
				return false;
			}
		}
		//Coger la franja de cartas y comparar:
		int primeaPos = Carta.baraja.indexOf(carta1);
		if (primeaPos > Carta.baraja.size()-6) {
			return false; //Ya no puede haber mas escaleras (esto es así por el modo de hacer las combinaciones -> no se hacen aleatorias)
		}
		ArrayList<Carta> escalera = new ArrayList<Carta>(Carta.baraja.subList(primeaPos, primeaPos+cartas.size()));
		ArrayList<Carta> misCartas = new ArrayList<Carta>(cartas);
		return misCartas.equals(escalera);
	}
	
	
	private static Connection conn;
	private static Statement st;
	private static ResultSet rs;
	
	
	//Haciendo filtraManos para los 4 casos que voy a hacer
	private static void filtraManosGeneral(CODIGO_EXPLICACION_FILTRO codFiltro) {
		numCartas.clear();//Para que se quede vacío despúes de las llamadas hechas en el main (para empezar a guardar en la BD ya que utilizo este método para guardar combinaciones en mi BD)
		
		switch (codFiltro) {
		case A:
			int r = new Random().nextInt(4)+1;
			filtrarManosAs(new LinkedList<Carta>(), Carta.baraja, r);
			guardarDatosBD(codFiltro, noRepetirCartasEnBD(numCartas), r);
			numCartas.clear(); //Este clear se hace arriba pero lo hago aquí también por si se llama a algún metodo que no sea este y utilice el ArrayList.
			break;
		case B:
			filtrarManosPoker(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
			guardarDatosBD(codFiltro, noRepetirCartasEnBD(numCartas), n_ParaFiltrosPoker);
			numCartas.clear();
			break;
		case C:
			filtrarManosFull(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
			guardarDatosBD(codFiltro, noRepetirCartasEnBD(numCartas), n_ParaFiltrosPoker);
			numCartas.clear();
			break;
		case D:
			filtrarManosEscalera(new LinkedList<Carta>(), Carta.baraja, n_ParaFiltrosPoker);
			guardarDatosBD(codFiltro, noRepetirCartasEnBD(numCartas), n_ParaFiltrosPoker);
			numCartas.clear();
			break;
		default:
			System.err.println("Código del filtro mal introducido");
		}
}
	
	private static int minN = 9999;
	//Variable estática para saber si tengo que meter más columnas. Puedo filtrar combinaciones de n cartas y luego hacer otro filtro con combinaciones de x<n o x>n cartas.
	private static int numCol = 0;
	private static void guardarDatosBD(CODIGO_EXPLICACION_FILTRO codFiltro, ArrayList<LinkedList<Carta>> cartas , int n) {
		if (n < minN) {
			minN = n;
		}
		try {
			// - Tabla Filtro:
			st.executeUpdate("insert into Filtro values (' " + codFiltro + " ', ' " + codFiltro.descripcion + " ')");
			
			st.executeUpdate("insert into ManoFiltro values (' " + codFiltro + " ', ' " + cartas.size() + " ')");
			
			//Crear las columnas necesarias para añadir cada carta de cada combinación/mano
			ArrayList<ArrayList<String>> recuperadas = new ArrayList<ArrayList<String>>();
			boolean cambiar = false;
			if (n > numCol || n > minN) {
				cambiar = true;
				rs = st.executeQuery("select * from CartaMano");
				ArrayList<String> guardaCartas = new ArrayList<String>();
				while (rs.next()) {
					for (int i = 1; i < numCol+2; i ++) {
						String card = rs.getString(i);
						guardaCartas.add(card);
					}
					recuperadas.add(new ArrayList<String>(guardaCartas));
					guardaCartas.clear();
				}
				st.executeUpdate("delete from CartaMano");
				if (n > numCol) {
					for (int i = numCol+1; i <= ((numCol < n) ? n:numCol) ; i ++) {
						st.executeUpdate("alter table CartaMano add Carta" + i + " string");
					}

					numCol = n;
				}
				
			}
			if (cambiar) {
				int codMano = 0;
				for (LinkedList<Carta> comb: ordenar()) {
					//Meto todo a null y después voy actualizando:
					codMano ++;
					String original = " null, ";
					String veces = original.repeat(numCol-1);
					st.executeUpdate("insert into CartaMano values (' " + codMano + " ', " + veces + " null)");
					for (int i = 0; i < comb.size(); i ++) {
						st.executeUpdate("update CartaMano set Carta" + (i+1) +" = ' " + comb.get(i) + " '  where CodigoMano = ' " + codMano + " '");
					}
				}
			}
			else {
				int codMano = 0;
				for (LinkedList<Carta> comb: cartas) {
					//Meto todo a null y después voy actualizando:
					codMano ++;
					String original = " null, ";
					String veces = original.repeat(numCol-1);
					st.executeUpdate("insert into CartaMano values (' " + codMano + " ', " + veces + " null)");
					for (int i = 0; i < comb.size(); i ++) {
						st.executeUpdate("update CartaMano set Carta" + (i+1) +" = ' " + comb.get(i) + " '  where CodigoMano = ' " + codMano + " '");
					}
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error en la gestón de la base de datos");
		}
	}
	
	//Método para crear las tablas solo una vez:
	private static void cargarBD() {
		
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
			st.executeUpdate("create table Filtro (Codigo char(1), Descripcion string)"); //Para tener código y descripción de los filtros a los que se ha llamado.
			st.executeUpdate("create table ManoFiltro (CodigoFiltro char(1), numCombinaciones integer)"); //Para esta tabla voy a poner el código del filtro y el número de combinaciones creadas.
			st.executeUpdate("create table CartaMano (CodigoMano integer)"); //Modificar el número de columnas de esta tabla dependiendo n, el número de cartas en cada combinación.
				
			//Insertar datos:
			// - Tabla Carta (meter las cartas de la baraja)
			for (Carta carta: Carta.baraja) {
				String sent = "insert into Carta values (' " + carta.getNumero() + " ', ' " + carta.getPalo() + " ', ' " + carta +" ')";
				st.executeUpdate(sent);
			}
			
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error en la gestón de la base de datos");
	}
		
	}

	//Para no repetir combinaciones de cartas en la BD
	private static ArrayList<LinkedList<Carta>> noRepetirCartasEnBD(ArrayList<LinkedList<Carta>> combsCartas) {
	    ArrayList<LinkedList<Carta>> noRepetidas = new ArrayList<>();

	    for (LinkedList<Carta> combs : combsCartas) {
	        if (!setCartasEnBD.contains(combs)) {
	            noRepetidas.add(combs);
	            setCartasEnBD.add(combs);
	        }
	    }

	    return noRepetidas;
	}
	
	private static List<LinkedList<Carta>> ordenar() {
        List<LinkedList<Carta>> listaCartas = new ArrayList<>(setCartasEnBD);

        Collections.sort(listaCartas, (list1, list2) -> {
            // Comparar por tamaño (de mayor a menor)
            int compareSize = Integer.compare(list2.size(), list1.size());
            if (compareSize != 0) {
                return compareSize;
            }

            return list1.getFirst().compareTo(list2.getFirst());
        });
        return listaCartas;
	}
	
}


