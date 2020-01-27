package diceforge.moteur;

import java.util.Random;

public class Inventaire {

    private Random rand ;

    private Dé dé;
    private int points = 0;

    /**
     * le Random est à l'extérieur car il est mieux qu'il n'y ait qu'une instance (mieux pour le pseudo aléatoire)
     * @param rand
     */
    public Inventaire(Random rand) {
        this.rand = rand;
        dé = new Dé(this.rand);
    }


    public int lancerDés() {
        return dé.lancer();
    }

    public void ajouterPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
