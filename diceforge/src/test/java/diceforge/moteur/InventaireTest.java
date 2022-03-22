package diceforge.moteur;

import diceforge.random.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventaireTest {
    Inventaire inventaire;
    @Mock
    Random rand;

    @BeforeEach
    void setUp() {
        inventaire = new Inventaire(rand);
    }

    @Test
    void lancerDés() {
        when(rand.faceAleatoire()).thenReturn(2, 2,3, 4, 1,5, 2, 3);
        for(int i = 0; i < 8; i++) assertEquals(2, inventaire.lancerDés());
    }


    @Test
    void lancerDésSurLaFace3pts() {
        when(rand.faceAleatoire()).thenReturn(6);
        for(int i = 0; i < 8; i++) assertEquals(3, inventaire.lancerDés());
    }


    @Test
    void ajouterPointsPositifs() {
        assertEquals(0, inventaire.getPoints());
        inventaire.ajouterPoints(5);
        assertEquals(5, inventaire.getPoints());
        inventaire.ajouterPoints(3);
        assertEquals(8, inventaire.getPoints());
    }

    @Test
    void ajouterPointsNégatif() {
        assertEquals(0, inventaire.getPoints());
        inventaire.ajouterPoints(-5);
        assertEquals(-5, inventaire.getPoints());
        inventaire.ajouterPoints(-3);
        assertEquals(-8, inventaire.getPoints());
    }

    @Test
    void ajouterPointsPositifsEtNégatifs() {
        assertEquals(0, inventaire.getPoints());
        inventaire.ajouterPoints(5);
        assertEquals(5, inventaire.getPoints());
        inventaire.ajouterPoints(-3);
        assertEquals(2, inventaire.getPoints());
    }


    @Test
    void ajouterPoints0() {
        assertEquals(0, inventaire.getPoints());
        inventaire.ajouterPoints(0);
        assertEquals(0, inventaire.getPoints());
    }
}