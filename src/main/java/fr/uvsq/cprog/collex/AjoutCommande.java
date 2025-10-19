package fr.uvsq.cprog.collex;

/**
 * Commande pour ajouter une nouvelle entrée dans le DNS.
 */
public class AjoutCommande implements Commande {

  private final Dns dns;
  private final DnsTUI tui;
  private final AdresseIP ip;
  private final NomMachine nom;

  /**
   * Constructeur de la commande d'ajout.
   */
  public AjoutCommande(Dns dns, DnsTUI tui, AdresseIP ip, NomMachine nom) {
    this.dns = dns;
    this.tui = tui;
    this.ip = ip;
    this.nom = nom;
  }

  @Override
  public void execute() {
    try {
      dns.addItem(ip, nom);
      // Le README ne montre pas de message en cas de succès.
    } catch (IllegalArgumentException e) {
      tui.affiche(e.getMessage());
    }
  }
}
