package diceforge.moteur;

import diceforge.joueur.Identité;
import diceforge.random.Random;
import diceforge.serveur.Serveur;

import java.util.ArrayList;

import static diceforge.echange.Protocole.NB_JOUEURS;

public class GestionnaireDeTour {
    private ArrayList<Identité> joueurs = new ArrayList();
    private ArrayList<Inventaire> inventaires= new ArrayList();
    private Random rand;
    private Serveur serveur;

    int nbJoueursAyantJouéCeTour = 0;

    public GestionnaireDeTour(Random rand) {
        this.rand = rand;
    }

    public synchronized void  jouerUnTour() {
        System.out.println("nb j = "+joueurs.size() );
        nbJoueursAyantJouéCeTour = 0;
        if ((joueurs.size() == NB_JOUEURS) && (inventaires.size() == NB_JOUEURS)) {
            System.out.println(" j 0 = "+joueurs.get(0));
            serveur.transfereDemandeDeJouer(joueurs.get(0));
            System.out.println(" j 1 = "+joueurs.get(1));

            serveur.transfereDemandeDeJouer(joueurs.get(1));
        }

    }

    private void déterminerGagnant() {
        if (inventaires.get(0).getPoints() > inventaires.get(1).getPoints())

            System.out.println(joueurs.get(0).getNom()+" gagne");
        else
            System.out.println(joueurs.get(1).getNom()+" gagne");
    }

    private void afficherScore() {
        for(int i  = 0; i < NB_JOUEURS; i++)
        System.out.println(joueurs.get(i).getNom() +" a "+inventaires.get(i).getPoints()+" points ");
    }

    public synchronized void ajouterJoueur(Identité joueurIdentité) {
        System.out.println("ajouterJoueur" + joueurIdentité);
        if (joueurs.size() < NB_JOUEURS)
        {
            this.joueurs.add(joueurIdentité);
            this.inventaires.add(new Inventaire(this.rand));

        }
        if (joueurs.size() == NB_JOUEURS) jouerUnTour();
    }

    /**
     * lance les dés pour le joueur, comptabilise ses points ainsi gagné et les affiche
     */
    public synchronized void lanceMesDés(int i) {
        int points = inventaires.get(i).lancerDés();
        inventaires.get(i).ajouterPoints(points);
        System.out.println(joueurs.get(i).getNom()+" lance les dés et obtient "+points+" points");
        nbJoueursAyantJouéCeTour++;

        if(nbJoueursAyantJouéCeTour == NB_JOUEURS) finirTour();

    }



    public synchronized void finirTour() {
        // déplacer ici car asynchrone
        afficherScore();
        déterminerGagnant();

        // à faire que si on veut arrêter le serveur après une partie
        // à ne pas faire si on veut lancer 500 parties
        serveur.terminer();
    }


    /**
     * Modificateur du serveur réseau (pour communiquer avec les joueurs-clients).
     * @param serveur l'élément gérant le réseau
     */
    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public Serveur getServeur() {
        return serveur;
    }
}

