package diceforge.serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import diceforge.joueur.Identité;
import diceforge.moteur.GestionnaireDeTour;
import diceforge.random.Random;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static diceforge.echange.Protocole.*;

public class ServeurSocketIO  implements /* ConnectListener, */ Serveur, DataListener<Identité> {


    protected class CoupleIdClient {
        Identité id;
        SocketIOClient client;

        CoupleIdClient(Identité id, SocketIOClient client){
            this.id = id;
            this.client = client;
        }

    }

    private final GestionnaireDeTour moteur;
    private ArrayList<CoupleIdClient> mesClients = new ArrayList<>();
    SocketIOServer server;

    public ServeurSocketIO(Configuration config, GestionnaireDeTour moteur) {
        // le moteur
        this.moteur = moteur;

        // creation du serveur
        server = new SocketIOServer(config);

        // changement de protocole, la connexion est reportée à un message "identification"
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("Server > "+socketIOClient);
                System.out.println("Server > "+socketIOClient.getTransport());
                System.out.println("Server > "+server.getConfiguration().getTransports());
            }
        });

        server.addEventListener(IDENTIFICATION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité nom, AckRequest ackRequest) throws Exception {
                System.out.println("NEW PLAYER "+nom);
                receptionNouveauJoueur(socketIOClient, nom);
            }
        });

        server.addEventListener(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS, Identité.class, this);


        server.start(); // démarre un thread… le programme ne s’arrêtera pas tant que le serveur n’est pas terminé

    }

    protected synchronized void receptionNouveauJoueur(SocketIOClient socketIOClient, Identité id) {
        System.out.println("receptionNouveauJoueur "+id);
        mesClients.add(new CoupleIdClient(id, socketIOClient));
        // System.out.println(monClient.getRemoteAddress());
        // pour exemple javadoc : moteur.setServeur(this);
        // la ligne avait été écrasée lors de l'exemple de javadoc
        moteur.ajouterJoueur(id);
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

        ServeurSocketIO serveur = new ServeurSocketIO(config, moteur);
        moteur.setServeur(serveur);
    }

    /*
    // changement de protocole, la connexion est reportée à un message "identification"
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        monClient = socketIOClient;
        // System.out.println(monClient.getRemoteAddress());
        moteur.ajouterJoueur(new Identité("nomTemp"));
        moteur.jouer();
    }
    */
    public void  transfereDemandeDeJouer(Identité id) {
        System.out.println("transfereDemandeDeJouer taille client = "+ mesClients.size()+ " id = "+id+ " / "+ mesClients.get(retrouverJoueur(id)).id);
        System.out.println("transfereDemandeDeJouer sendEvent ");
        mesClients.get(retrouverJoueur(id)).client.sendEvent(DEMANDER_AU_JOUEUR_DE_JOUER);
    }

    int retrouverJoueur(Identité id) {
        int res = -1;
        for(int i = 0; i < mesClients.size(); i++) {
            CoupleIdClient couple = mesClients.get(i);
            if (couple.id.equals(id)) {

                res = i;
                break;
            }
        }

        return res;
    }

    @Override
    public synchronized void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) throws Exception {
        // @todo à déplacer dans GestionnaireDeTour car ici cela suppose que les id ont été ajouté dans le même ordre
        int i = retrouverJoueur(id);
        System.out.println("c'est le joueur "+i+" id = "+id);
        moteur.lanceMesDés(i);
    }

    public void terminer() {
        for(CoupleIdClient c : mesClients) c.client.disconnect();

        new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop(); // à faire sur un autre thread que sur le thread de SocketIO
                System.out.println("fin du serveur - fin");

            }
        }).start();

    }
}
