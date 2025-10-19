package fr.uvsq.cprog.collex;

import java.util.Scanner;

/**
 * Classe représentant l'interface utilisateur en ligne de commande.
 */
public class DnsTUI {

  private final Dns dns;
  private final Scanner scanner;

  public DnsTUI(Dns dns) {
    this.dns = dns;
    this.scanner = new Scanner(System.in);
  }

  public void affiche(String resultat) {
    System.out.println(resultat);
  }

  /**
   * Lit la prochaine commande de l'utilisateur.
   */
  public Commande nextCommande() {
    System.out.print("> ");
    String input = scanner.nextLine().trim();
    if (input.isEmpty()) {
      return () -> { }; // Commande vide qui ne fait rien
    }

    String[] parts = input.split("\\s+");
    String commandName = parts[0];

    if (commandName.equalsIgnoreCase("quit") || commandName.equalsIgnoreCase("exit")) {
      return new QuitterCommande();
    }

    if (commandName.equalsIgnoreCase("ls")) {
      return parseLsCommand(parts);
    }

    if (commandName.equalsIgnoreCase("add")) {
      return parseAddCommand(parts);
    }

    if (parts.length == 1) {
      return parseLookupCommand(input);
    }

    return createErrorCommand("Commande inconnue.");
  }

  private Commande parseLsCommand(String[] parts) {
    if (parts.length == 2) {
      return new LsCommande(dns, this, parts[1], false);
    }
    if (parts.length == 3 && parts[1].equals("-a")) {
      return new LsCommande(dns, this, parts[2], true);
    }
    return createErrorCommand("ERREUR: Syntaxe 'ls': ls [-a] <domaine>");
  }

  private Commande parseAddCommand(String[] parts) {
    if (parts.length != 3) {
      return createErrorCommand("ERREUR: Syntaxe 'add': add <adresse.ip> <nom.machine>");
    }
    try {
      // L'ordre dans la commande est `add ip nom`, 
      // mais le constructeur de DnsItem est `(ip, nom)`
      AdresseIP ip = new AdresseIP(parts[1]);
      NomMachine name = new NomMachine(parts[2]);
      return new AjoutCommande(dns, this, ip, name);
    } catch (IllegalArgumentException e) {
      return createErrorCommand("ERREUR: " + e.getMessage());
    }
  }

  private Commande parseLookupCommand(String input) {
    try {
      return new RechercheNomCommande(dns, this, new AdresseIP(input));
    } catch (IllegalArgumentException ipError) {
      try {
        return new RechercheIpCommande(dns, this, new NomMachine(input));
      } catch (IllegalArgumentException nameError) {
        return createErrorCommand("ERREUR: Entrée non reconnue.");
      }
    }
  }

  private Commande createErrorCommand(String message) {
    return () -> affiche(message);
  }
}
