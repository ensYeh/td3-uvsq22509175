package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Classe représentant un service DNS.
 */
public class Dns {

  private final Map<NomMachine, DnsItem> itemsByMachine;
  private final Map<AdresseIP, DnsItem> itemsByIp;
  private String dbFilePath;

  /**
   * Constructeur de la classe Dns.
   */
  public Dns() {
    this.itemsByMachine = new HashMap<>();
    this.itemsByIp = new HashMap<>();
    loadDatabase();
  }

  private void loadDatabase() {
    Properties prop = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        System.out.println("Désolé, impossible de trouver le fichier config.properties");
        return;
      }
      prop.load(input);
      this.dbFilePath = prop.getProperty("db.file");
      Path dbPath = Paths.get(this.dbFilePath);

      if (Files.exists(dbPath)) {
        List<String> lines = Files.readAllLines(dbPath);
        for (String line : lines) {
          String[] parts = line.split("\\s+");
          if (parts.length == 2) {
            try {
              NomMachine name = new NomMachine(parts[0]);
              AdresseIP ip = new AdresseIP(parts[1]);
              DnsItem item = new DnsItem(ip, name);
              this.itemsByMachine.put(name, item);
              this.itemsByIp.put(ip, item);
            } catch (IllegalArgumentException e) {
              System.err.println(
                  "Ligne invalide dans la base de données ignorée: " 
                  + line + " - " + e.getMessage());
            }
          }
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public DnsItem getItem(NomMachine name) {
    return itemsByMachine.get(name);
  }

  public DnsItem getItem(AdresseIP ip) {
    return itemsByIp.get(ip);
  }

  /**
   * Retourne la liste des items d'un domaine.
   */
  public List<DnsItem> getItems(String domain) {
    return itemsByMachine.values().stream()
        .filter(item -> item.getNom().getDomain().equals(domain))
        .collect(Collectors.toList());
  }

  /**
   * Ajoute un item dans le DNS.
   */
  public void addItem(AdresseIP ip, NomMachine name) {
    if (itemsByIp.containsKey(ip)) {
      throw new IllegalArgumentException("ERREUR : L'adresse IP existe déjà !");
    }
    if (itemsByMachine.containsKey(name)) {
      throw new IllegalArgumentException("ERREUR : Le nom de machine existe déjà !");
    }

    DnsItem newItem = new DnsItem(ip, name);
    itemsByIp.put(ip, newItem);
    itemsByMachine.put(name, newItem);

    updateDatabaseFile();
  }

  private void updateDatabaseFile() {
    if (this.dbFilePath == null) {
      System.err.println(
          "Le chemin du fichier de base de données n'est pas configuré. "
          + "La mise à jour est impossible.");
      return;
    }
    Path dbPath = Paths.get(this.dbFilePath);
    List<String> lines = itemsByMachine.values().stream()
        .map(item -> item.getNom().toString() + " " + item.getAdresse().toString())
        .sorted((a, b) -> a.compareTo(b)) // Tri pour un fichier propre
        .collect(Collectors.toList());
    try {
      Files.write(dbPath, lines);
    } catch (IOException e) {
      System.err.println(
          "Erreur lors de la mise à jour du fichier de base de données: " + e.getMessage());
    }
  }
}
