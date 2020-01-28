package diceforge;

import diceforge.client.Client;
import diceforge.joueur.Joueur;
import diceforge.moteur.GestionnaireDeTour;
import diceforge.serveur.Serveur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Random;

public class UnePartie {

    public UnePartie() {
        System.out.println("début de la partie");

        Serveur.main(null);
        try {
            Client.main(null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
