package fr.perroquets.game4j;

import fr.perroquets.game4j.carte.Carte;

public class Game4J {

    private static Game4J instance;
    private Carte carte;

    public static void main(String[] args) {
        if(instance == null) {
            instance = new Game4J();
        }
    }

    private Game4J() {
        this.carte = new Carte(64);
    }

    public Carte getCarte() {
        return carte;
    }

    public static Game4J getInstance() {
        return instance;
    }
}
