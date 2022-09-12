package diceforge;

import diceforge.client.Client;
import diceforge.joueur.Joueur;
import diceforge.serveur.Serveur;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Lanceur implements  Runnable {


    private final String j ;

    public Lanceur(String j) {
            this.j = j;
    }

    public final static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String nomJoeur1 = "J1";
        String nomJoeur2 = "J2";

        if (args.length >= 1) nomJoeur1 = args[0];
        if (args.length >= 2) nomJoeur2 = args[1];


        Thread joueur1 = new Thread(new Lanceur(nomJoeur1));


        Thread joueur2 = new Thread(new Lanceur(nomJoeur2));



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
