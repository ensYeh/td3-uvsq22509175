package fr.uvsq.cprog.collex;

/**
 * Interface pour le pattern Commande.
 * Chaque commande que l'utilisateur peut exécuter implémentera cette interface.
 */
public interface Commande {
    /**
     * Exécute la commande.
     */
    void execute();
}
