package fr.perroquets.game4j;

import fr.perroquets.game4j.carte.Carte;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game4J {

    private static Game4J instance;
    private Game currentGame;
    private List<Game> allGames = new ArrayList<>();
    private boolean debug = false;

    public static void main(String[] args) {
        if(instance == null) {
            instance = new Game4J();
        }
    }

    public void launchGame() {
        System.out.println(" ");
        System.out.println("============================================");
        System.out.println("                MENU DU JEU");
        System.out.println(" ");
        System.out.println("1: Commencer une nouvelle partie");
        System.out.println("2: Reprendre une partie en cours");
        System.out.println("3: Voir l'historique des parties");
        System.out.println(" ");
        System.out.println("============================================");

        final Scanner scanner = new Scanner(System.in);
        final int entry = scanner.nextInt();
        if(entry == 1) {
            System.out.println("Vous avez décidé de commencer une nouvelle partie...");
            System.out.println("Génération de la partie...");
            final Random random = new Random();
            final int energy = random.nextInt(100-1)+1;
            this.currentGame = new Game("BLABLA", new Carte(Dimensions.SEPT_PAR_SEPT), new Personnage(energy, 0, 0, 6, 0, 0, 20, null), 0.3, 0.3, GameState.LOADING);
            this.currentGame.generateMap();
            this.currentGame.getPersonnage().setPosition(this.currentGame.getCarte().getCases().get(0));
            this.currentGame.getPersonnage().getPosition().setHidden(false);
            try {
                this.currentGame.saveGame();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            System.out.println("Début de la partie...");
            this.currentGame.setGameState(GameState.INGAME);
            this.currentGame.onGame();
        } else if(entry == 2) {
            System.out.println("Vous avez décidé de reprendre une partie en cours...");
        } else if(entry == 3) {
            System.out.println("Vous avez décidé de voir l'historique des parties...");
            try {
                allGames = Game.restoreAllGames();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            if(allGames.size() == 0) {
                System.out.println("L'historique est vide...");
            } else {
                System.out.println("Historique: ");
                for (Game game : this.allGames) {
                    System.out.println("Partie: " + game.getId() + " - " + game.getStartDateTime() + "/" + game.getEndDateTime() + " - " + game.getGameState().getToDisplay());
                }
            }
        } else {
            System.out.println("Aie.");
        }
    }

    private Game4J() {
        System.out.println("Bienvenue sur GAME4J 2000");
        try {
            final File saveFile = new File("save.json");
            if(!saveFile.exists()) {
                saveFile.createNewFile();
                final BufferedWriter writer = new BufferedWriter(new FileWriter("save.json"));
                writer.write("{\"games\":[]}");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final MainThread thread = new MainThread();
        thread.start();
    }

    public boolean isDebug() {
        return debug;
    }

    public Game getCurrentGame() {
        return this.currentGame;
    }

    public static Game4J getInstance() {
        return instance;
    }
}
