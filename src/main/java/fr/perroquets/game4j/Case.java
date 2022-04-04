package fr.perroquets.game4j;

public class Case {
    private int x;
    private int y;
    private int id;

    public Case(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int[] getPosition() {
        int[] position = new int[2];
        position[0] = this.x;
        position[1] = this.y;

        return position;

    }

    
}
