package fr.perroquets.game4j;

public class Movement {

    private Case from;
    private Case to;

    public Movement(Case from, Case to) {
        this.from = from;
        this.to = to;
    }

    public Case getFrom() {
        return from;
    }

    public Case getTo() {
        return to;
    }
}
