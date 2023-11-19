package cap05;

import java.io.File;

import javax.swing.JOptionPane;

public class GestionTwitter {

public static void main(String[] args) {

try {

//TODO Configurar el path y ruta del fichero a cargar

JOptionPane.showMessageDialog(null, "Espera unos segundos (30-40 aprox) para poder ver los datos por consola", "Información", JOptionPane.INFORMATION_MESSAGE);

String fileName = "data.csv";

CSV.processCSV( new File( fileName ) );

} catch (Exception e) {

e.printStackTrace();

}

CSV.comprobarAmigos();

CSV.actualizarAmigosEnSistema();

CSV.imprimirUsuariosOrdenados();

//new VentanaGeneral().setVisible(true);

}


}