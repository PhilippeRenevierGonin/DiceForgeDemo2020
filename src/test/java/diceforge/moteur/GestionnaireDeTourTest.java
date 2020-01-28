package diceforge.moteur;

import diceforge.joueur.Identité;
import diceforge.joueur.Joueur;
import diceforge.serveur.Serveur;
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
    Serveur s;

    GestionnaireDeTour moteur;

    @BeforeEach
    void setUp() {
        moteur = new GestionnaireDeTour(new Random());
        moteur.setServeur(s);
        moteur = spy(moteur);
    }


    @Test
    void jouer() {
        ajouterJoueur();
        moteur.jouer();
                verify(s,times(1)).transfereDemandeDeJouer();
    }



    @Test
    void jouerAvecJoeurQuiAppelleLeMoteur() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                moteur.lanceMesDés();
                return null;
            }
        }).when(s).transfereDemandeDeJouer();

        ajouterJoueur();
        moteur.jouer();
        verify(s,times(1)).transfereDemandeDeJouer();
        verify(moteur,times(1)).lanceMesDés();
    }


    void ajouterJoueur() {
        moteur.ajouterJoueur(new Identité("joueur test"));
    }

    @Test
    void lanceMesDés() {
    }
}