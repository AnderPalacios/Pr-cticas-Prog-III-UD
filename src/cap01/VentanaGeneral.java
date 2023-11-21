package cap01;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/*
 * IDEAS:
 * Interfaz de usuario gráfica (GUI): Podrías tener una ventana principal que muestre una lista 
 * de todos los libros disponibles en la biblioteca. Esta lista podría incluir detalles como el título del 
 * libro, el autor y si el libro está actualmente prestado o no. También podrías tener una función de 
 * búsqueda que permita a los usuarios buscar libros por título o autor. Además, podrías tener ventanas 
 * separadas para funciones como reservar un libro, devolver un libro, etc. (Hacer un ventana para que el Usuario
 * se registre en la biblio online y así hacer tests con su gmail...)
Librería externa: Podrías utilizar la librería Apache POI para leer y escribir datos en archivos Excel. 
Esto te permitiría almacenar la información de los libros en un archivo Excel, que podrías leer al iniciar 
la aplicación para cargar la lista de libros. También podrías utilizar esta librería para actualizar el 
archivo Excel cada vez que un libro sea reservado o devuelto.

Pruebas unitarias con JUnit: Podrías escribir pruebas unitarias para las funciones que manejan la reserva y 
devolución de libros. Por ejemplo, podrías tener una prueba que verifique que un libro no pueda 
ser reservado si ya está prestado. También podrías tener una prueba que verifique que un libro sea 
marcado como disponible después de ser devuelto.

Registro de eventos (logging): Podrías utilizar la librería Log4j para registrar eventos como la reserva 
y devolución de libros. Estos registros podrían incluir detalles como qué libro fue reservado/devuelto y 
cuándo ocurrió el evento. Podrías guardar estos registros en un archivo de texto para poder revisarlos más 
tarde.
 */


public class VentanaGeneral extends JFrame{
	
	private static final Dimension TAMAYO_VENT = new Dimension(480,640);
	
	//main para ir probando
	public static void main(String[] args) {
		
	}
	
	//Protected para las pruebas
	protected JButton btnCragar;
	protected JList<Libro> librosDisponibles;
	protected JList<Libro> librosAlquilados;
	
	private ArrayList<Libro> listaLibrosBiblioteca;
	private JTable tablaLibros; //En principio no haré pruebas con la tabla pero añadiré una JTable;
	private ModeloTabla modelo;
	
	
	public VentanaGeneral() {
		setSize(TAMAYO_VENT);
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("BIBLIOTECA ONLINE");
	}
	
	
	
	private class ModeloTabla implements TableModel {

		@Override
		public int getRowCount() {
			return listaLibrosBiblioteca.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}
		
		private String[] cabeceras = {"Título","Autor","ISBN","Prestado"};
		@Override
		public String getColumnName(int columnIndex) {
			return cabeceras[columnIndex];
		}

		private Class<?>[] tipoColumna = {String.class, String.class, String.class, Boolean.class};
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return tipoColumna[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
