package diceforge.joueur;

import diceforge.moteur.GestionnaireDeTour;

public class Joueur {

    private String nom;

    public void setMoteur(GestionnaireDeTour moteur) {
        this.moteur = moteur;
    }

    private GestionnaireDeTour moteur;

    public Joueur(String nom) {
        setNom(nom);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }


}
