package diceforge.serveur;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import diceforge.joueur.Identité;
import diceforge.moteur.GestionnaireDeTour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static diceforge.echange.Protocole.DEMANDER_AU_JOUEUR_DE_JOUER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServeurTest {

    @Mock
    SocketIOClient client;

    @Mock
    Random rand;

    GestionnaireDeTour moteur;
    Serveur serveur;

    @BeforeEach
    void setUp() {
        /*
        on crée un vrai "serveur" qui ne sera pas utilisé, il faudrait réorganiser le code pour simplifier le test
         */
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        moteur = new GestionnaireDeTour(rand);

        serveur = new Serveur(config, moteur);

        moteur.setServeur(serveur);

    }

    @Test
    void receptionNouveauJoueur() {
        Identité id = new Identité();

        serveur.receptionNouveauJoueur(client, id);

        // on pourrait vérifier "monClient" dans Serveur et identité dans GestionnaireDeTour (nécessite getter)
        verify(client,times(1)).sendEvent(DEMANDER_AU_JOUEUR_DE_JOUER);
    }
}