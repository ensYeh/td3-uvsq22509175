package fr.uvsq.cprog.collex;

/**
 * Classe principale de l'application DNS.
 */
public class App {

    private final Dns dns;
    private final DnsTUI tui;

    public App() {
        this.dns = new Dns();
        this.tui = new DnsTUI(dns);
    }

    /**
     * Lance la boucle principale de l'application.
     */
    public void run() {
        tui.affiche("Bienvenue sur l'application DNS. Tapez 'quit' ou 'exit' pour quitter.");
        while (true) {
            Commande command = tui.nextCommande();
            command.execute();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
