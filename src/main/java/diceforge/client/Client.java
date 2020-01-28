package diceforge.client;

import diceforge.joueur.Joueur;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class Client implements Emitter.Listener {

    private final Socket mSocket;
    private final Joueur joueur;

    public Client(Socket mSocket, Joueur joueur) {
        this.mSocket = mSocket;
        this.joueur = joueur;
        mSocket.on("àToiDeJoeur", this);


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

        c.connnexion();
    }

    @Override
    public void call(Object... objects) {
        joueur.joue();
    }

    public void transfereMessage(String lancerDé) {
        mSocket.emit(lancerDé);
    }
}
