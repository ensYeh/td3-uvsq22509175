package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher un nom de machine à partir d'une adresse IP.
 */
public class RechercheNomCommande implements Commande {

    private final Dns dns;
    private final DnsTUI tui;
    private final AdresseIP ip;

    public RechercheNomCommande(Dns dns, DnsTUI tui, AdresseIP ip) {
        this.dns = dns;
        this.tui = tui;
        this.ip = ip;
    }

    @Override
    public void execute() {
        DnsItem item = dns.getItem(ip);
        if (item != null) {
            tui.affiche(item.getNom().toString());
        } else {
            tui.affiche("Aucun nom de machine trouvé pour " + ip);
        }
    }
}
