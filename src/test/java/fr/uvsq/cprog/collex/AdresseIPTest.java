package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class AdresseIPTest {

    @Test
    public void parseValid() {
        AdresseIP ip = new AdresseIP("193.51.31.90");
        assertEquals("193.51.31.90", ip.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidFormat() {
        new AdresseIP("193.51.31");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidOctet() {
        new AdresseIP("256.0.0.1");
    }
}
