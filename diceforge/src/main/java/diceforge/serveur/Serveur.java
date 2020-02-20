package diceforge.serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import diceforge.joueur.Identité;
import diceforge.moteur.GestionnaireDeTour;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import static diceforge.echange.Protocole.*;

public class Serveur implements /* ConnectListener, */ DataListener<String> {


    private final GestionnaireDeTour moteur;
    private SocketIOClient monClient;
    SocketIOServer server;

    public Serveur(Configuration config, GestionnaireDeTour moteur) {
        // le moteur
        this.moteur = moteur;

        // creation du serveur
        server = new SocketIOServer(config);

        // changement de protocole, la connexion est reportée à un message "identification"
        // server.addConnectListener(this);

        server.addEventListener(IDENTIFICATION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité nom, AckRequest ackRequest) throws Exception {
                receptionNouveauJoueur(socketIOClient, nom);
            }
        });

        server.addEventListener(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS, String.class, this);


        server.start(); // démarre un thread… le programme ne s’arrêtera pas tant que le serveur n’est pas terminé

    }

    protected void receptionNouveauJoueur(SocketIOClient socketIOClient, Identité id) {
        monClient = socketIOClient;
        // System.out.println(monClient.getRemoteAddress());
        moteur.ajouterJoueur(id); //@todo, recevoir le nom
        moteur.jouer();
    }


    public final static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // config  com.corundumstudio.socketio.Configuration;
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        // le gestionnaire de tour
        GestionnaireDeTour moteur = new GestionnaireDeTour(new Random());

        Serveur serveur = new Serveur(config, moteur);
        moteur.setServeur(serveur);
    }

    /*
    // changement de protocole, la connexion est reportée à un message "identification"
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        monClient = socketIOClient;
        // System.out.println(monClient.getRemoteAddress());
        moteur.ajouterJoueur(new Identité("nomTemp")); //@todo, recevoir le nom
        moteur.jouer();
    }
    */
    public void transfereDemandeDeJouer() {
        monClient.sendEvent(DEMANDER_AU_JOUEUR_DE_JOUER);
    }

    @Override
    public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
        moteur.lanceMesDés();
    }

    public void terminer() {
        monClient.disconnect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop(); // à faire sur un autre thread que sur le thread de SocketIO
                System.out.println("fin du serveur - fin");

            }
        }).start();

    }
}
