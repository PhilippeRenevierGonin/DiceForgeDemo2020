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

public interface Serveur {

    void  transfereDemandeDeJouer(Identité id) ;
    void terminer() ;
}
