package diceforge.moteur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventaireTest {
    Inventaire inventaire;

    @BeforeEach
    void setUp() {
        inventaire = new Inventaire();
    }

    @Test
    void lancerDés() {
        assertEquals(2, inventaire.lancerDés());
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