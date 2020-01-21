package diceforge;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class UnePartie {

    public static final void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("début de la partie");
    }
}
