package cap06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cap06.DataSetMunicipios;

public class GestionDatos extends JFrame{
	
	private DataSetMunicipios datosMunis;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode raiz; //Lo creo como argumento porque lo necesitaré
	private JTable tablaDatos;
	private MiModeloTabla modeloTabla;
	private panelGrafico pnlVisualizacion;
	
	private ArrayList<Municipio> municipiosAñadidos = new ArrayList<Municipio>();
	
	//Paso 7 - atributos
	//private Municipio municipioSeleccionado = null;
	private int poblacionSeleccionada = 0; 
	private int vecesSelecionado = 1;
	
	//Paso 10 - atributos
	private boolean ordenar = false; //Esto mirarlo que creo que ya no lo uso
	private HashMap<String, ArrayList<Municipio>> arrayPorProvincia = new HashMap<String, ArrayList<Municipio>>();
	private int turnoOrden = 1;
	
	//Paso 11 - atributos
	private HashMap<String, Integer> mapaPoblacionPorProvincia = new HashMap<String, Integer>();
	
	//Paso 12 - atributos
	private int poblacionEstado;
	private static final int COL_PROVINCIA = 4;
	private boolean activa = false; //Para que el panel esté 'vacío' hasta que selecciones la primera provincia a ver en el gráfico
	
	public GestionDatos(JFrame ventOrigen) {
		
		setTitle("VentanaComponentes");
		setSize( 700, 700 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Componente 1:
		JLabel lblMensaje = new JLabel("Práctica tema 6 SWING");
		add(lblMensaje, BorderLayout.NORTH);
		
		//Componente 2:
		tree = new JTree();
		add(new JScrollPane(tree), BorderLayout.WEST);
		
		//Componente 3:
		tablaDatos = new JTable();
		add(new JScrollPane(tablaDatos), BorderLayout.CENTER);
		
		//Componente 4:
		pnlVisualizacion = new panelGrafico(0);
		pnlVisualizacion.add(new JLabel("Gráfico población: Rojo - Estado, Azul - Provincia"),BorderLayout.SOUTH);
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
		
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ventOrigen.setVisible( false );
			}
			@Override
			public void windowClosed(WindowEvent e) {
				ventOrigen.setVisible( true );
			}
		});
		
		//Paso 8:
		btnInsertar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaDatos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					insertarMunicipio(municipiosAñadidos.get(filaSeleccionada));
				}
				tablaDatos.repaint();
			}
		});
		
		//Paso 9:
		btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaDatos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					modeloTabla.removeRow(filaSeleccionada);
				}
				tablaDatos.repaint();
			}
		});
		
		//Paso 10:
		btnOrdenar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				for (int i = 0; i < modeloTabla.getRowCount(); i++) {
//					modeloTabla.removeRow(i);
//					tablaDatos.repaint();
//				}
				
				turnoOrden ++;
				
				for (String s: arrayPorProvincia.keySet()) {
					if (turnoOrden % 2 == 0) {
						Collections.sort(arrayPorProvincia.get(s), new Comparator<Municipio>() {

							@Override
							public int compare(Municipio o1, Municipio o2) {
								return Integer.compare(o2.getHabitantes(), o1.getHabitantes());
							}
							
						});
					} else {
						Collections.sort(arrayPorProvincia.get(s), new Comparator<Municipio>() {

							@Override
							public int compare(Municipio o1, Municipio o2) {
								return o1.getNombre().compareTo(o2.getNombre());
							}
							
						});
					}
				}
				
				modeloTabla.setRowCount(0); //Primero, quito todas las líneas
				
				//Añado todos los municipios ordenados (el criterio de ordenacion se gestiona arriba)
				for (String s: arrayPorProvincia.keySet()) {
			        for (Municipio municipio : arrayPorProvincia.get(s)) {
			            modeloTabla.addRow(new Object[]{
			                municipio.getCodigo(),
			                municipio.getNombre(),
			                municipio.getHabitantes(),
			                municipio.getHabitantes(),
			                municipio.getProvincia(),
			                municipio.getAutonomia()
			            });
			        }
				}

		        // Actualizar la vista de la tabla
		        tablaDatos.repaint();
		        
		        //Para probar
//		        for (String s: arrayPorProvincia.keySet()) {
//		        	System.out.println("Array: " + arrayPorProvincia.get(s));
//		        }
			}
		});
		
	}
	
	public void setDatos(DataSetMunicipios datosMunis) {
		
		this.datosMunis = datosMunis;
		
		//Dato de la población del estado para paso 12:
		for (Municipio m: datosMunis.getListaMunicipios()) {
			poblacionEstado += m.getHabitantes();
		}
		
		//Cargar mapa para el paso 11:
		for (Municipio m: datosMunis.getListaMunicipios()) {
			if (!mapaPoblacionPorProvincia.containsKey(m.getProvincia())) {
				mapaPoblacionPorProvincia.put(m.getProvincia(), m.getHabitantes());
			} else {
				int habitantesComulativos = mapaPoblacionPorProvincia.get(m.getProvincia());
				mapaPoblacionPorProvincia.put(m.getProvincia(),m.getHabitantes()+habitantesComulativos);
			}
		}
		System.out.println(mapaPoblacionPorProvincia);
		//this.datosMunis = datosMunis; (No sé porque estaba dos veces)
		
		//Parte del JTree:
		raiz = new DefaultMutableTreeNode("Municipios");
		treeModel = new DefaultTreeModel(raiz);
		tree.setModel(treeModel);
		
		
		//Paso 11:
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			private JProgressBar pb = new JProgressBar(0,5000000);

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
					boolean leaf, int row, boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				if (leaf) {
					DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value; //value es el nodo
					String provincia = (String) nodo.getUserObject();
					for (String provinviaEnMapa: mapaPoblacionPorProvincia.keySet()) {
						if (provincia.equals(provinviaEnMapa)) {
							pb.setValue(mapaPoblacionPorProvincia.get(provinviaEnMapa));
		                    JPanel panel = new JPanel();
		                    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		                    panel.add(c);
		                    
		                    panel.add(pb);
		                    
		                    return panel;
						}
					}
				}
				return c;
			}
			
			
		});
		
		
		//Crear los nodos del tree:
		for (Municipio m: datosMunis.getListaMunicipios()) {
			DefaultMutableTreeNode nodoPadre = crearNodo(m.getNombre(), raiz); //Datos del nivel 2
			crearNodo(m.getProvincia(), nodoPadre); //Datos del nivel 3
		}
		
		modeloTabla = new MiModeloTabla();
		tablaDatos.setModel( modeloTabla );
		
		//Ajustes de la tabla:
		tablaDatos.getTableHeader().setReorderingAllowed(false); //Prohibe el movimineto de columnas del usuario
		for (int i = 0; i < 6; i++) { 
			tablaDatos.getColumnModel().getColumn(i).setMinWidth(tablaDatos.getColumnModel().getColumn(i).getWidth()+50);
		}
		
		
		tablaDatos.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
			
			//Para el cálculo progresivo del valor
		    private static Color calculateColor(int value, int minValue, int maxValue) {
		        float range = (float) (maxValue - minValue);
		        float percentage = (value - minValue) / range;
		        int red = (int) (255 * percentage);
		        int green = 255 - red;
		        return new Color(red, green, 0);
		    }
			
			private JProgressBar poblacion = new JProgressBar(50000, 5000000) {
				public void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setColor(calculateColor(getValue(), 50000, 5000000));
					System.out.println(getPercentComplete());
				    int width = (int) (getWidth() * getPercentComplete()); //Devuelve cuan llena está
				    g.fillRect(0, 0, width, getHeight());
				}
			};
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				if (column == 3) {
					poblacion.setValue((Integer)value);
					return poblacion;
				}
//				else {
//					c.setBackground( new Color(0, 0, 0) );
//				}
				return c;
			}
			
		});
		
		//Paso 7
			tablaDatos.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					c.setBackground( Color.white );
//					int poblacion = municipioSeleccionado.getHabitantes();
//					
					if (poblacionSeleccionada != 0) {
						if (column == 1) {
							if (municipiosAñadidos.get(row).getHabitantes() > poblacionSeleccionada ) {
								c.setBackground( Color.RED );
							}
							else {
								c.setBackground( Color.GREEN );
							}
						}
					}
					
//					if (column == 1) {
//						c.setBackground(Color.red);
//					}
					return c;
				}
				
			});
		//Paso 7
		tablaDatos.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				vecesSelecionado ++;
				if (vecesSelecionado % 2 == 0) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						int filaEnTabla = tablaDatos.rowAtPoint(e.getPoint());
						if (filaEnTabla >= 0) {
							//municipioSeleccionado = municipiosAñadidos.get(filaEnTabla);
							//System.out.println(municipioSeleccionado);
							poblacionSeleccionada = municipiosAñadidos.get(filaEnTabla).getHabitantes();
						}
						else {
							//municipioSeleccionado = null;
							poblacionSeleccionada = 0;
						}
						//activa = true;
						tablaDatos.repaint();
						//System.out.println(activa);
					}
				}
				else {
					poblacionSeleccionada = 0;
					tablaDatos.repaint();
				}
				
				//Paso 12:
				int filaEnTabla = tablaDatos.rowAtPoint( e.getPoint() );
				int columnaEnTabla = tablaDatos.columnAtPoint( e.getPoint() );
				if (columnaEnTabla == COL_PROVINCIA & filaEnTabla >= 0) {
					String provinciaSel = municipiosAñadidos.get(filaEnTabla).getProvincia();
					int valorDePoblacion = mapaPoblacionPorProvincia.get(provinciaSel);
					pnlVisualizacion.setValorProvincia(valorDePoblacion);
					pnlVisualizacion.setProvincia(provinciaSel);
					activa = true; //Hasta que seleccione una provincia, el panel de Visualizacion tiene que estar vacío.
					pnlVisualizacion.repaint();
				} else {
					pnlVisualizacion.setValorProvincia(0);
				}
			}
		});
		
		tree.addMouseListener(new MouseAdapter() {
		
		@Override 
		public void mouseClicked(MouseEvent e) {
			TreePath tp = tree.getPathForLocation(e.getX(), e.getY()); //Dame la ruta para la pasición de ratón
			if (tp != null) {
				DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) tp.getLastPathComponent(); //Pillo el nodo
				if (nodoSeleccionado.isLeaf()) { //Si es una hoja significa que no tiene hijos. En este caso una provincia
					cargarPorProvincia((String) nodoSeleccionado.getUserObject());
					poblacionSeleccionada = 0;
				}
				tablaDatos.repaint();
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
	
	
	public void cargarPorProvincia(String provincia) {
		for (Municipio m: datosMunis.getListaMunicipios()) {
			if (m.getProvincia().equals(provincia)) {
				//modeloTabla.addRow(new Object[] {m.getCodigo(), m.getNombre(),(int) m.getHabitantes(), (int) m.getHabitantes(), m.getProvincia(), m.getAutonomia()});
				municipiosAñadidos.add(m);
				System.out.println(municipiosAñadidos);
				//Esto es para el paso 8:
				if (!arrayPorProvincia.containsKey(provincia)) {
					ArrayList<Municipio> arr = new ArrayList<Municipio>();
					arr.add(m);
					arrayPorProvincia.put(provincia, arr);
				} else {
					ArrayList<Municipio> arr1 = arrayPorProvincia.get(provincia);
					arr1.add(m);
					arrayPorProvincia.put(provincia, arr1);
				}
			}
		}
		//Ordeno los municipios de la Provincia seleccionada alfabeticamente
		Collections.sort(arrayPorProvincia.get(provincia), new Comparator<Municipio>() {

			@Override
			public int compare(Municipio o1, Municipio o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
		});
		// Voy añadiendo cada uno de ellos
		for (Municipio m: arrayPorProvincia.get(provincia)) {
            modeloTabla.addRow(new Object[]{
	                m.getCodigo(),
	                m.getNombre(),
	                m.getHabitantes(),
	                m.getHabitantes(),
	                m.getProvincia(),
	                m.getAutonomia()
	            });
		}
		pnlVisualizacion.setMunis(municipiosAñadidos);
	}
	
	public void insertarMunicipio(Municipio municipio) {
		if (municipio != null) {
			modeloTabla.addRow(new Object[] {0, "", 50000, 50000,  municipio.getProvincia(), municipio.getAutonomia()});
			municipiosAñadidos.add(municipio);
			System.out.println(municipio);
		}
	}

	public JTree getTree() {
		return this.tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}
	
	
	//Creo una clase interna no anónima para el modelo de la tabla:
	private class MiModeloTabla extends DefaultTableModel {
		
		@Override
		public int getRowCount() {
			return super.getRowCount();
		}

		@Override
		public int getColumnCount() {
			return 6;
		}
		
		private final Class<?>[] CLASES_COLS = { Integer.class, String.class, Integer.class, Integer.class, String.class, String.class};
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return CLASES_COLS[columnIndex];
		}
		
		private final String[] cabeceras = { "Código", "Nombre", "Habitantes", "Población","Provincia", "Autonomía",};
		@Override
		public String getColumnName(int column) {
			return cabeceras[column];
		}
		
		//Paso 6
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 0 || column == 4 || column == 5) { //La columna del código tampoco es editable
				return false;
			}
			return super.isCellEditable(row, column);
		}

		@Override
		public Object getValueAt(int row, int column) {
			return super.getValueAt(row, column);
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			super.setValueAt(aValue, row, column);
		}
		
	}
	
	
	//Parte 12:
	private class panelGrafico extends JPanel {
		
		private int valorProvincia;
		private String provincia;
		private ArrayList<Municipio> munis = new ArrayList<Municipio>();
		
		public panelGrafico(int vlaorProvincia) { //El valor del estado es siempore el mismo
			this.valorProvincia = vlaorProvincia;
		}
		
		public int getValorProvincia() {
			return valorProvincia;
		}
		
		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}

		public void setValorProvincia(int valorProvincia) {
			this.valorProvincia = valorProvincia;
		}
		
		public ArrayList<Municipio> getMunis() {
			return munis;
		}

		public void setMunis(ArrayList<Municipio> munis) {
			this.munis = munis;
		}

		public void paintComponent(Graphics g) {
			
			//if (activa) {
			/*
			 * Esto (if(activa)) lo había usado para que el gráfico se empezara a
			 * a ver una vez seleccionada una provincia pero el enunciado indica que 
			 * la escala del estado sea siempre visible. Sin esta condición, se ve la
			 *  escala del estado pero no la de la provincia porque ne se ha seleccionado
			 * ninguna todavía.
			 */
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
		        
		        int barWidth = 50;
		        double Provincia = valorProvincia/100000*3; // Altura de la primera barra
		        double Estado = poblacionEstado/100000*3; // Altura de la segunda barra

		        int x1 = 50; // Posición x de la primera barra
		        int x2 = 150; // Posición x de la segunda barra
		        int y = 650;

		        g2d.setColor(Color.blue);
		        g2d.fill(new Rectangle2D.Double(x1, y - Provincia, barWidth, Provincia));

		        g2d.setColor(Color.red);
		        g2d.fill(new Rectangle2D.Double(x2, y - Estado, barWidth, Estado));
		        
		        g2d.setColor(Color.black); //Para la línea
		        g2d.drawLine(x1-5, y, x2+barWidth, y);
		        g2d.drawLine(x1-5, 650, x1, x1);
		        
		        //Dibujar ejes X e y:
		        g2d.drawString("X (Provincia/Estado)", x2+barWidth-15, y+15);
		        g2d.drawString("Y (nº Habitantes)", x1-5, 45);
		        //g2d.drawRect(155, 90, 40, 20); 
		        
		        //Dibujar referencias
		        g2d.drawString("20M", x1 - 23, 80);
		        g2d.drawString("15M", x1 - 23, 222); 
		        g2d.drawString("10M", x1 - 23, 365); 
		        g2d.drawString("5M", x1 - 23, 507); 
		        
		        //Dibujar líneas horizontales indicando los habitantes de cada municipio en una provincia
		        g2d.setColor( Color.green );
		        for (Municipio m: munis) {
		        	int acomulado = 0;
		        	if (m.getProvincia().equals(provincia)) {
		        		g2d.drawLine(x1, 650-m.getHabitantes()/100000*3-acomulado, x1+barWidth, 650-m.getHabitantes()/100000*3-acomulado);
		        		acomulado += m.getHabitantes()/100000*3;
		        	}
		        }
			//}
	        
		}
	}

}
