package fr.uvsq.cprog.collex;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DnsTest {

    private Path dbPath;

    @Before
    public void setUp() throws IOException {
        // On s'assure que le fichier de config pointe vers le bon fichier de test
        // Pour ce test, on va supposer que Dns() utilise "dns_db.txt"
        // On va donc réécrire ce fichier avant chaque test pour avoir un état connu.
        dbPath = Paths.get("dns_db.txt");
        List<String> lines = List.of(
            "www.google.com 8.8.8.8",
            "www.uvsq.fr 193.51.31.90"
        );
        Files.write(dbPath, lines);
    }

    @Test
    public void testDnsLoading() {
        Dns dns = new Dns();
        DnsItem item = dns.getItem(new NomMachine("www.google.com"));
        assertNotNull(item);
        assertEquals(new AdresseIP("8.8.8.8"), item.getAdresse());
    }

    @Test
    public void testGetItemByIp() {
        Dns dns = new Dns();
        DnsItem item = dns.getItem(new AdresseIP("193.51.31.90"));
        assertNotNull(item);
        assertEquals(new NomMachine("www.uvsq.fr"), item.getNom());
    }

    @Test
    public void testGetItemsByDomain() {
        Dns dns = new Dns();
        List<DnsItem> items = dns.getItems("uvsq.fr");
        assertEquals(1, items.size());
        assertEquals(new NomMachine("www.uvsq.fr"), items.get(0).getNom());
    }

    @Test
    public void testAddItem() throws IOException {
        Dns dns = new Dns();
        AdresseIP newIp = new AdresseIP("1.1.1.1");
        NomMachine newName = new NomMachine("one.one.one.one");
        
        dns.addItem(newIp, newName);

        // Vérifier en mémoire
        DnsItem item = dns.getItem(newIp);
        assertNotNull(item);
        assertEquals(newName, item.getNom());

        // Vérifier dans le fichier
        List<String> lines = Files.readAllLines(dbPath);
        assertTrue(lines.stream().anyMatch(line -> line.contains("one.one.one.one 1.1.1.1")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateIp() {
        Dns dns = new Dns();
        dns.addItem(new AdresseIP("8.8.8.8"), new NomMachine("new.machine.com"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateName() {
        Dns dns = new Dns();
        dns.addItem(new AdresseIP("1.2.3.4"), new NomMachine("www.google.com"));
    }
}
