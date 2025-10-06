package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class NomMachineTest {

    @Test
    public void parseValid() {
        NomMachine n = new NomMachine("www.uvsq.fr");
        assertEquals("www", n.getHost());
        assertEquals("uvsq.fr", n.getDomain());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalid() {
        new NomMachine("invalidname");
    }
}
