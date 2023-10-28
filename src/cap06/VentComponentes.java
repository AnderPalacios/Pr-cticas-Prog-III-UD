package cap06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class VentComponentes extends JFrame{
	
	private static final Dimension TAMAYO = new Dimension(480, 640);
	
	public static void main(String[] args) {
		
		VentComponentes ventC = new VentComponentes();
		ventC.setVisible( true );
		
	}
	
	private DataSetMunicipios datosMunis;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode raiz; //Lo creo como argumento porque lo necesitaré como argumneto
	private JTable tablaDatos;
	private MiModeloTabla modeloTabla;
	
	public VentComponentes() {
		
		setTitle("VentanaComponentes");
		setSize( TAMAYO );
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JLabel lblMensaje = new JLabel();
		getContentPane().add(lblMensaje, BorderLayout.NORTH);

		
		tree = new JTree();
		add(new JScrollPane(tree), BorderLayout.WEST);
		
		tablaDatos = new JTable();
		add(new JScrollPane(tablaDatos), BorderLayout.CENTER);
		
		JPanel pnlVisualizacion = new JPanel();
		pnlVisualizacion.setBackground( Color.red );
		add(pnlVisualizacion, BorderLayout.EAST);
		
		//Componente 5:
		JPanel pnlBotonera = new JPanel();
		JButton btnInsertar = new JButton("Insertar");
		JButton btnBorrar = new JButton("Borrar");
		JButton btnOrdenar = new JButton("Ordenar");
		pnlBotonera.add(btnInsertar);
		pnlBotonera.add(btnBorrar);
		pnlBotonera.add(btnOrdenar);
		pnlBotonera.setBackground( Color.black );
		add(pnlBotonera, BorderLayout.SOUTH);
		
		
	}
	
	public void setDatos(DataSetMunicipios datosMunis) {
		this.datosMunis = datosMunis;
		
		//Parte del JTree:
		raiz = new DefaultMutableTreeNode("Municipios");
		treeModel = new DefaultTreeModel(raiz);
		tree.setModel(treeModel);
		
		//Crear los nodos del tree:
		for (Municipio m: datosMunis.getListaMunicipios()) {
			DefaultMutableTreeNode nodoPadre = crearNodo(m.getNombre(), raiz); //Datos del nivel 2
			crearNodo(m.getProvincia(), nodoPadre); //Datos del nivel 3
		}
		
		//Parte del JTable:
		modeloTabla = new MiModeloTabla();
		tablaDatos.setModel( modeloTabla );
		
	       tree.addTreeSelectionListener(new TreeSelectionListener() {
	            @Override
	            public void valueChanged(TreeSelectionEvent e) {
	                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	                if (selectedNode != null && selectedNode.isLeaf()) {
	                    String provincia = (String) selectedNode.getUserObject();
	                    modeloTabla.setDatos(provincia);
	                }
	            }
	        });
		
	}
	
	//Método para crear los nodos del JTree:
	public DefaultMutableTreeNode crearNodo(String dato, DefaultMutableTreeNode nodoPadre) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);
		//nodoPadre.add(nodo);
		treeModel.insertNodeInto(nodo, nodoPadre, 0);
		return nodo;
	}
	
	
	private class MiModeloTabla extends AbstractTableModel {
		
		
		private String provincia;
		
	    public void setDatos(String provincia) {
	        this.provincia = provincia;
	        fireTableDataChanged();
	    }

		@Override
		public int getRowCount() {
			 return 0;
		}

		@Override
		public int getColumnCount() {
			return 6;
		}
		
		private final String[] NOM_COLUMNAS = {"Código","Nombre","Num Habitantes", "Barra Habitantes", "Provincia", "Autonomía"};
		@Override
		public String getColumnName(int columnIndex) {
			return NOM_COLUMNAS[columnIndex];
		}
		
		private final Object[] CLASE_COLUMNAS = {Integer.class, String.class, Integer.class, JProgressBar.class, String.class, String.class};
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return (Class<?>) CLASE_COLUMNAS[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
		//private String provincia;
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
	        if (provincia != null) {
	        	for (Municipio m: datosMunis.getListaMunicipios()) {
					if (m.getProvincia().equals(provincia)) {
					switch (columnIndex) {
					case 0:
						return datosMunis.getListaMunicipios().get(rowIndex).getCodigo();
					case 1:
						return datosMunis.getListaMunicipios().get(rowIndex).getNombre();
					case 2:
						return datosMunis.getListaMunicipios().get(rowIndex).getHabitantes();
					case 3:
						return datosMunis.getListaMunicipios().get(rowIndex).getProvincia();
					case 4:
						return datosMunis.getListaMunicipios().get(rowIndex).getAutonomia();
					default:
						return null;
					}
				}
	        	}
	        }
	        return null;
			
//			tree.addMouseListener(new MouseAdapter() {
//				
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
//					if (tp != null) {
//						DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tp.getLastPathComponent();
//						if (nodo.isLeaf()) { //Si es una hoja significa que no tiene hijos. En este caso una provincia
//							provincia = (String) nodo.getUserObject().toString(); //Pillo el dato (el este caso la provincia, que es un String)
//						}
//					}
//				}
//				
//			});
//			
//			for (Municipio m: datosMunis.getListaMunicipios()) {
//				if (m.getProvincia().equals(provincia)) {
//					switch (columnIndex) {
//					case 0:
//						return datosMunis.getListaMunicipios().get(rowIndex).getCodigo();
//					case 1:
//						return datosMunis.getListaMunicipios().get(rowIndex).getNombre();
//					case 2:
//						return datosMunis.getListaMunicipios().get(rowIndex).getHabitantes();
//					case 3:
//						return datosMunis.getListaMunicipios().get(rowIndex).getProvincia();
//					case 4:
//						return datosMunis.getListaMunicipios().get(rowIndex).getAutonomia();
//					default:
//						return null;
//					}
//				}
//			}
			
			//return null;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			
		}
		
		
	}
	
}
