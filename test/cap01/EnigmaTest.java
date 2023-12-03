package cap01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnigmaTest {
	
	@Test
	public void encripatrString() {
		String uno = ",";
		assertEquals(Enigma.encriptar(uno),".");
        assertEquals(Enigma.encriptar("HELLO WORLD"), "KHÑÑR ZRUÑG");
        assertEquals(Enigma.encriptar("hello world"),"khññr zruñg");
        assertEquals(Enigma.encriptar("!@#$%^"),"!@#$%^");
        assertEquals(Enigma.encriptar(". , , "), ". . . ");
        assertEquals(Enigma.encriptar("aBcd"), "dEfg");
        assertEquals(Enigma.encriptar("aBc,d"), "dEf.g");
        assertEquals(Enigma.encriptar("z"), "c");
        assertEquals(Enigma.encriptar("xyz"), "abc");
        assertEquals(Enigma.encriptar("w"), "z");
        assertEquals(Enigma.encriptar("\n"), "\n");
        assertEquals(Enigma.encriptar("\t"), "\t");
        assertEquals(Enigma.encriptar("''"), "''");
	}
	
	public void encriptarConTilde() {
		assertEquals("Japón", "Mdscp");
	}
	
	@Test
	public void encriptarNumero() {
		assertEquals(Enigma.encriptar("1234"), "0123");
		assertEquals(Enigma.encriptar("34567"), "23789");
		assertEquals(Enigma.encriptar("999888888"), "111000000");
		assertEquals(Enigma.encriptar("9"), "1");
		assertEquals(Enigma.encriptar("0"), "9");
		assertEquals(Enigma.encriptar("08"), "90");
	}
	
	//Probar números negativos ( '-' es un caracter, los números no se van a interpretar como negatios):
	@Test
	public void encriptarNumeroNegativo() {
		assertEquals(Enigma.encriptar("-1"), "-0");
		assertEquals(Enigma.encriptar("-250"), "-179");
		assertEquals(Enigma.encriptar("-994554"), "-113773");
	}
	
	@Test
	public void encriptarStringYNumero() {
		assertEquals(Enigma.encriptar(",a1b-2c3d"), ".d0e-1f2g");
		assertEquals(Enigma.encriptar("Z-+b-257d"), "C-+e-179g");
		assertEquals(Enigma.encriptar("Italia: 1939"), "Lwdñld: 0121");
		assertEquals(Enigma.encriptar("Para: ALEMANIA-base:Roma E3"), "Sdud: DÑHODPLD-edvh:Urod H2");
		assertEquals(Enigma.encriptar("Japon: 1945 ¿|? fin"), "Mdsrp: 0137 ¿|? ilp");
	}

}
