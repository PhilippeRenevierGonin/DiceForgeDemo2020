package diceforge;

import diceforge.client.Client;
import diceforge.serveur.Serveur;

import java.net.URISyntaxException;

public class Lanceur {

    public final static void main(String [] args) {

        Thread joueur = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String [] argsClient = {};
                    Client.main(argsClient);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread moteur = new Thread(new Runnable() {
            @Override
            public void run() {
                String [] argsServeur = {};
                Serveur.main(argsServeur);
            }
        });

        moteur.start();
        joueur.start();

    }
}
