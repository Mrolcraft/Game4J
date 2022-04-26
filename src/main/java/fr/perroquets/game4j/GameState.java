package fr.perroquets.game4j;

public enum GameState {

    LOADING(0, "En chargement..."), INGAME(1, "En jeu"), PAUSED(2, "Interrompue"), FINISHED(3, "Terminee");

    private int id;
    private String toDisplay;

    GameState(int id, String toDisplay) {
        this.id = id;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public int getId() {
        return id;
    }

    public static GameState getFromID(int id) {
        for (GameState gameState : values()) {
            if(gameState.getId() == id) return gameState;
        }
        return null;
    }
}
