package cap03_04;

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
			conn = DriverManager.getConnection("jdbc:sqlite:cartas.db");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}

