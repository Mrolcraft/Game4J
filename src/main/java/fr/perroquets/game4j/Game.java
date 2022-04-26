package fr.perroquets.game4j;

import fr.perroquets.game4j.carte.Carte;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private Carte carte;
    private Personnage personnage;
    private GameState gameState;
    private String id;
    private String startDateTime;
    private String endDateTime;

    public Game(String id, Carte carte, Personnage personnage, GameState gameState) {
        this.carte = carte;
        this.id = id;
        this.personnage = personnage;
        this.gameState = gameState;
        this.startDateTime = "bla";
        this.endDateTime = "bla";
    }

    public Game(Carte carte, Personnage personnage, GameState gameState, String id, String startDate, String endDate) {
        this.carte = carte;
        this.personnage = personnage;
        this.gameState = gameState;
        this.id = id;
        this.startDateTime = startDate;
        this.endDateTime = endDate;
    }

    public void onGame() {
        while(this.gameState == GameState.INGAME) {

        }
    }

    public void saveGame() throws IOException, ParseException {
        final JSONParser jsonParser = new JSONParser();
        org.json.JSONObject json;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("save.json"), "UTF-8"))) {
            final Object obj = jsonParser.parse(reader);
            final JSONObject jsonObject = (JSONObject) obj;
            json = new org.json.JSONObject(jsonObject.toString());
        }

        final org.json.JSONObject gameParsed = parseGame();
        int index = -1;
        for (int i = 0; i < json.getJSONArray("games").length(); i++) {
            if(json.getJSONArray("games").getJSONObject(i).getString("id").equals(this.getId())) {
                index = i;
                return;
            }
        }

        if(index != -1) {
            json.getJSONArray("games").remove(index);
        }
        json.getJSONArray("games").put(gameParsed);
        final BufferedWriter writer = new BufferedWriter(new FileWriter("save.json"));
        writer.write(json.toString());
        writer.close();
    }

    private org.json.JSONObject parseGame() {
        final org.json.JSONObject jsonObject = new org.json.JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("state", this.getGameState().getId());
        final org.json.JSONObject personnageObject = new org.json.JSONObject();
        personnageObject.put("initialEnergy", this.getPersonnage().getInitialEnergy());
        personnageObject.put("lostEnergy", this.getPersonnage().getLostEnergy());
        personnageObject.put("wonEnergy", this.getPersonnage().getWonEnergy());
        personnageObject.put("currentEnergy", this.getPersonnage().getCurrentEnergy());
        personnageObject.put("maxUndoCount", this.getPersonnage().getMaxUndoCount());
        personnageObject.put("distance", this.getPersonnage().getDistance());
        personnageObject.put("currentUndoCount", this.getPersonnage().getCurrentUndoCount());
        personnageObject.put("direction", this.getPersonnage().getDirection().getId());
        personnageObject.put("position", this.getPersonnage().getPosition().getId());
        final JSONArray history = new JSONArray();
        this.getPersonnage().getHistory().forEach(mvt -> {
            final org.json.JSONObject mvtJson = new org.json.JSONObject();
            mvtJson.put("from", mvt.getFrom().getId());
            mvtJson.put("to", mvt.getTo().getId());
            history.put(mvtJson);
        });
        personnageObject.put("history", history);
        jsonObject.put("personnage", personnageObject);
        final org.json.JSONObject mapObject = new org.json.JSONObject();
        mapObject.put("dimensions", this.getCarte().getDimensions().getArea());
        final JSONArray cases = new JSONArray();
        this.getCarte().getCases().forEach(c -> {
            final org.json.JSONObject caseObject = new org.json.JSONObject();
            caseObject.put("x", c.getPosition()[0]);
            caseObject.put("y", c.getPosition()[1]);
            caseObject.put("id", c.getId());
            caseObject.put("energy", c.getEnergy());
            cases.put(caseObject);
        });
        mapObject.put("cases", cases);
        final JSONArray matrix_distance = new JSONArray();
        for (int i = 0; i < this.getCarte().getMatrix_distance().length; i++) {
            final JSONArray line = new JSONArray();
            for (int j = 0; j < this.getCarte().getMatrix_distance()[0].length; j++) {
                line.put(this.getCarte().getMatrix_distance()[i][j]);
            }
            matrix_distance.put(line);
        }
        mapObject.put("matrix_distance", matrix_distance);
        final JSONArray matrix_energy = new JSONArray();
        for (int i = 0; i < this.getCarte().getMatrix_energy().length; i++) {
            final JSONArray line = new JSONArray();
            for (int j = 0; j < this.getCarte().getMatrix_energy()[0].length; j++) {
                line.put(this.getCarte().getMatrix_energy()[i][j]);
            }
            matrix_energy.put(line);
        }
        mapObject.put("matrix_energy", matrix_energy);
        jsonObject.put("map", mapObject);
        jsonObject.put("start_date", this.getStartDateTime());
        jsonObject.put("end_date", this.getEndDateTime());
        return jsonObject;
    }

    public static Game restoreGame(String id) throws IOException, ParseException {
        final JSONParser jsonParser = new JSONParser();
        org.json.JSONObject json;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("save.json"), "UTF-8"))) {
            final Object obj = jsonParser.parse(reader);
            final JSONObject jsonObject = (JSONObject) obj;
            json = new org.json.JSONObject(jsonObject.toString());
        }
        org.json.JSONObject game = null;
        for (int i = 0; i < json.getJSONArray("games").length(); i++) {
            if(json.getJSONArray("games").getJSONObject(i).getString("id").equals(id)) {
                game = json.getJSONArray("games").getJSONObject(i);
            }
        }

        if(game == null) return null;
        final org.json.JSONObject mapObject = game.getJSONObject("map");
        final int dimensions = mapObject.getInt("dimensions");
        final List<Case> cases = new ArrayList<>();
        for (int i = 0; i < mapObject.getJSONArray("cases").length(); i++) {
            cases.add(new Case(mapObject.getInt("x"), mapObject.getInt("y"), mapObject.getInt("id"), mapObject.getInt("energy")));
        }
        int[][] matrix_distance = new int[dimensions][dimensions];
        int[][] matrix_energy = new int[dimensions][dimensions];
        final JSONArray distanceJSON = mapObject.getJSONArray("matrix_distance");
        for (int i = 0; i < distanceJSON.length(); i++) {
            for (int j = 0; j < distanceJSON.getJSONArray(0).length(); j++) {
                matrix_distance[i][j] = distanceJSON.getJSONArray(i).getInt(j);
            }
        }
        final JSONArray energyJSON = mapObject.getJSONArray("matrix_energy");
        for (int i = 0; i < energyJSON.length(); i++) {
            for (int j = 0; j < energyJSON.getJSONArray(0).length(); j++) {
                matrix_energy[i][j] = energyJSON.getJSONArray(i).getInt(j);
            }
        }
        final Carte carte = new Carte(Dimensions.getFromArea(dimensions), cases, matrix_distance, matrix_energy);

        final org.json.JSONObject personnageObject = game.getJSONObject("personnage");
        final int initialEnergy = personnageObject.getInt("initialEnergy");
        final int lostEnergy = personnageObject.getInt("lostEnergy");
        final int wonEnergy = personnageObject.getInt("wonEnergy");
        final int currentEnergy = personnageObject.getInt("currentEnergy");
        final int maxUndoCount = personnageObject.getInt("maxUndoCount");
        final int distance = personnageObject.getInt("distance");
        final int currentUndoCount = personnageObject.getInt("currentUndoCount");
        final Direction direction = Direction.getFromID(personnageObject.getInt("direction"));
        final Case position = cases.stream().filter(c -> c.getId() == personnageObject.getInt("position")).findFirst().orElse(null);
        final List<Movement> history = new ArrayList<>();
        for (int i = 0; i < personnageObject.getJSONArray("history").length(); i++) {
            final org.json.JSONObject mvtObject = personnageObject.getJSONArray("history").getJSONObject(i);
            history.add(new Movement(cases.stream().filter(c -> c.getId() == mvtObject.getInt("from")).findFirst().orElse(null), cases.stream().filter(c -> c.getId() == mvtObject.getInt("to")).findFirst().orElse(null)));
        }

        final Personnage personnage = new Personnage(initialEnergy, lostEnergy, wonEnergy, currentEnergy, maxUndoCount, distance, currentUndoCount, direction, position, history);
        return new Game(carte, personnage, GameState.getFromID(game.getInt("state")), id, game.getString("start_date"), game.getString("end_date"));
    }

    public static List<Game> restoreAllGames() throws IOException, ParseException{
        final JSONParser jsonParser = new JSONParser();
        org.json.JSONObject json;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("save.json"), "UTF-8"))) {
            final Object obj = jsonParser.parse(reader);
            final JSONObject jsonObject = (JSONObject) obj;
            json = new org.json.JSONObject(jsonObject.toString());
        }

        org.json.JSONObject game = null;
        final List<Game> games = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("games").length(); i++) {
            game = json.getJSONArray("games").getJSONObject(i);
            final org.json.JSONObject mapObject = game.getJSONObject("map");
            final int dimensions = mapObject.getInt("dimensions");
            final List<Case> cases = new ArrayList<>();
            for (int j = 0; j < mapObject.getJSONArray("cases").length(); j++) {
                cases.add(new Case(mapObject.getJSONArray("cases").getJSONObject(j).getInt("x"), mapObject.getJSONArray("cases").getJSONObject(j).getInt("y"), mapObject.getJSONArray("cases").getJSONObject(j).getInt("id"), mapObject.getJSONArray("cases").getJSONObject(j).getInt("energy")));
            }
            int[][] matrix_distance = new int[dimensions][dimensions];
            int[][] matrix_energy = new int[dimensions][dimensions];
            final JSONArray distanceJSON = mapObject.getJSONArray("matrix_distance");
            for (int j = 0; j < distanceJSON.length(); j++) {
                for (int h = 0; h < distanceJSON.getJSONArray(0).length(); h++) {
                    matrix_distance[j][h] = distanceJSON.getJSONArray(j).getInt(h);
                }
            }
            final JSONArray energyJSON = mapObject.getJSONArray("matrix_energy");
            for (int j = 0; j < energyJSON.length(); j++) {
                for (int h = 0; h < energyJSON.getJSONArray(0).length(); h++) {
                    matrix_energy[j][h] = energyJSON.getJSONArray(j).getInt(h);
                }
            }
            final Carte carte = new Carte(Dimensions.getFromArea(dimensions), cases, matrix_distance, matrix_energy);

            final org.json.JSONObject personnageObject = game.getJSONObject("personnage");
            final int initialEnergy = personnageObject.getInt("initialEnergy");
            final int lostEnergy = personnageObject.getInt("lostEnergy");
            final int wonEnergy = personnageObject.getInt("wonEnergy");
            final int currentEnergy = personnageObject.getInt("currentEnergy");
            final int maxUndoCount = personnageObject.getInt("maxUndoCount");
            final int distance = personnageObject.getInt("distance");
            final int currentUndoCount = personnageObject.getInt("currentUndoCount");
            final Direction direction = Direction.getFromID(personnageObject.getInt("direction"));
            final Case position = cases.stream().filter(c -> c.getId() == personnageObject.getInt("position")).findFirst().orElse(null);
            final List<Movement> history = new ArrayList<>();
            for (int j = 0; j < personnageObject.getJSONArray("history").length(); j++) {
                final org.json.JSONObject mvtObject = personnageObject.getJSONArray("history").getJSONObject(j);
                history.add(new Movement(cases.stream().filter(c -> c.getId() == mvtObject.getInt("from")).findFirst().orElse(null), cases.stream().filter(c -> c.getId() == mvtObject.getInt("to")).findFirst().orElse(null)));
            }

            final Personnage personnage = new Personnage(initialEnergy, lostEnergy, wonEnergy, currentEnergy, maxUndoCount, distance, currentUndoCount, direction, position, history);
            games.add(new Game(carte, personnage, GameState.getFromID(game.getInt("state")), game.getString("id"), game.getString("start_date"), game.getString("end_date")));
        }
        return games;
    }

    public void generateMap() {
        this.gameState = GameState.LOADING;
        this.getCarte().generateCarte();
    }

    public void pause() {
        this.gameState = GameState.PAUSED;
    }

    public void unpause() {
        this.gameState = GameState.INGAME;
    }

    public Carte getCarte() {
        return carte;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getId() {
        return id;
    }
}
