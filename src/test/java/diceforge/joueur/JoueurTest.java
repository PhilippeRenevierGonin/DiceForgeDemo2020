package diceforge.joueur;

import diceforge.client.Client;
import diceforge.moteur.GestionnaireDeTour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JoueurTest {
    @Mock
    Client moteur;

    Joueur j;

    @BeforeEach
    void setUp() {
        j = new Joueur("test");
        j.setClient(moteur);
    }

    @Test
    void joue() {
        j.joue();
        verify(moteur, times(1)).transfereMessage("jouer");
    }
}