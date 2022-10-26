package diceforge.client;

import diceforge.echange.Protocole;
import diceforge.echange.ToJSON;
import diceforge.echange.WebSocketConstantes;
import diceforge.joueur.Joueur;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;

public class ClientWebSocket implements Client {


    private final WebSocketClient clientWS;
    private final Joueur joueur;

    public final static void main(String [] args) throws URISyntaxException {

        String nom = "client à distance";
        if (args.length >= 1) nom = args[0];

        Joueur j = new Joueur(nom);
        // @todo adresse écrite en dur
        Client client = new ClientWebSocket("ws://134.59.20.194:10101", j);

    }




    public ClientWebSocket(String adresse, Joueur joueur) {
        this.joueur = joueur;
        try {
            clientWS = new WebSocketClient(new URI(adresse)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("Client > CONNECTÉ");
                    joueur.setClient(ClientWebSocket.this);

                }

                @Override
                public void onMessage(String s) {
                    System.out.println("Client > on a reçu "+s);
                    if (s.equals(Protocole.DEMANDER_AU_JOUEUR_DE_JOUER)) {
                        joueur.joue();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("Client > on a onClose");
                    clientWS.close();
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Client > error");
                    e.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        clientWS.connect();
    }


    @Override
    public void transfereMessage(String msg) {
        System.out.println("Client > not impl transfereMessage");
    }

    @Override
    public void transfereMessage(String msg, ToJSON obj) {
        clientWS.send(msg+ WebSocketConstantes.SEPARATEUR+obj.toJSON());
    }

    @Override
    public void connnexion() {

    }
}
