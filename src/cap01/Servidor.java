package cap01;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Servidor extends JFrame {
	
	public static void main(String[] args) {
		
		Servidor s = new Servidor();
		s.setVisible( true );
		
	}

	private JTextArea txtaTexto;
	
	public Servidor() {
		
		setSize(250, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(300, 100);
		setResizable(false);
		
		JPanel pnl = new JPanel();
		pnl.setLayout(new BorderLayout());
		
		txtaTexto = new JTextArea();
		pnl.add(new JScrollPane(txtaTexto), BorderLayout.CENTER);
		
		Thread hilo = new Thread() {

			@Override
			public void run() {
				
				try {
					ServerSocket servidor = new ServerSocket(7777);
					
					String destino;
					String mensaje;
					Enigma mensaje_encriptado;
					
					while (true) {
						Socket misocket = servidor.accept(); //Acepta las conexiones que vengan del 'exterior'
						
						//Crear el flujo de datos de entrada
						ObjectInputStream datos_recibidos = new ObjectInputStream(misocket.getInputStream());
						try {
							mensaje_encriptado = (Enigma) datos_recibidos.readObject();
							
							destino = mensaje_encriptado.getDestino();
							mensaje = mensaje_encriptado.getMensaje();
							
							txtaTexto.append("\n - " + destino + ": " + mensaje);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						
						misocket.close(); //Cierro la conexión
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
		hilo.start();
		
		getContentPane().add(pnl);
		
	}
}
