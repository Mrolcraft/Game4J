package fr.perroquets.game4j.carte;

import fr.perroquets.game4j.Case;

import java.util.ArrayList;
import java.util.List;

public class Carte {

    private int dimensions;

    private List<Case> cases = new ArrayList<>();

    public Carte(int dimensions) {
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return dimensions;
    }

    /**
     * TODO:
     * Generate map
     */
    public void generateCarte() {

    }

    public List<Case> getCases() {
        return cases;
    }

    public void afficherCarte() {

    }

    public void addCase() {

    }

    public void removeCase() {

    }

    public void generate_matrix_distance() {

    }

    public void generate_matrix_energie() {

    }

}
