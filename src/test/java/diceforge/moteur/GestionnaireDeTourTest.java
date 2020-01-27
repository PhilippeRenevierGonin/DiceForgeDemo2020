package diceforge.moteur;

import diceforge.joueur.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionnaireDeTourTest {

    @Mock
    Joueur j;

    GestionnaireDeTour moteur;

    @BeforeEach
    void setUp() {
        moteur = new GestionnaireDeTour(new Random());
        moteur = spy(moteur);
    }


    @Test
    void jouer() {
        ajouterJoueur();
        moteur.jouer();
                verify(j,times(1)).joue();
    }



    @Test
    void jouerAvecJoeurQuiAppelleLeMoteur() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                moteur.lanceMesDés();
                return null;
            }
        }).when(j).joue();

        ajouterJoueur();
        moteur.jouer();
        verify(j,times(1)).joue();
        verify(moteur,times(1)).lanceMesDés();
    }

    @Test
    void ajouterJoueur() {
        moteur.ajouterJoueur(j);
        verify(j,times(1)).setMoteur(moteur);
    }

    @Test
    void lanceMesDés() {
    }
}