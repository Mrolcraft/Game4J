package fr.perroquets.game4j;

import fr.perroquets.game4j.carte.Carte;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game4J {

    private static Game4J instance;
    private Game currentGame;
    private List<Game> allGames = new ArrayList<>();

    public static void main(String[] args) {
        if(instance == null) {
            instance = new Game4J();
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
            this.currentGame = new Game("BLABLABLA", new Carte(Dimensions.QUATRE_PAR_QUATRE), new Personnage(20, 0, 0, 6, 0, 0, 20, null), GameState.LOADING);
            this.currentGame.generateMap();
            this.currentGame.getCarte().generate_matrix_distance();
            this.currentGame.getCarte().generate_matrix_energy();
            this.currentGame.getPersonnage().setPosition(this.currentGame.getCarte().getCases().get(0));
            try {
                this.currentGame.saveGame();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            final Dijkstra dijkstra = new Dijkstra();
            List<Integer> path = dijkstra.getPath(this.currentGame.getCarte().getMatrix_distance(), 0, 15);
            System.out.println("Best path in distance");
            for (Integer i: path) {
                System.out.println(i);
            }
            final DijkstraEnergy dijkstraEnergy = new DijkstraEnergy();
            List<Integer> pathEnergy = dijkstraEnergy.getPath(this.currentGame.getCarte().getMatrix_energy(), 0, 15);
            System.out.println("Best path in energy");
            for (Integer i: pathEnergy) {
                System.out.println(i);
            }
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

    public Game getCurrentGame() {
        return this.currentGame;
    }

    public static Game4J getInstance() {
        return instance;
    }
}
