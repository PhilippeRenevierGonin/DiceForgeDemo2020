package diceforge.serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import diceforge.echange.Protocole;
import diceforge.echange.WebSocketConstantes;
import diceforge.joueur.Identité;
import diceforge.moteur.GestionnaireDeTour;
import diceforge.random.Random;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static diceforge.echange.Protocole.DEMANDER_AU_JOUEUR_DE_JOUER;

public class ServeurWebSocket extends WebSocketServer implements Serveur {


    private final GestionnaireDeTour moteur;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<CoupleIdClient> mesClients = new ArrayList<>();

    protected class CoupleIdClient {
        Identité id;
        WebSocket client;

        CoupleIdClient(Identité id, WebSocket client){
            this.id = id;
            this.client = client;
        }

    }

    public ServeurWebSocket(InetAddress addr, int port, GestionnaireDeTour moteur) throws UnknownHostException {
        super(new InetSocketAddress(addr, port));
        this.moteur = moteur;
    }

    @Override
    public void transfereDemandeDeJouer(Identité id) {
        System.out.println("transfereDemandeDeJouer taille client = "+ mesClients.size()+ " id = "+id+ " / "+ mesClients.get(retrouverJoueur(id)).id);
        System.out.println("transfereDemandeDeJouer send ");
        mesClients.get(retrouverJoueur(id)).client.send(DEMANDER_AU_JOUEUR_DE_JOUER);
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
    public void terminer() {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        stop(1000, "serveur > la partie est finie");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                }
            });
            t.start();

    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Server > "+webSocket);
        System.out.println("Server > "+webSocket.getLocalSocketAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("serveur >  on close");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println("serveur > " + s);
        int i = s.indexOf(WebSocketConstantes.SEPARATEUR);
        if (i > 0) {
            String msg = s.substring(0, i);
            System.out.println("serveur > onMessage > msg : " + msg);
            String json = s.substring(i+WebSocketConstantes.SEPARATEUR.length());
            System.out.println("serveur > onMessage > json : " + json);

            if (msg.equals(Protocole.IDENTIFICATION)) receptionNouveauJoueur(webSocket, json);
            else if (msg.equals(Protocole.DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS)) reçuCoup(webSocket, json);

        }
    }

    private synchronized void  receptionNouveauJoueur(WebSocket webSocket, String id) {
        try {
            Identité joueur = objectMapper.readValue(id, Identité.class);
            mesClients.add(new CoupleIdClient(joueur, webSocket));
            moteur.ajouterJoueur(joueur);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public synchronized void reçuCoup(WebSocket webSocket, String json)  {
        // @todo à déplacer dans GestionnaireDeTour car ici cela suppose que les id ont été ajouté dans le même ordre
        try {
            Identité joueur = objectMapper.readValue(json, Identité.class);
            int i = retrouverJoueur(joueur);
            System.out.println("c'est le joueur "+i+" id = "+joueur);
            moteur.lanceMesDés(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onStart() {

        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);

    }



    public static final void main(String [] args) throws UnknownHostException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        GestionnaireDeTour moteur = new GestionnaireDeTour(new Random());

        /*
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        InetAddress [] ia = InetAddress.getAllByName("macharpima");
        System.out.println(ia);
        System.out.println(ia.length);
        for(InetAddress i : ia) System.out.println(i); */

        // ServeurWebSocket serveur = new ServeurWebSocket(ia[0], 10101, moteur);
        ServeurWebSocket serveur = new ServeurWebSocket(InetAddress.getLocalHost(), 10101, moteur);
        moteur.setServeur(serveur);

        serveur.start();

/*
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = null;
            try {
                in = sysin.readLine();
                if (in.equals("exit")) {
                    serveur.stop(1000);
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

 */
    }
}
