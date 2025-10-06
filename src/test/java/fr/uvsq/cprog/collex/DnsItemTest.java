package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;

public class DnsItemTest {

    @Test
    public void createItem() {
        AdresseIP ip = new AdresseIP("193.51.31.90");
        NomMachine nm = new NomMachine("www.uvsq.fr");
        DnsItem item = new DnsItem(ip, nm);
        assertEquals(ip, item.getAdresse());
        assertEquals(nm, item.getNom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullsNotAllowed() {
        new DnsItem(null, null);
    }
}
