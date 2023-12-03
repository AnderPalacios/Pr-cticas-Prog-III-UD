package cap01;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Cliente extends JFrame{
	
	private static Logger logger = Logger.getLogger(Cliente.class.getName());
	
	private JPanel pnlPrincipal;
	private JTextField txtEnvio;
	private JButton btnEnvio;
	private JLabel lblFoto;
	private JLabel lblTexto;
	private JComboBox<String> comboAliados;
	
	public static void main(String[] args) {
		
		Cliente c = new Cliente();
		c.setVisible(true );
		
	}
	
	public JPanel getPnlPrincipal() {
		return pnlPrincipal;
	}

	public void setPnlPrincipal(JPanel pnlPrincipal) {
		this.pnlPrincipal = pnlPrincipal;
	}

	public JTextField getTxtEnvio() {
		return txtEnvio;
	}

	public void setTxtEnvio(JTextField txtEnvio) {
		this.txtEnvio = txtEnvio;
	}

	public JButton getBtnEnvio() {
		return btnEnvio;
	}

	public void setBtnEnvio(JButton btnEnvio) {
		this.btnEnvio = btnEnvio;
	}

	public JLabel getLblFoto() {
		return lblFoto;
	}

	public void setLblFoto(JLabel lblFoto) {
		this.lblFoto = lblFoto;
	}

	public JLabel getLblTexto() {
		return lblTexto;
	}

	public void setLblTexto(JLabel lblTexto) {
		this.lblTexto = lblTexto;
	}

	public JComboBox<String> getComboAliados() {
		return comboAliados;
	}

	public void setComboAliados(JComboBox<String> comboAliados) {
		this.comboAliados = comboAliados;
	}

	public Cliente() {
		
		try {
			Handler h = new FileHandler("errores.log");
			logger.addHandler(h);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error al configurar el logger de archivo", e);
		}
		
		setSize(250, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(675, 550);
		setResizable(false);
		
		pnlPrincipal = new JPanel();
		
		lblFoto = new JLabel();
		
		comboAliados = new JComboBox<String>();
		comboAliados.addItem("Italia");
		comboAliados.addItem("Japon");
		
		comboAliados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sel = comboAliados.getSelectedItem().toString();
				if (sel.equals("Italia")) {
					corregirImagen(lblFoto, new ImageIcon(getClass().getResource("banderaItalia.jpg")));
				}
				if (sel.equals("Japon")) {
					corregirImagen(lblFoto, new ImageIcon(getClass().getResource("banderaJapon.png")));
				}
				
			}
		});
		pnlPrincipal.add(comboAliados);
		
		lblTexto = new JLabel("ALIADO - ALEMANIA");
		pnlPrincipal.add(lblTexto);
		
		pnlPrincipal.add(corregirImagen(lblFoto, new ImageIcon(getClass().getResource("banderaItalia.jpg"))));
		
		txtEnvio = new JTextField(20);
		pnlPrincipal.add(txtEnvio);
		
		btnEnvio = new JButton("Enviar");
		btnEnvio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Creo el Socket en el evento para que se cree cuando pulse el botón
				try {
					//Utilizando mi IP
					Socket misocket = new Socket("192.168.1.101", 7777); //Segundo argumento es el puerto
					
					Enigma mensajeEnigma = new Enigma();
					mensajeEnigma.setDestino(comboAliados.getSelectedItem().toString());
					mensajeEnigma.setMensaje(mensajeEnigma.encriptar(txtEnvio.getText()));
					
					// Crear un flujo de datos de salida y decirle por dónde van a circular
					ObjectOutputStream datosEnvio = new ObjectOutputStream(misocket.getOutputStream());
					
					datosEnvio.writeObject(mensajeEnigma); //Le digo que va a ir por el fujo de datos 
					misocket.close();
					
					txtEnvio.setText("");
					
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					logger.log(Level.SEVERE, "Error al enviar mensaje", e2);
				}
				
			}
		});
		pnlPrincipal.add(btnEnvio);
		
		getContentPane().add(pnlPrincipal);
		
	}
	
	public JLabel corregirImagen(JLabel lblDado, ImageIcon imagenOriginal) {
		Image originalImage = imagenOriginal.getImage();
		int newHeight = 2*getHeight()/4;
		int newWidth = (int) ((double) originalImage.getWidth(null) / originalImage.getHeight(null) * newHeight);
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		lblDado.setIcon(scaledIcon);
		return lblDado;
	}
	
}
