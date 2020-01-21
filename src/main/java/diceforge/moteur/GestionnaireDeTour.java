package diceforge.moteur;

import diceforge.joueur.Joueur;

public class GestionnaireDeTour {

    private Joueur joueur;
    private Inventaire inventaire;

    public void jouer() {

    }

    public void ajouterJoueur(Joueur j) {
        this.joueur = j;
        this.joueur.setMoteur(this);
        this.inventaire = new Inventaire();
    }
}
