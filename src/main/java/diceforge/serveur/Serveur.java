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

public class Serveur implements ConnectListener, DataListener<String> {


    private final GestionnaireDeTour moteur;
    private SocketIOClient monClient;

    public Serveur(Configuration config, GestionnaireDeTour moteur) {
        // le moteur
        this.moteur = moteur;

        // creation du serveur
        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(this);

        server.addEventListener("jouer", String.class, this);


        server.start(); // démarre un thread… le programme ne s’arrêtera pas tant que le serveur n’est pas terminé

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

    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        monClient = socketIOClient;
        moteur.ajouterJoueur(new Identité("nomTemp")); //@todo, recevoir le nom
        moteur.jouer();
    }

    public void transfereDemandeDeJouer() {
        monClient.sendEvent("àToiDeJoeur");
    }

    @Override
    public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
        moteur.lanceMesDés();
    }
}
