package cap05;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class VentanaGeneral extends JFrame{

private final static Dimension TAMAYO_VENT = new Dimension(480, 640);

    protected JTextArea textArea; //Protected para que desde la otra clase pueda tener acceso al JTextArea.
    private JProgressBar pb;
    private ArrayList<UsuarioTwitter> amigosPorUsuario;
//    private ModeloTabla modeloTabla;
//    private JTable tablaUsuarios;

    public JTextArea getTextArea() {
return textArea;
}

public void setTextArea(JTextArea textArea) {
this.textArea = textArea;
}
public VentanaGeneral() {
        // Configuración básica de la ventana
        setTitle("Ventana Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Inicialización de componentes
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton  processButton = new JButton("Procesar Archivo CSV");
       
        pb = new JProgressBar();
        //tablaUsuarios = new JTable();

        // Configuración del diseño
        setLayout(new BorderLayout());
        add(pb, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(processButton, BorderLayout.SOUTH);
        //add(new JScrollPane(tablaUsuarios), BorderLayout.WEST);

        // Manejador de eventos para el botón
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               processCSVFile();
               //CSV.actualizarAmigosEnSistema();
               ArrayList<ArrayList<Object>> datos = CSV.getDatos();
               Thread hilo = new Thread() {
              @Override
            public void run() {
                       int num = 0;
                       int valorPb = 0;
                       for (ArrayList<Object> arr: datos) {
                      num ++;
                      System.out.println(num + " " + arr.toString());
                      textArea.append(arr.toString()+"\n");
                      textArea.repaint();
                      try {
Thread.sleep(50); //Reducir los milisegundos para cargar los datos más rápido.
} catch (InterruptedException e) {
e.printStackTrace();
}
                      if (num == 2001) { //Cargo 2001 un datos ya que no me deja cargar muchos mas.
                      this.stop(); //Para que no cargue todos los datos ya que son demasiados
                      }
                      if (num % 20 == 0) {
                      valorPb ++;
                          pb.setValue(valorPb);
                          pb.repaint();
                      }
                       }
            }
               };
               hilo.start();
            }
        });
    }

// public void setDatos(TreeSet<UsuarioTwitter> datos) {
// this.amigosPorUsuario = new ArrayList<>(datos);
// modeloTabla = new ModeloTabla();
// tablaUsuarios.setModel(modeloTabla);
// }

    private void processCSVFile() {
        // Solicitar la ruta del archivo CSV utilizando JOptionPane
        String filePath = JOptionPane.showInputDialog(this, "Ingrese la ruta del archivo CSV:");
        JOptionPane.showMessageDialog(null, "Espera unos segundos (30-40 aprox) para poder ver los datos en la ventana", "Información", JOptionPane.INFORMATION_MESSAGE);
        if (filePath != null && !filePath.isEmpty()) {
            //Procesar el archivo CSV
            try {
                File file = new File(filePath);
                //CSV.processCSV(file, textArea);
                CSV.processCSV(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar el archivo CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ruta de archivo no válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
//    public void modificarAmigos() {
//     for (UsuarioTwitter u: amigosPorUsuario) {
//     if (u.getAmigosEnSistema() <= 10) {
//     amigosPorUsuario.remove(u);
//     }
//     }
//    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaGeneral().setVisible(true);
            }
        });
    }
    /**
     * Esta parte de la JTable está comentada ya que no he completado estos últimos apartados sobre la JTable
     */
//    private class ModeloTabla implements TableModel {
//    
//     private ArrayList<UsuarioTwitter> usuarios;
//    
//     public ModeloTabla(TreeSet<UsuarioTwitter> datos) {
//     this.usuarios = new ArrayList<>(datos);
//     }
//
// @Override
// public int getRowCount() {
// return amigosPorUsuario.size();
// }
//
// @Override
// public int getColumnCount() {
// return 6;
// }
//
// private final String[] cabeceras = {"id", "screenName", "followersCount","friendsCount","lang","lasSeen"};
// @Override
// public String getColumnName(int columnIndex) {
// return cabeceras[columnIndex];
// }
//
// @Override
// public Class<?> getColumnClass(int columnIndex) {
// return Object.class;
// }
//
// @Override
// public boolean isCellEditable(int rowIndex, int columnIndex) {
// return false;
// }
//
// @Override
// public Object getValueAt(int rowIndex, int columnIndex) {
// switch (columnIndex) {
// case 0:
// return amigosPorUsuario.get(rowIndex).getId();
// case 1:
// return amigosPorUsuario.get(rowIndex).getScreenName();
// case 2:
// return amigosPorUsuario.get(rowIndex).getFollowersCount();
// case 3:
// return amigosPorUsuario.get(rowIndex).getFriendsCount();
// case 4:
// return amigosPorUsuario.get(rowIndex).getLang();
// case 5:
// return amigosPorUsuario.get(rowIndex).getLastSeen();
// default:
// return null;
// }
// }
//
// @Override
// public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void addTableModelListener(TableModelListener l) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void removeTableModelListener(TableModelListener l) {
// // TODO Auto-generated method stub
//
// }
//    
//    }

}