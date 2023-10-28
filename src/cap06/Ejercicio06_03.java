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

import cap06.VentanaTablaDatos;

public class Ejercicio06_03 {
	
	private static JFrame ventana;
	private static DataSetMunicipios dataset;

	//private static VentanaTablaDatos ventanaDatos;
	
	//Para práctica 6
	private static GestionDatos ventPrueba;
	
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
			
			ventPrueba = new GestionDatos();
			ventPrueba.setDatos(dataset);
			ventPrueba.setVisible( true );
			
		} catch (IOException e) {
			System.err.println( "Error en carga de municipios" );
		}
	}
	
}
