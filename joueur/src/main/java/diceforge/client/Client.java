package diceforge.client;

import diceforge.echange.ToJSON;
import diceforge.joueur.Identité;
import diceforge.joueur.Joueur;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static diceforge.echange.Protocole.DEMANDER_AU_JOUEUR_DE_JOUER;

public class Client implements Emitter.Listener {

    private final Socket mSocket;
    private final Joueur joueur;

    public Client(Socket mSocket, Joueur joueur) {
        this.mSocket = mSocket;
        this.joueur = joueur;
        mSocket.on(DEMANDER_AU_JOUEUR_DE_JOUER, this);


    }

    public void connnexion() {
        mSocket.connect();
    }

    public final static void main(String [] args) throws URISyntaxException {


        Socket mSocket = IO.socket("http://127.0.0.1:10101");
        //"http://192.168.1.23:10101";


        Joueur j = new Joueur("client à distance");

        Client c = new Client(mSocket, j);

        j.setClient(c);

        // changement de protocole, la connexion est reportée à un message "identification"
        // c.connnexion();
    }

    @Override
    public void call(Object... objects) {
        joueur.joue();
    }

    /**
     * méthode générique d'envoi de message (sans json) au serveur
     * @param msg le message (à connaitre par la classe utilisatrice) à envoyer
     */
    public void transfereMessage(String msg) {
        mSocket.emit(msg);
    }

    public void transfereMessage(String msg, ToJSON obj) {
        mSocket.emit(msg, obj.toJSON());
    }

    /*
    alternative à transfereMessage : dans ce cas, la classe utilisatrice ne connait pas le protocole d'échange
    entre le client et le serveur
    public void demanderÀLancerLesDé() {
        mSocket.emit("jouer");
    }
     */

}
