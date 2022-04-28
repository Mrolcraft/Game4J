package fr.perroquets.game4j;

public class Movement {

    private Case from;
    private Case to;
    private int id;

    public Movement(int id, Case from, Case to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public Case getFrom() {
        return from;
    }

    public Case getTo() {
        return to;
    }
}
