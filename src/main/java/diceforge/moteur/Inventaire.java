package diceforge.moteur;

public class Inventaire {

    private Dé dé = new Dé();
    private int points = 0;


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
