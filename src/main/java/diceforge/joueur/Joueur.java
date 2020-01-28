package diceforge.joueur;

import diceforge.client.Client;
import diceforge.moteur.GestionnaireDeTour;

public class Joueur {


    private Identité identité;
    private Client client;


    public Joueur(String nom) {
        setIdentité(new Identité(nom));
    }


    public void joue() {
        client.transfereMessage("jouer");
    }

    public void setIdentité(Identité identité) {
        this.identité = identité;
    }

    public Identité getIdentité() {
        return identité;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
