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


    /**
     * lance le dé associé à cet inventaire
     * @return le nombre de points gangés
     */
    public int lancerDés() {
        return dé.lancer();
    }

    /**
     * permet de cumuler les points gagnés ou perdus
     * @param points le nombre de points à ajouter (>0) ou à retrancher (<0)
     */
    public void ajouterPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
