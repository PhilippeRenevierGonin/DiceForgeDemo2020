package diceforge.client;

import diceforge.joueur.Joueur;
import io.socket.client.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static diceforge.echange.Protocole.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientTest {

    @Mock
    Socket mockDeSocket;

    ClientSocketIO objetTesté;
    Joueur joueur;
    Joueur spyDuJoueur;

    @BeforeEach
    void init() {
        InOrder ordre = inOrder(mockDeSocket);
        joueur = new Joueur("joueur test");
        spyDuJoueur = spy(joueur);
        objetTesté=new ClientSocketIO(mockDeSocket, spyDuJoueur);

        ordre.verify(mockDeSocket, times(1)).on(DEMANDER_AU_JOUEUR_DE_JOUER, objetTesté);
        ordre.verify(mockDeSocket, times(1)).on(eq("disconnect"), any());

        spyDuJoueur.setClient(objetTesté);

        ordre.verify(mockDeSocket, times(1)).connect();
        ordre.verify(mockDeSocket, times(1)).emit(eq(IDENTIFICATION), any());

    }


    @Test
    void call() {
        // InOrder ordre = inOrder(mockDeSocket);

        // methode que je teste
        objetTesté.call();

        // verify ou assert... liés à l'appel de la méthode ci-dessus
        verify(spyDuJoueur, times(1)).joue();
        // si order : ordre.verify(mockDeSocket, times(1)).emit(eq(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS), any());
        verify(mockDeSocket, times(1)).emit(eq(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS), any());

    }
}