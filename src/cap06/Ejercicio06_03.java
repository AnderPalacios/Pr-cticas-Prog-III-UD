package cap06;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cap06.VentComponentes;
import cap06.VentanaComponentes;
import cap06.VentanaTablaDatos;

public class Ejercicio06_03 {
	
	private static JFrame ventana;
	private static DataSetMunicipios dataset;

	private static VentanaTablaDatos ventanaDatos;
	
	//Para práctica 6
	private static VentanaComponentes ventanaComp; //(es de la otra ventana que tengo)
	private static VentComponentes ventComp;
	private static Prueba ventPrueba;
	private static DefaultMutableTreeNode raiz;
	
	public static void main(String[] args) {
		ventana = new JFrame( "Ejercicio 6.3" );
		ventana.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		ventana.setLocationRelativeTo( null );
		ventana.setSize( 200, 80 );

		JButton bCargaMunicipios = new JButton( "Carga municipios > 200k" );
		ventana.add( bCargaMunicipios );
		
		bCargaMunicipios.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargaMunicipios();
			}
		});
		
		ventana.setVisible( true );
	}
	
	private static void cargaMunicipios() {
		try {
			dataset = new DataSetMunicipios( "Municipios 100-200.txt" );
			System.out.println( "Cargados municipios:" );
			for (Municipio m : dataset.getListaMunicipios() ) {
				System.out.println( "\t" + m );
			}
			// TODO Resolver el ejercicio 6.3
//			ventanaDatos = new VentanaTablaDatos( ventana );
//			ventanaDatos.setDatos( dataset );
//			ventanaDatos.setVisible( true );
//			
//			// TODO Resolver práctica 6 (apartado 3)
//			ventComp =  new VentComponentes();
//			ventComp.setDatos(dataset);
//			ventComp.setVisible( true );
			
			
//			ventanaComp = new VentanaComponentes();
//			ventanaComp.setDatos( dataset );
//			ventanaComp.setVisible( true );
//			raiz = new DefaultMutableTreeNode("Municipios");
//			JTree tree = ventanaComp.getTree(raiz);
//			for (Municipio m: dataset.getListaMunicipios()) {
//				DefaultMutableTreeNode nodoPadre = ventanaComp.crearNodo(m.getNombre(), raiz); //Para este segundo nivel, la raíz es el padre de todos
//				ventanaComp.crearNodo(m.getProvincia(), nodoPadre );
//			}
//			//Estas dos líneas son purba (funcionan)
//			ventanaComp.setDatos(dataset);
//			tree.addMouseListener(new MouseAdapter() {
//				
//				@Override 
//				public void mouseClicked(MouseEvent e) {
//					TreePath tp = tree.getPathForLocation(e.getX(), e.getY()); //Dame la ruta para la pasición de ratón
//					if (tp != null) {
//						DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) tp.getLastPathComponent(); //Pillo el nodo
//						if (nodoSeleccionado.isLeaf()) { //Si es una hoja significa que no tiene hijos. En este caso una provincia
//							ventanaComp.cargarPorProvincia((String) nodoSeleccionado.getUserObject());
//						}
//					}
//				}
//			});
//			
//			JTable tabla = ventanaComp.getTablaDatos();
//			tabla.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
//				
//				private JProgressBar pb = new JProgressBar(50000, 5000000);
//
//				@Override
//				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//						boolean hasFocus, int row, int column) {
//					
//					if (column == 2) { //La columna de habitantes
//						pb.setValue((int)value);
//						return pb;
//					}
//					
//					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//				}
//			});
			//ventanaComp.cargarPorProvincia("Madrid");
			//Aquí lo haré visible
			//ventanaComp.setVisible( true );
			
			ventPrueba = new Prueba();
			ventPrueba.setDatos(dataset);
			ventPrueba.setVisible( true );
//			JTree tree = ventPrueba.getTree();
//			tree.addMouseListener(new MouseAdapter() {
//				
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					if (e.getClickCount() == 1) {
//						TreePath tp = tree.getPathForLocation(e.getX(), e.getY()); //Dame una ruta para una posición de ratón
//						if (tp != null) {
//							DefaultMutableTreeNode nodoSel = (DefaultMutableTreeNode)tp.getLastPathComponent();
//							System.out.println("Nodo seleccionado " + nodoSel.getUserObject().getClass().getName() + " " +  nodoSel.getUserObject());
//						}
//					}
//					
//				}
//			});
			
			
		} catch (IOException e) {
			System.err.println( "Error en carga de municipios" );
		}
	}
	
}
