package cap05;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

/** Clase para proceso básico de ficheros csv
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class CSV {

private static boolean LOG_CONSOLE_CSV = false;  // Log a consola de lo que se va leyendo en el CSV

//Paso 4:
private static HashMap<String, UsuarioTwitter> mapaPorId = new HashMap<String, UsuarioTwitter>();

//Paso 6:
private static HashMap<String, UsuarioTwitter> mapaPorNick = new HashMap<String, UsuarioTwitter>();

//Paso 7:
private static TreeSet<UsuarioTwitter> amigosPorUsuario = new TreeSet<UsuarioTwitter>();

//Paso 12:
private static VentanaGeneral vent = new VentanaGeneral();


//Swing
private static ArrayList<ArrayList<Object>> datos = new ArrayList<>();

public static ArrayList<ArrayList<Object>> getDatos() {
return datos;
}
public static void setDatos(ArrayList<ArrayList<Object>> datos) {
CSV.datos = datos;
}
public static TreeSet<UsuarioTwitter> getAmigosPorUsuario() {
return amigosPorUsuario;
}
public static void setAmigosPorUsuario(TreeSet<UsuarioTwitter> amigosPorUsuario) {
CSV.amigosPorUsuario = amigosPorUsuario;
}
//_______________________________________________________
/** Procesa un fichero csv
* @param file Fichero del csv
* @throws IOException
*/
public static void processCSV( File file )
throws IOException // Error de I/O
{
processCSV( file.toURI().toURL() );
}

/** Procesa un fichero csv
* @param urlCompleta URL del csv
* @throws IOException
* @throws UnknownHostException
* @throws FileNotFoundException
* @throws ConnectException
*/
public static void processCSV( URL url )
throws MalformedURLException,  // URL incorrecta
IOException, // Error al abrir conexión
UnknownHostException, // servidor web no existente
FileNotFoundException, // En algunos servidores, acceso a p�gina inexistente
ConnectException // Error de timeout
{
BufferedReader input = null;
InputStream inStream = null;
try {
   URLConnection connection = url.openConnection();
   connection.setDoInput(true);
   inStream = connection.getInputStream();
   input = new BufferedReader(new InputStreamReader( inStream, "UTF-8" ));  // Supone utf-8 en la codificación de texto
   String line = "";
   int numLine = 0;
   while ((line = input.readLine()) != null) { //&& numLine < 50
    numLine++;
    if (LOG_CONSOLE_CSV) System.out.println( numLine + "\t" + line );
    try {
    ArrayList<Object> l = processCSVLine( input, line, numLine );
    if (LOG_CONSOLE_CSV) System.out.println( "\t" + l.size() + "\t" + l );
    if (numLine==1) {
    procesaCabeceras( l );
    } else {
    if (!l.isEmpty())
    //vent.textArea.append(l.toString());
    procesaLineaDatos( l );
    datos.add(l);
    }
    } catch (StringIndexOutOfBoundsException e) {
    /* if (LOG_CONSOLE_CSV) */ System.err.println( "\tError: " + e.getMessage() );
    }
   }
   //Esto es otra manera de comprobar si hay repetidos en los mapas:
   System.out.println(numLine + " lineas con la cabecera" + " y claves en mapaPorId: " + mapaPorId.size() + " " + (numLine-1 == mapaPorId.size())); //numLine-1 por las cabeceras
   System.out.println(numLine + " lineas con la cabecera" + " y claves en mapaPorNick: " + mapaPorNick.size() + " " + (numLine-1 == mapaPorNick.size()));
} finally {
try {
inStream.close();
input.close();
} catch (Exception e2) {
}
}
}

/** Procesa una línea de entrada de csv
* @param input Stream de entrada ya abierto
* @param line La línea YA LEÍDA desde input
* @param numLine Número de línea ya leída
* @return Lista de objetos procesados en el csv. Si hay algún string sin acabar en la línea actual, lee más líneas del input hasta que se acaben los strings o el input
* @throws StringIndexOutOfBoundsException
*/
public static ArrayList<Object> processCSVLine( BufferedReader input, String line, int numLine ) throws StringIndexOutOfBoundsException {
ArrayList<Object> ret = new ArrayList<>();
ArrayList<Object> lista = null; // Para posibles listas internas
int posCar = 0;
boolean inString = false; // Marca de cuando se está leyendo un string
boolean lastString = false;  // Marca que el último leído era un string
boolean inList = false; // Marca de cuando se está leyendo una lista (entre corchetes, separada por comas)
boolean finString = false;
String stringActual = "";
char separador = 0;
while (line!=null && (posCar<line.length() || line.isEmpty() && posCar==0)) {
if (line.isEmpty() && posCar==0) {
if (!inString) return ret;  // Línea vacía
} else {
char car = line.charAt( posCar );
if (car=='"') {
if (inString) {
if (nextCar(line,posCar)=='"') {  // Doble "" es un "
posCar++;
stringActual += "\"";
} else {  // " de cierre
inString = false;
finString = true;
lastString = true;
}
} else {  // !inString
if (stringActual.isEmpty()) {  // " de apertura
inString = true;
} else {  // " después de valor - error
throw new StringIndexOutOfBoundsException( "\" after data in char " + posCar + " of line [" + line + "]" );
}
}
} else if (!inString && (car==' ' || car=='\t')) {  // separador fuera de string
// Nada que hacer
} else if (car==',' || car==';') {
if (inString) {  // separador dentro de string
stringActual += car;
} else {  // separador que separa valores
if (separador==0) { // Si no se había encontrado separador hasta ahora
separador = car;
if (inList)
lista.add( getDato( stringActual, lastString ) );
else if (lista!=null) {
ret.add( lista );
lista = null;
} else
ret.add( getDato( stringActual, lastString ) );
stringActual = "";
lastString = false;
finString = false;
} else { // Si se había encontrado, solo vale el mismo (, o ;)
if (separador==car) {  // Es un separador
if (inList)
lista.add( getDato( stringActual, lastString ) );
else if (lista!=null) {
ret.add( lista );
lista = null;
} else
ret.add( getDato( stringActual, lastString ) );
stringActual = "";
lastString = false;
finString = false;
} else {  // Es un carácter normal
if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
stringActual += car;
}
}
}
} else if (!inString && car=='[') {  // Inicio de lista
if (inList) throw new StringIndexOutOfBoundsException( "Nested lists not allowed in this process in line " + numLine + ": [" + line + "]");
inList = true;
lista = new ArrayList<>();
} else if (!inString && car==']') {  // Posible fin de lista
if (!inList) throw new StringIndexOutOfBoundsException( "Closing list not opened in line " + numLine + ": [" + line + "]");
if (!stringActual.isEmpty()) lista.add( getDato( stringActual, lastString ) );
stringActual = "";
inList = false;
} else {  // Carácter dentro de valor
if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
stringActual += car;
}
posCar++;
}
if (posCar>=line.length() && inString) {  // Se ha acabado la línea sin acabarse el string. Eso es porque algún string incluye salto de línea. Se sigue con la siguiente línea
line = null;
   try {
line = input.readLine();
    if (LOG_CONSOLE_CSV) System.out.println( "  " + numLine + " (add)\t" + line );
posCar = 0;
stringActual += "\n";
} catch (IOException e) {}  // Si la línea es null es que el fichero se ha acabado ya o hay un error de I/O
}
}
if (inString) throw new StringIndexOutOfBoundsException( "String not closed in line " + numLine + ": [" + line + "]");
if (lista!=null)
ret.add( lista );
else if (!stringActual.isEmpty())
ret.add( getDato( stringActual, lastString ) );
return ret;
}

// Devuelve el siguiente carácter (car 0 si no existe el siguiente carácter)
private static char nextCar( String line, int posCar ) {
if (posCar+1<line.length()) return line.charAt( posCar + 1 );
else return Character.MIN_VALUE;
}

// Devuelve el objeto que corresponde a un dato (por defecto String. Si es entero o doble válido, se devuelve ese tipo)
private static Object getDato( String valor, boolean esString ) {
if (esString) return valor;
try {
long entero = Long.parseLong( valor );
return new Long( entero );
} catch (Exception e) {}
try {
double doble = Double.parseDouble( valor );
return new Double( doble );
} catch (Exception e) {}
return valor;
}


private static void procesaCabeceras( ArrayList<Object> cabs ) {
// TODO Cambiar este proceso si se quiere hacer algo con las cabeceras
System.err.println( cabs );  //Saca la cabecera por consola de error
}

private static int numLin = 0;
private static void procesaLineaDatos( ArrayList<Object> datos ) {
// TODO Cambiar este proceso si se quiere hacer algo con las cabeceras
numLin++;
//System.out.println( numLin + "\t" + datos );  // Saca la cabecera por consola
//vent.getTextArea().append("dasdsdsad"); //Para probar en ejercico de swing
UsuarioTwitter usuario = new UsuarioTwitter((String)datos.get(0),(String)datos.get(1),(ArrayList<String>)datos.get(2),(String)datos.get(3),
(long)datos.get(4),(long)datos.get(5),(String)datos.get(6),(long)datos.get(7),(String)datos.get(8),(ArrayList<String>)datos.get(9));

//Paso 4:
//Comprobar si hay repetidos:
if (!mapaPorId.containsKey(usuario.getId())) {
mapaPorId.put(usuario.getId(), usuario);
} else {
System.err.println("Id de usuario duplicado");
}

//Paso 6:
if (!mapaPorNick.containsKey(usuario.getScreenName())) {
mapaPorNick.put(usuario.getScreenName(), usuario);
} else {
System.err.println("Nick de usuario duplicado");
}
//repetidos(mapaPorId);
}

// public static void repetidos(HashMap<String, UsuarioTwitter> map) {
// System.out.println(map.size()!=numLin);
// }

//Paso 7 a):
protected static void comprobarAmigos() {
int contadorTienenAmigos = 0;
for (String nick: mapaPorNick.keySet() ) {
int contadorAmigos = 0;
for (String idAmigo: mapaPorNick.get(nick).getFriends()) {
if (mapaPorId.containsKey(idAmigo)) {
contadorAmigos ++;
}
}
if (contadorAmigos > 0) {
contadorTienenAmigos ++;
System.out.println("Usuario: " + nick + " tiene: " + contadorAmigos +
" amigos en el sistema y " + (mapaPorNick.get(nick).getFriends().size()-contadorAmigos) + " fuera.");
}
}
System.out.println("Hay " + contadorTienenAmigos + " con algunos amigos dentro de nuestro sistema."); //Verificar el enunciado
}

//Paso 7 b):
    protected static void actualizarAmigosEnSistema() {
        for (UsuarioTwitter usuario : mapaPorNick.values()) {
            int amigosEnSistema = 0;
            for (String idAmigo : usuario.getFriends()) {
                if (mapaPorId.containsKey(idAmigo)) {
                    amigosEnSistema++;
                }
            }
            usuario.setAmigosEnSistema(amigosEnSistema);
            if (amigosEnSistema > 0) {
            amigosPorUsuario.add(usuario);
            }
        }
    }
   
  //Paso 7 b) imprimirlos por pantalla:
    protected static void imprimirUsuariosOrdenados() {
        for (UsuarioTwitter usuario : amigosPorUsuario) {
            System.out.println(usuario.getScreenName() + " - " + usuario.getAmigosEnSistema() + " amigos.");
            try {
Thread.sleep(400);
} catch (InterruptedException e) {
e.printStackTrace();
}
        }
    }
   
    //Actualizar lista de amigos para apartado de ejercicios de Swing (solo añadir los Usuarios que tienen mas de 10 amigos):
    protected static void actualizarAmigosEnSistema2() {
        for (UsuarioTwitter usuario : mapaPorNick.values()) {
            int amigosEnSistema = 0;
            for (String idAmigo : usuario.getFriends()) {
                if (mapaPorId.containsKey(idAmigo)) {
                    amigosEnSistema++;
                }
            }
            usuario.setAmigosEnSistema(amigosEnSistema);
            if (amigosEnSistema > 10) {
            amigosPorUsuario.add(usuario);
            }
        }
        //Para apartados de la parte de swing
//        vent = new VentanaGeneral();
//        vent.processButton.doClick();
//        vent.setVisible( true );
//        vent.setDatos(amigosPorUsuario);
    }
}