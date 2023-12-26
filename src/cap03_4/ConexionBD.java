package cap03_4;

import java.sql.*;

public class ConexionBD {
	
	static {
		try {
			Class.forName("org.sqlite.JDBC"); //Preparar a java para que se conecte con la BD
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Connection conectarConBD() {
		
		Connection conn = null;
		
		//Conectarlo, 'conseguir una conexión'
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:cartas.db"); //La URL es dónde está la BD y ya tengo la conexión
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
