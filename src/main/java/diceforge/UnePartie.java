package diceforge;

import diceforge.joueur.Joueur;
import diceforge.moteur.GestionnaireDeTour;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class UnePartie {

    public UnePartie() {
        System.out.println("début de la partie");

        Joueur j = new Joueur("premier joueur");
        GestionnaireDeTour moteur = new GestionnaireDeTour();
        moteur.ajouterJoueur(j);

        moteur.jouer();
    }

    public static final void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new UnePartie();

    }
}
