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

    public Case getNorth() {
        return Game4J.getInstance().getCarte().getCases().stream().filter(aCase ->
                (aCase.getPosition()[0] == this.x) && (aCase.getPosition()[1] == this.y + 1)
        ).findFirst().orElse(null);
    }

    public Case getSouth() {
        return Game4J.getInstance().getCarte().getCases().stream().filter(aCase ->
            (aCase.getPosition()[0] == this.x) && (aCase.getPosition()[1] == this.y - 1)
        ).findFirst().orElse(null);

    }

    public Case getWest() {
        return Game4J.getInstance().getCarte().getCases().stream().filter(aCase ->
                (aCase.getPosition()[0] == this.x -1) && (aCase.getPosition()[1] == this.y)
        ).findFirst().orElse(null);

    }

    public Case getEast() {
        return Game4J.getInstance().getCarte().getCases().stream().filter(aCase ->
                (aCase.getPosition()[0] == this.x + 1) && (aCase.getPosition()[1] == this.y)
        ).findFirst().orElse(null);
    }
    public double getDistance(Case caseo) {
        return (Math.sqrt(Math.pow(caseo.getPosition()[0]-this.x,2) + Math.pow(caseo.getPosition()[1]-this.y,2)));

    }
}





























