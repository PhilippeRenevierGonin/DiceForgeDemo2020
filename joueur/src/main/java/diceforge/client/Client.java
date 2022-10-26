package diceforge.client;

import diceforge.echange.ToJSON;

public interface Client {
    void transfereMessage(String msg);

    void transfereMessage(String msg, ToJSON obj);

    void connnexion();
}
