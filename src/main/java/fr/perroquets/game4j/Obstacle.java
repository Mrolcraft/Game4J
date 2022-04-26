package fr.perroquets.game4j;

public class Obstacle extends Case {

    private String type;

    public Obstacle(int x, int y, int id, int energy, String type){
        super(x,y,id, energy);
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
