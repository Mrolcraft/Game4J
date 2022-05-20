package fr.perroquets.game4j;

import fr.perroquets.game4j.carte.Carte;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
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
    private GameFrame gameFrame;
    private LoadingFrame loadingFrame;


    public static void main(String[] args) {
        if(instance == null) {
            instance = new Game4J();
        }
    }

    public void launchGame() {
        /*System.out.println(" ");
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

        } else if(entry == 2) {
            System.out.println("Vous avez decide de reprendre une partie en cours...");
        } else if(entry == 3) {
            System.out.println("Vous avez decide de voir l'historique des parties...");
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
        }*/

        final Menu menu = new Menu();
        menu.setVisible(true);
    }

    public void initNewGame() {
        final Random random = new Random();
        final int energy = random.nextInt(100-1)+1;
        this.currentGame = new Game(this.generateID(), new Carte(Dimensions.CINQ_PAR_CINQ), new Personnage(energy, 0, 0, 6, 0, 0, 20, null), 0.3, 0.3, GameState.LOADING);
        this.loadingFrame.setProgress(10);
        this.currentGame.generateMap();
        if(this.currentGame.getCarte().getBestPathInDistance() == null || this.currentGame.getCarte().getBestPathInDistance().size() == 0) {
            initNewGame();
            return;
        }
        this.loadingFrame.setProgress(70);
        this.currentGame.getPersonnage().setPosition(this.currentGame.getCarte().getCases().get(0));
        this.currentGame.getPersonnage().getPosition().setHidden(false);
        this.currentGame.setGameState(GameState.INGAME);
        try {
            this.currentGame.saveGame();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.loadingFrame.setProgress(100);
        System.out.println("Debut de la partie...");
        this.loadingFrame.setVisible(false);
        final GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        this.gameFrame = gameFrame;
        final GameThread gameThread = new GameThread();
        gameThread.start();
    }

    private String generateID() {
        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder();
        final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 10; i++) {
            final int index = random.nextInt(35);
            stringBuilder.append(letters.toCharArray()[index]);
        }
        return stringBuilder.toString();
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

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public static Game4J getInstance() {
        return instance;
    }

    public LoadingFrame getLoadingFrame() {
        return loadingFrame;
    }

    public void setLoadingFrame(LoadingFrame loadingFrame) {
        this.loadingFrame = loadingFrame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
