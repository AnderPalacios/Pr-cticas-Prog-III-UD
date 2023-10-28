package cap06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class VentanaComponentes extends JFrame{
	
	private static final Dimension TAMAYO = new Dimension(480, 640);
	
	//Hago este main para ir probando:
	public static void main(String[] args) {
		
		VentanaComponentes vent = new VentanaComponentes();
		//vent.crearNodo("Hola", new DefaultMutableTreeNode("Raiz"));
		vent.setVisible( true );
		
	}
	
	//Atributos:
	
	private JTree tree;
	private DefaultTreeModel modeloArbol;
	private JTable tablaDatos;
	private DefaultTableModel modelotabla; //Me creo un modelo personalizado
	
	private DataSetMunicipios datosMunis;
	
	
	public VentanaComponentes() {
		
		setTitle("VentanaComponentes");
		setSize( TAMAYO );
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		// Componente 1
		JLabel lblMensaje = new JLabel(); // Inicialmente vacío
		add(lblMensaje, BorderLayout.NORTH);
		
		//Componente 2
		tree = getTree(null); //Inicialmente vacío. Luego al cargar los datos los voy rellenando
//		tree.addMouseListener(new MouseAdapter() {
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				TreePath tp = tree.getPathForLocation(e.getX(), e.getY()); //Dame la ruta para la pasición de ratón
//				if (tp != null) {
//					DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) tp.getLastPathComponent(); //Pillo el nodo
//					if (nodoSeleccionado.isLeaf()) { //Si es una hoja significa que no tiene hijos. En este caso una provincia
//						cargarPorProvincia((String) nodoSeleccionado.getUserObject());
//					}
//				}
//			}
//		});
		
		//Componente 3
		tablaDatos = new JTable();
//		modelotabla = new DefaultTableModel(new String[] {"Código","Nombre", "Habitantes","Provincia","Autonomía"}, 0);
//		tablaDatos.setModel(modelotabla);
		
//		tablaDatos.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
//			
//			private JProgressBar pb = new JProgressBar(50000, 5000000);
//
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int column) {
//				
//				if (column == 2) { //La columna de habitantes
//					pb.setValue((int)value);
//					return pb;
//				}
//				
//				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			}
//		});
		
		JScrollPane scrollTabla = new JScrollPane(tablaDatos);
		add(scrollTabla, BorderLayout.CENTER);
		
		//Componente 4:
		JPanel pnlVisualizacion = new JPanel();
		add(pnlVisualizacion, BorderLayout.EAST);
		
		//Componente 5:
		JPanel pnlBotonera = new JPanel();
		JButton btnInsertar = new JButton("Insertar");
		JButton btnBorrar = new JButton("Borrar");
		JButton btnOrdenar = new JButton("Ordenar");
		pnlBotonera.add(btnInsertar);
		pnlBotonera.add(btnBorrar);
		pnlBotonera.add(btnOrdenar);
		
		add(pnlBotonera, BorderLayout.SOUTH);
		
	}

	public JTree getTree(DefaultMutableTreeNode raiz) {
		tree = new JTree();
		modeloArbol = new DefaultTreeModel(raiz);
		tree.setModel(modeloArbol);
		JScrollPane scrollTree = new JScrollPane(tree);
		add(scrollTree, BorderLayout.WEST);
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}
	
	
	public JTable getTablaDatos() {
		return tablaDatos;
	}

	public void setTablaDatos(JTable tablaDatos) {
		this.tablaDatos = tablaDatos;
	}

	public DefaultMutableTreeNode crearNodo(String dato, DefaultMutableTreeNode nodoPadre) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);
		//nodoPadre.add(nodo);
		modeloArbol.insertNodeInto(nodo, nodoPadre, 0);
		return nodo;
	}
	
	//Tengo todos los datos cargados
	public void setDatos(DataSetMunicipios datosMunis) {
		this.datosMunis = datosMunis;
		modelotabla = new DefaultTableModel(new String[] {"Código","Nombre", "Habitantes", "Población", "Provincia","Autonomía"}, 0);
		tablaDatos.setModel(modelotabla);
		tablaDatos.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
			
			private JProgressBar pb = new JProgressBar(50000, 5000000);

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				if (column == 3) { //La columna de habitantes
					pb.setValue((int)value);
					return pb;
				}
				
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		});
		
	}
	
	public void cargarPorProvincia(String provincia) {
		for (Municipio m: datosMunis.getListaMunicipios()) {
			if (m.getProvincia().equals(provincia)) {
				modelotabla.addRow(new Object[] {m.getCodigo(), m.getNombre(), m.getHabitantes(),new JProgressBar(50000, 5000000), m.getProvincia(), m.getAutonomia()});
			}
		}
	}
	
	
	//Hago una clase interna que implemente TableModel para modificarla como yo quiera
	private class MiModeloTabla implements TableModel {

		@Override
		public int getRowCount() {
			return datosMunis.getListaMunicipios().size();
		}

		@Override
		public int getColumnCount() {
			return 5;
		}
		
		private String[] NOM_COLUMNAS = {"Código", "Nombre", "Habi"};
		@Override
		public String getColumnName(int columnIndex) {
			return NOM_COLUMNAS[columnIndex];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 1 || columnIndex == 2) {
				return true;
			}
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
