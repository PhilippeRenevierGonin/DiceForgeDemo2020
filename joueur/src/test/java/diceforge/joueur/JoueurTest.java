package diceforge.joueur;

import diceforge.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static diceforge.echange.Protocole.DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JoueurTest {
    @Mock
    Client connexionVersMoteur;

    Joueur j;

    @BeforeEach
    void setUp() {
        j = new Joueur("test");
        j.setClient(connexionVersMoteur);
    }

    @Test
    void joue() {
        j.joue();
        verify(connexionVersMoteur, times(1)).transfereMessage(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS);
    }
}