package diceforge.client;

import diceforge.echange.ToJSON;
import diceforge.joueur.Joueur;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.EngineIOException;

import java.net.URISyntaxException;

import static diceforge.echange.Protocole.DEMANDER_AU_JOUEUR_DE_JOUER;

public class ClientSocketIO implements Emitter.Listener, Client {

    private final Socket mSocket;
    private final Joueur joueur;

    public ClientSocketIO(Socket mSocket, Joueur joueur) {
        this.mSocket = mSocket;
        this.joueur = joueur;
        mSocket.on(DEMANDER_AU_JOUEUR_DE_JOUER, this);
        mSocket.on("disconnect", (objects) -> {
            terminer();
        });


    }

    public void connnexion() {


        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("************ EVENT_CONNECT_ERROR ***************" + args); // not authorized
                System.out.println(args.length); // not authorized
                System.out.println(args[0]); // not authorized
               if (args[0] instanceof EngineIOException) {
                   EngineIOException error = (EngineIOException) args[0];
                   error.printStackTrace();
               }
            }
        });

        mSocket.on("connect", new Emitter.Listener() {
                    @Override
                    public void call(Object... objects) {
                        System.out.println("************ connect ***************" + objects); // not authorized

                    }
                });

                mSocket.connect();
    }

    public final static void main(String [] args) throws URISyntaxException {

        String nom ="client à distance";
        if (args.length >= 1) nom = args[0];
        Socket mSocket = IO.socket("http://127.0.0.1:10101");
        //"http://192.168.1.23:10101";


        Joueur j = new Joueur(nom);

        ClientSocketIO c = new ClientSocketIO(mSocket, j);

        j.setClient(c);

        // changement de protocole, la connexion est reportée à un message "identification"
        // c.connnexion();
    }

    @Override
    public void call(Object... objects) {
        System.out.println(joueur.getIdentité()+" > on me demande de jouer");
        joueur.joue();
    }

    /**
     * méthode générique d'envoi de message (sans json) au serveur
     * @param msg le message (à connaitre par la classe utilisatrice) à envoyer
     */
    @Override
    public void transfereMessage(String msg) {
        mSocket.emit(msg);
    }

    @Override
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


    public void terminer() {


        // pour ne pas être sur le thread de SocketIO
        new Thread(new Runnable() {

            @Override
            public void run() {
                mSocket.off(DEMANDER_AU_JOUEUR_DE_JOUER);
                mSocket.off("disconnect");
                mSocket.close();
                System.out.println("@todo >>>> c'est fini");
                // hack pour arrêter plus vite (sinon attente de plusieurs secondes
                // à ne pas faire si on veut lancer 500 parties
                System.exit(0);
            }
        }).start() ;



    }

}
