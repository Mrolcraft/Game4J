package fr.perroquets.game4j;

public class Case {

    private int x;
    private int y;
    private int id;
    private int energy;
    private CaseType caseType;
    private boolean hidden;

    public Case(int x, int y, int id, int energy, CaseType caseType, boolean hidden) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.energy = energy;
        this.caseType = caseType;
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    /**
     * Permet de récupérer la position de la case
     * @return int[]
     */
    public int[] getPosition() {
        int[] position = new int[2];
        position[0] = this.x;
        position[1] = this.y;

        return position;

    }

    public int getId() {
        return id;
    }

    /**
     * Permet de récupérer le voisin nord de la case actuelle
     * @return Case
     */
    public Case getNorth() {
        return Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(aCase ->
                (aCase.getId() == this.id-Game4J.getInstance().getCurrentGame().getCarte().getDimensions().getSize())
        ).findFirst().orElse(null);
    }

    /**
     * Permet de récupérer le voisin Sud de la case actuelle
     * @return Case
     */
    public Case getSouth() {
        return Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(aCase ->
            (aCase.getId() == this.id+Game4J.getInstance().getCurrentGame().getCarte().getDimensions().getSize())
        ).findFirst().orElse(null);

    }

    /**
     * Permet de récupérer le voisin Ouest de la case actuelle
     * @return Case
     */
    public Case getWest() {
        return Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(aCase ->
                (aCase.getId() == this.id - 1)
        ).findFirst().orElse(null);

    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Permet de récupérer le voisin Est de la case actuelle
     * @return Case
     */
    public Case getEast() {
        return Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(aCase ->
                (aCase.getId() == this.id + 1)
        ).findFirst().orElse(null);
    }
}





























