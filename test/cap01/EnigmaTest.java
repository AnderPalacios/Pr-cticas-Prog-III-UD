package cap01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnigmaTest {
	
    @Test
    public void encripatrString() {
        String uno = ",";
        assertEquals(".", Enigma.encriptar(uno));
        assertEquals("KHÑÑR ZRUÑG", Enigma.encriptar("HELLO WORLD"));
        assertEquals("khññr zruñg", Enigma.encriptar("hello world"));
        assertEquals("!@#$%^", Enigma.encriptar("!@#$%^"));
        assertEquals(". . . ", Enigma.encriptar(". , , "));
        assertEquals("dEfg", Enigma.encriptar("aBcd"));
        assertEquals("dEf.g", Enigma.encriptar("aBc,d"));
        assertEquals("c", Enigma.encriptar("z"));
        assertEquals("abc", Enigma.encriptar("xyz"));
        assertEquals("z", Enigma.encriptar("w"));
        assertEquals("\n", Enigma.encriptar("\n"));
        assertEquals("\t", Enigma.encriptar("\t"));
        assertEquals("''", Enigma.encriptar("''"));
    }

    @Test
    public void encriptarConTilde() {
        assertEquals("Mdscp", Enigma.encriptar("Japón"));
        assertEquals("c", Enigma.encriptar("ó"));
        assertEquals("c", Enigma.encriptar("á"));
        assertEquals("ccc", Enigma.encriptar("úáé"));
    }

    @Test
    public void encriptarNumero() {
        assertEquals("0123", Enigma.encriptar("1234"));
        assertEquals("23789", Enigma.encriptar("34567"));
        assertEquals("111000000", Enigma.encriptar("999888888"));
        assertEquals("1", Enigma.encriptar("9"));
        assertEquals("9", Enigma.encriptar("0"));
        assertEquals("90", Enigma.encriptar("08"));
    }

    // Probar números negativos ( '-' es un caracter, los números no se van a interpretar como negativos):
    @Test
    public void encriptarNumeroNegativo() {
        assertEquals("-0", Enigma.encriptar("-1"));
        assertEquals("-179", Enigma.encriptar("-250"));
        assertEquals("-113773", Enigma.encriptar("-994554"));
    }

    @Test
    public void encriptarStringYNumero() {
        assertEquals(".d0e-1f2g", Enigma.encriptar(",a1b-2c3d"));
        assertEquals("C-+e-179g", Enigma.encriptar("Z-+b-257d"));
        assertEquals("Lwdñld: 0121", Enigma.encriptar("Italia: 1939"));
        assertEquals("Sdud: DÑHODPLD-edvh:Urod H2", Enigma.encriptar("Para: ALEMANIA-base:Roma E3"));
        assertEquals("Mdsrp: 0137 ¿|? ilp", Enigma.encriptar("Japon: 1945 ¿|? fin"));
    }

}
