//package cap01;
//
//import static org.junit.Assert.*;
//
//import java.awt.AWTException;
//import java.awt.Robot;
//
//import javax.swing.JFrame;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class VentanaMainTest {
//
//	private VentanaMain vent = new VentanaMain(null, null);
//	
//	//Alguna prueba para los componentes de las ventanas
//	@Test
//	public void getVentanaMainTest() {
//		JFrame v = vent.getVentanaMain();
//		v.setVisible( true );
//		
//		//Comprobar el Título;
//		assertEquals("SEGUNDA GUERRA MUNDIAL", v.getTitle());
//		
//		Cliente c = vent.getAliado();
//		assertEquals("Enviar", c.getBtnEnvio().getText());
//		assertEquals("ALIADO - ALEMANIA", c.getLblTexto().getText());
//		
//		for (int i = 0; i < c.getComboAliados().getItemCount(); i ++) {
//			if (i == 0) {
//				assertEquals("Italia", c.getComboAliados().getItemAt(i));
//			} else {
//				assertEquals("Japon", c.getComboAliados().getItemAt(i));
//			}
//		}
//	}
//
//}
