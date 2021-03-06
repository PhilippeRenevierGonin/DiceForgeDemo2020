package diceforge;

import diceforge.client.Client;
import diceforge.joueur.Joueur;
import diceforge.serveur.Serveur;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class Lanceur implements  Runnable {


    private final String j ;

    public Lanceur(String j) {
            this.j = j;
    }

    public final static void main(String [] args) {

        //  la façon de lancer... forcer à une nouvelle socket, sinon c'était la même socket pour les deux clients
        //  la façon de lancer... forcer à une nouvelle socket, sinon c'était la même socket pour les deux clients


        Thread joueur1 = new Thread(new Lanceur("J1"));


        Thread joueur2 = new Thread(new Lanceur("J2"));



        Thread moteur = new Thread(new Runnable() {
            @Override
            public void run() {
                String [] argsServeur = {};
                Serveur.main(argsServeur);
            }
        });

        moteur.start();
        joueur1.start();
        joueur2.start();

    }

    @Override
    public void run() {
        Socket mSocket = null;
        try {
            // @todo : il crée la même socket.. il faut une option... dans le lanceur
            IO.Options options = new IO.Options();
            options.forceNew = true;
            mSocket = IO.socket("http://127.0.0.1:10101", options);
            System.out.println(mSocket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //"http://192.168.1.23:10101";


        Joueur j = new Joueur(this.j);

        Client c = new Client(mSocket, j);

        j.setClient(c);
    }
}
