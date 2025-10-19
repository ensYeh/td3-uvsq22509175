package fr.uvsq.cprog.collex;

/**
 * Commande pour quitter l'application.
 */
public class QuitterCommande implements Commande {

    public QuitterCommande() {
        // Constructeur vide
    }

    @Override
    public void execute() {
        System.out.println("Au revoir !");
        System.exit(0);
    }
}
