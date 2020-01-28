package diceforge.moteur;

import diceforge.joueur.Joueur;

import java.util.Random;

public class GestionnaireDeTour {
    private Joueur joueur;
    private Inventaire inventaire;
    private Random rand;

    public GestionnaireDeTour(Random rand) {
        this.rand = rand;
    }


    public void jouer() {
        if ((joueur != null) && (inventaire != null)) {
            joueur.joue();
        }
        afficherScore();
        déterminerGagnant();
    }

    private void déterminerGagnant() {
        System.out.println(joueur.getNom()+" gagne");
    }

    private void afficherScore() {
        System.out.println(joueur.getNom() +" a "+inventaire.getPoints()+" points ");
    }

    public void ajouterJoueur(Joueur j) {
        this.joueur = j;
        this.joueur.setMoteur(this);
        this.inventaire = new Inventaire(this.rand);
    }

    /**
     * lance les dés pour le joueur, comptabilise ses points ainsi gagné et les affiche
     */
    public void lanceMesDés() {
        int points = inventaire.lancerDés();
        inventaire.ajouterPoints(points);
        System.out.println(joueur.getNom()+" lance les dés et obtient "+points+" points");
    }
}

