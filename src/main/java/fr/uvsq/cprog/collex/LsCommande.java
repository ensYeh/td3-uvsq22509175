package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;

/**
 * Commande pour lister les machines d'un domaine.
 */
public class LsCommande implements Commande {

  private final Dns dns;
  private final DnsTUI tui;
  private final String domain;
  private final boolean sortByIp;

  /**
   * Constructeur de la commande ls.
   */
  public LsCommande(Dns dns, DnsTUI tui, String domain, boolean sortByIp) {
    this.dns = dns;
    this.tui = tui;
    this.domain = domain;
    this.sortByIp = sortByIp;
  }

  @Override
  public void execute() {
    List<DnsItem> items = dns.getItems(domain);

    if (sortByIp) {
      // Tri par adresse IP. AdresseIP implémente Comparable.
      items.sort(Comparator.comparing(DnsItem::getAdresse));
    } else {
      // Tri par nom de machine.
      items.sort(Comparator.comparing(item -> item.getNom().toString()));
    }

    if (items.isEmpty()) {
      tui.affiche("Aucune machine trouvée pour le domaine " + domain);
    } else {
      for (DnsItem item : items) {
        // Format: "193.51.25.12 ecampus.uvsq.fr"
        tui.affiche(item.getAdresse().toString() + " " + item.getNom().toString());
      }
    }
  }
}
