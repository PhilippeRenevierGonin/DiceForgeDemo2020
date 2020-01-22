package diceforge.moteur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DéTest {
    Dé dé;

    @BeforeEach
    void setUp() {
        dé = new Dé();
    }

    @Test
    void lancer() {
        assertEquals(2, dé.lancer(), "au début, le dé renvoie toujours 2");
    }
}