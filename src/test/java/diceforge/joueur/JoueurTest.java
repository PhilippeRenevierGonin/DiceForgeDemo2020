package diceforge.joueur;

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
    GestionnaireDeTour moteur;

    Joueur j;

    @BeforeEach
    void setUp() {
        j = new Joueur("test");
        j.setMoteur(moteur);
    }

    @Test
    void joue() {
        j.joue();
        verify(moteur, times(1)).lanceMesDés();
    }
}