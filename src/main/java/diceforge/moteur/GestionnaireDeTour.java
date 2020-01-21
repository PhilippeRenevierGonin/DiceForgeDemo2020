package diceforge.moteur;

import diceforge.joueur.Joueur;

public class GestionnaireDeTour {
    private Joueur joueur;
    private Inventaire inventaire;


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
        this.inventaire = new Inventaire();
    }

    public void lanceMesDés() {
        int points = inventaire.lancerDés();
        inventaire.ajouterPoints(points);
        System.out.println(joueur.getNom()+" lance les dés et obtient "+points+" points");
    }
}

