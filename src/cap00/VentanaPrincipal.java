package cap00;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class VentanaPrincipal extends JFrame {
	
	public static void main(String[] args) {
		VentanaPrincipal ventanaEcosistema = new VentanaPrincipal();
		ventanaEcosistema.setVisible(true);
	}
	
	protected Random r = new Random();
	protected static int nEcosistema = 1;
	
	protected JPanel pnlPrincipal;
	protected JPanel pnlBotones;
	protected JComboBox<String> comboElementos;
	protected JButton btnVida;
	protected JToggleButton btnMover;
	protected JToggleButton btnCrear;
	
	private int panelX, panelY;
	
	//Para actualizar la población/cantidad al usar el método evolucionar
	protected JLabel lblAbejas;
	protected JLabel lblFlores;
	
	protected HashMap<ElementoEcosistema, JLabel> mapaParaEvolucionar = new HashMap<ElementoEcosistema, JLabel>();
	//Hacer atributos de JPanel para que sean = getPanel(); meterlos en otro mapa para que "vayan de la mano";
	protected JPanel pnlAbejas;
	protected JPanel pnlFlores;
	protected JPanel pnlAgua;
	protected HashMap<ElementoEcosistema, JPanel> mapaMoverPanel = new HashMap<ElementoEcosistema, JPanel>();
	
	
	public VentanaPrincipal() {
		
		this.setTitle("ECOSISTEMA");
		this.setBounds(600,200,700,700);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlPrincipal = new JPanel();
		pnlPrincipal.setLayout(null);
		pnlBotones = new JPanel();
		
		btnMover = new JToggleButton("Mover");
		btnCrear = new JToggleButton("Crear");
		comboElementos = new JComboBox<String>();
		comboElementos.addItem("Abejas");
		comboElementos.addItem("Flores");
		comboElementos.addItem("Agua");
		btnVida = new JButton("Vida");
		
		pnlBotones.add(btnMover);
		pnlBotones.add(btnCrear);
		pnlBotones.add(comboElementos);
		pnlBotones.add(btnVida);
		
		this.add(pnlBotones,BorderLayout.SOUTH);
		
		Ecosistema ecosistema = Ecosistema.getMundo();
		
		btnVida.addActionListener(new ActionListener() {
			protected int dia = 0; //Para ir evolucionando los elementos del ecosistema un día
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnVida.getText().equals("Vida")) {
					btnVida.setText("Parar");
					//Lanzar el hilo
					Thread hilo = new Thread(new Runnable() {
						
						@Override
						public void run() {
							while(btnVida.getText().equals("Parar")) { //Se destruye el hilo al cambiarlo
								dia ++;
								for (ElementoEcosistema ee: mapaParaEvolucionar.keySet()) {
									if (ee instanceof ColoniaAbejas) {
										ColoniaAbejas abeja = (ColoniaAbejas) ee;
										abeja.evoluciona(dia);
										System.out.println(abeja);
										mapaParaEvolucionar.get(abeja).setText(abeja.getPoblacion()+"");
									}
									else if (ee instanceof PlantacionFlores) {
										PlantacionFlores flor = (PlantacionFlores) ee;
										flor.evoluciona(dia);
										System.out.println(flor);
										mapaParaEvolucionar.get(flor).setText(flor.getCantidad()+"");
									}
								}
								//Parar el hilo durante un segundo para que se pueda ver la evolución en la ventana
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
						}
					});
					hilo.start();
				}
				else {
					btnVida.setText("Vida");
				}
			}
		});
		
		
		btnMover.addActionListener(new ActionListener() {
			HashMap<ElementoEcosistema, JPanel> mapaElemntosRecorridos = new HashMap<ElementoEcosistema, JPanel>();
			//Este mapa sirve para no volver a recorrer los elementos del ecosistema ya recorridos anteriormente
			@Override
			public void actionPerformed(ActionEvent e) {
				for (ElementoEcosistema ee: mapaMoverPanel.keySet()) {
					if (!mapaElemntosRecorridos.containsKey(ee)) { //Signifca que no he recorrido ese elemen to antes
						mapaMoverPanel.get(ee).addMouseListener(new MouseAdapter() {

							@Override
							public void mousePressed(MouseEvent e) {
								panelX = e.getX();
				                panelY = e.getY();
								
							}

						});
						mapaMoverPanel.get(ee).addMouseMotionListener(new MouseAdapter() {
							
							@Override
							public void mouseDragged(MouseEvent e) {
				                int newX = e.getX() + mapaMoverPanel.get(ee).getX() - panelX;
				                int newY = e.getY() + mapaMoverPanel.get(ee).getY() - panelY;
				                mapaMoverPanel.get(ee).setLocation(newX, newY);
								//Actualizo la posición del JPanel correspondiente después de arrastralo por la ventana.
							}

						});
					}
					mapaElemntosRecorridos.put(ee, mapaMoverPanel.get(ee));
				}
				
			}
		});
		
		
		
		pnlPrincipal.addMouseListener(new MouseAdapter() {
			private Point puntoPresionado;
			private Point puntoSoltado;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				puntoSoltado = e.getPoint();
				if(puntoPresionado != puntoSoltado) {
					if (btnCrear.isSelected()) {
						if (comboElementos.getSelectedItem().equals("Abejas")) {
							ColoniaAbejas Abejas = new ColoniaAbejas("Colonia 1", new Point(20,30), new Dimension(5,5), (long)r.nextInt(90));
							ecosistema.getElementos().add(Abejas);
							JPanel pnl = Abejas.getPanel();
							pnl.setBackground(Color.WHITE);
							JLabel lblNombre = new JLabel("Colonia "+nEcosistema);
							nEcosistema ++;
							lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
							lblAbejas = new JLabel(Abejas.getPoblacion()+"");
							lblAbejas.setHorizontalAlignment(SwingConstants.CENTER);
							mapaParaEvolucionar.put(Abejas, lblAbejas);
							JLabel lblTipo = new JLabel("Abejas");
							lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
							pnl.add(lblNombre);
							pnl.add(lblAbejas);
							pnl.add(lblTipo);
							pnl.setBounds((int)puntoPresionado.x, (int)puntoPresionado.y,(int) (puntoSoltado.x - puntoPresionado.x), (int) (puntoSoltado.y - puntoPresionado.y));
							pnlAbejas = pnl;
							mapaMoverPanel.put(Abejas, pnl);
							pnlPrincipal.add(pnlAbejas);
							
						}
						else if (comboElementos.getSelectedItem().equals("Flores")) {
							PlantacionFlores flores = new PlantacionFlores("Rosal", new Point(40,40), new Dimension(13,10), (long)r.nextInt(50));
							ecosistema.getElementos().add(flores);
							JPanel pnl = flores.getPanel();
							pnl.setBackground(Color.GREEN);
							JLabel lblNombre = new JLabel("Bosque " + nEcosistema);
							nEcosistema ++;
							lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
							lblFlores = new JLabel(flores.getCantidad()+"");
							lblFlores.setHorizontalAlignment(SwingConstants.CENTER);
							mapaParaEvolucionar.put(flores, lblFlores);
							JLabel lblTipo = new JLabel("Abejas");
							lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
							pnl.add(lblNombre);
							pnl.add(lblFlores);
							pnl.add(lblTipo);
							pnl.setBounds((int)puntoPresionado.x, (int)puntoPresionado.y,(int) (puntoSoltado.x - puntoPresionado.x), (int) (puntoSoltado.y - puntoPresionado.y));
							pnlFlores = pnl;
							mapaMoverPanel.put(flores, pnlFlores);
							pnlPrincipal.add(pnlFlores);
						}
						else {
							Agua agua = new Agua("Lago Ontario",new Point(100,200), new Dimension(30,40), (long)r.nextInt(150));
							ecosistema.getElementos().add(agua);
							JPanel pnl = agua.getPanel();
							pnl.setBackground(Color.BLUE);
							JLabel lblNombre = new JLabel("Lago "+nEcosistema);
							nEcosistema ++;
							JLabel lblPoblacion = new JLabel(agua.getCantidad()+"");
							JLabel lblTipo = new JLabel("Agua");
							lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
							lblPoblacion.setHorizontalAlignment(SwingConstants.CENTER);
							lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
							pnl.add(lblNombre);
							pnl.add(lblPoblacion);
							pnl.add(lblTipo);
							pnl.setBounds((int)puntoPresionado.x, (int)puntoPresionado.y,(int) (puntoSoltado.x - puntoPresionado.x), (int) (puntoSoltado.y - puntoPresionado.y));
							pnlAgua = pnl;
							mapaMoverPanel.put(agua, pnlAgua);
							pnlPrincipal.add(pnl);
							
						}
						pnlPrincipal.revalidate();
						pnlPrincipal.repaint();
					}
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				puntoPresionado = e.getPoint();
				
			}

		});
		
		getContentPane().add(pnlPrincipal);
		//Es lo mismo que: this.add(pnlPrincipal);
	}

}
