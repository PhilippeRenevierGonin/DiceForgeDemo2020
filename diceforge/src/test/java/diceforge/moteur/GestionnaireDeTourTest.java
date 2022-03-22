package diceforge.moteur;

import diceforge.joueur.Identité;
import diceforge.random.Random;
import diceforge.serveur.Serveur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;


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
        ajouterJoueur("premier");
        verify(s,times(0)).transfereDemandeDeJouer(any());
        ajouterJoueur("second");
        verify(s,times(2)).transfereDemandeDeJouer(any());


    }



    @Test
    void jouerAvecJoeurQuiAppelleLeMoteur() {
        Identité id1 = new Identité("toto");
        Identité id2 = new Identité("tutu");

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                moteur.lanceMesDés(0);
                return null;
            }
        }).when(s).transfereDemandeDeJouer(id1);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                moteur.lanceMesDés(1);
                return null;
            }
        }).when(s).transfereDemandeDeJouer(id2);

        ajouterJoueur("toto");
        ajouterJoueur("tutu");
        verify(s,times(1)).transfereDemandeDeJouer(id1);
        verify(s,times(1)).transfereDemandeDeJouer(id2);
        verify(moteur,times(1)).lanceMesDés(0);
        verify(moteur,times(1)).lanceMesDés(1);
    }


    void ajouterJoueur(String name) {
        moteur.ajouterJoueur(new Identité(name));
    }

    @Test
    void lanceMesDés() {
    }


}