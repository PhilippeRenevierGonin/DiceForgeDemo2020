package diceforge.joueur;

import diceforge.client.Client;

import static diceforge.echange.Protocole.DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS;
import static diceforge.echange.Protocole.IDENTIFICATION;

public class Joueur {


    private Identité identité;
    private Client client;


    public Joueur(String nom) {
        setIdentité(new Identité(nom));
    }


    public void joue() {
        client.transfereMessage(DEMANDER_AU_SERVEUR_DE_LANCER_LES_DÉS);
    }

    public void setIdentité(Identité identité) {
        this.identité = identité;
    }

    public Identité getIdentité() {
        return identité;
    }

    public void setClient(Client client) {
        this.client = client;
        this.client.connnexion();
        this.client.transfereMessage(IDENTIFICATION, getIdentité());
    }

    public Client getClient() {
        return client;
    }
}
