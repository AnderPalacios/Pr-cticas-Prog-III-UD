package cap00;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

public class PruebaEcosistema {
	
	public static void main(String[] args) {
		//Voy a hacerlo con un Random para ver como se actualiza con diferentes valores
		Random r = new Random();
		
		Agua agua = new Agua("Lago Ontario",new Point(100,200), new Dimension(30,40), (long)r.nextInt(150));
		ColoniaAbejas colonia1 = new ColoniaAbejas("Colonia 1", new Point(20,30), new Dimension(5,5), (long)r.nextInt(90));
		PlantacionFlores flores1 = new PlantacionFlores("Rosal", new Point(40,40), new Dimension(13,10), (long)r.nextInt(50));

		Ecosistema ecosistema = Ecosistema.getMundo();
		ecosistema.getElementos().add(agua);
		ecosistema.getElementos().add(colonia1);
		ecosistema.getElementos().add(flores1);
		
		for (int i = 1; i < 11; i ++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Día " + i);
			for (ElementoEcosistema ee: ecosistema.getElementos()) {
				ee.evoluciona(i);
				System.out.println(ee);
			}
			
		}
	}
}
