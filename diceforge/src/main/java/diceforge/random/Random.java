package diceforge.random;

public class Random {

    private  java.util.Random rand ;

    public Random() {
        this(new java.util.Random());
    }


    public Random(java.util.Random random) {
        this.rand = random;
    }

    /**
     *
     * @return entre 1 et 6, un numéro de face
     */
    public int faceAleatoire() {
        return (rand.nextInt(6)+1);
    }

}
