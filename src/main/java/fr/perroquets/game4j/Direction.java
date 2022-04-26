package fr.perroquets.game4j;

public enum Direction {

    NULL(0), NORTH(1), EAST(2), WEST(3), SOUTH(4);

    private int id;

    Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Direction getFromID(int id) {
        for (Direction direction : values()) {
            if(direction.getId() == id) return direction;
        }
        return null;
    }

}
