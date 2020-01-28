package diceforge.joueur;

public class Identité {
    private String nom;

    public Identité(String nom) {
        setNom(nom);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

}
