package diceforge.joueur;

import diceforge.echange.ToJSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.ImageGraphicAttribute;

public class Identité implements ToJSON {
    private String nom;

    public Identité() {
        this("sans nom");
    }

    public Identité(String nom) {
        setNom(nom);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }


    public String toString() {
        return getNom();
    }



    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Identité) {
            Identité id = (Identité) o;
            result = id.getNom().equals(getNom());
        }

        return result; // bug ; return false;
    }


    @Override
    public JSONObject toJSON() {
        JSONObject obj =  new JSONObject();
        try {
            obj.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
