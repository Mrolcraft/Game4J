package fr.perroquets.game4j;

public enum CaseType {

    BLANK(1, "O"), WIN(2, "W"), BONUS(3, "B"), OBSTACLE(4, "X");

    private int id;
    private String toPrint;

    CaseType(int id, String toPrint) {
        this.id = id;
        this.toPrint = toPrint;
    }

    public String getToPrint() {
        return toPrint;
    }

    public int getId() {
        return id;
    }

    /**
     * Permet de récupérer {@CaseType} à partir de l'id
     * @param id
     * @return CaseType
     */
    public static CaseType getFromID(int id) {
        for (CaseType type : values()) {
            if(type.getId() == id) return type;
        }
        return BLANK;
    }
}
