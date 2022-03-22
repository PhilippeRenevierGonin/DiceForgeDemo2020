package diceforge.moteur;


import diceforge.random.Random;

public class Dé {

    /**
     *
     */
    private final Random rand;

    /**
     * le Random est à l'extérieur car il est mieux qu'il n'y ait qu'une instance (mieux pour le pseudo aléatoire)
     *
     * @param rand
     */
    public Dé(Random rand) {
        this.rand = rand;
    }

    /**
     * dé qui retourne 2pts pour 5 faces ou 3 pts pour la dernière face
     * se base sur le random du proijet pour cela (si c'est 6, c'est 3pts sinon c'est 2pts)
     *
     * @return 2 ou 3 pts
     */
    public int lancer() {
        int aléa = rand.faceAleatoire();
        if (aléa == 6) return 3;
        else return 2;
    }
}
