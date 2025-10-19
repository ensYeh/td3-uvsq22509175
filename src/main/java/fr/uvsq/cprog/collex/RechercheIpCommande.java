package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher une adresse IP à partir d'un nom de machine.
 */
public class RechercheIpCommande implements Commande {

  private final Dns dns;
  private final DnsTUI tui;
  private final NomMachine nom;

  /**
   * Constructeur de la commande de recherche d'IP.
   */
  public RechercheIpCommande(Dns dns, DnsTUI tui, NomMachine nom) {
    this.dns = dns;
    this.tui = tui;
    this.nom = nom;
  }

  @Override
  public void execute() {
    DnsItem item = dns.getItem(nom);
    if (item != null) {
      tui.affiche(item.getAdresse().toString());
    } else {
      tui.affiche("Aucune adresse IP trouvée pour " + nom);
    }
  }
}
