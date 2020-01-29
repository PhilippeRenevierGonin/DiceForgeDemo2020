package diceforge.moteur;

import diceforge.joueur.Identité;
import diceforge.serveur.Serveur;

import java.util.Random;

public class GestionnaireDeTour {
    private Identité joueur;
    private Inventaire inventaire;
    private Random rand;
    private Serveur serveur;

    public GestionnaireDeTour(Random rand) {
        this.rand = rand;
    }


    public void jouer() {
        if ((joueur != null) && (inventaire != null)) {
            serveur.transfereDemandeDeJouer();
        }

    }

    private void déterminerGagnant() {
        System.out.println(joueur.getNom()+" gagne");
    }

    private void afficherScore() {
        System.out.println(joueur.getNom() +" a "+inventaire.getPoints()+" points ");
    }

    public void ajouterJoueur(Identité joueurIdentité) {
        this.joueur = joueurIdentité;
        this.inventaire = new Inventaire(this.rand);
    }

    /**
     * lance les dés pour le joueur, comptabilise ses points ainsi gagné et les affiche
     */
    public void lanceMesDés() {
        int points = inventaire.lancerDés();
        inventaire.ajouterPoints(points);
        System.out.println(joueur.getNom()+" lance les dés et obtient "+points+" points");

        // déplacer ici car asynchrone
        afficherScore();
        déterminerGagnant();
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public Serveur getServeur() {
        return serveur;
    }
}

