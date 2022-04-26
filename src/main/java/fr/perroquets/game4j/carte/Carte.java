package fr.perroquets.game4j.carte;

import fr.perroquets.game4j.Case;
import fr.perroquets.game4j.Dimensions;
import fr.perroquets.game4j.Graph;
import fr.perroquets.game4j.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carte {

    private Dimensions dimensions;

    private List<Case> cases = new ArrayList<>();

    private List<Movement> best_energy = new ArrayList<>();
    private List<Movement> best_distance = new ArrayList<>();

    private int[][] matrix_distance;
    private int[][] matrix_energy;

    public Carte(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Carte(Dimensions dimensions, List<Case> cases, int[][] matrix_distance, int[][] matrix_energy) {
        this.dimensions = dimensions;
        this.cases = cases;
        this.matrix_distance = matrix_distance;
        this.matrix_energy = matrix_energy;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    /**
     * TODO:
     * Generate map
     */
    public void generateCarte() {
        this.cases.add(new Case(0, 0,0, 0));
        this.cases.add(new Case(1, 0, 1, 10));
        this.cases.add(new Case(2, 0, 2, -10));
        this.cases.add(new Case(3, 0, 3, 0));
        this.cases.add(new Case(0, 1,4, -10));
        this.cases.add(new Case(1, 1, 5, 10));
        this.cases.add(new Case(2, 1, 6, 0));
        this.cases.add(new Case(3, 1, 7, 0));
        this.cases.add(new Case(0, 2,8, 10));
        this.cases.add(new Case(1, 2,9, 0));
        this.cases.add(new Case(2, 2,10, 0));
        this.cases.add(new Case(3, 2,11, -10));
        this.cases.add(new Case(0, 3,12, 0));
        this.cases.add(new Case(1, 3,13, 0));
        this.cases.add(new Case(2, 3,14, -10));
        this.cases.add(new Case(3, 3,15, 0));


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
        final Graph g = new Graph(this.getDimensions().getArea());
        
        int a = 0;
        for (int i = 0; i < this.getDimensions().getArea(); i++) {
            if(a < this.getDimensions().getMax_loop()) {
                g.addEdge(i, i + 1);
                System.out.println("addEdge(" + i + "," + (i+1) + ")");
                a++;
            } else {
                a = 0;
            }
        }

        for (int i = 0; i <= Math.ceil(this.getDimensions().getArea()/2)+this.getDimensions().getMax_loop(); i++) {
            g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())));
            System.out.println("addEdge(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");
        }
        System.out.println(g);
        this.matrix_distance = g.getAdjMatrix();
        for (int i = 0; i < this.matrix_distance.length; i++) {
            for (int j = 0; j < this.matrix_distance[0].length; j++) {
                if(this.matrix_distance[i][j] == 0 && i != j) {
                    this.matrix_distance[i][j] = -1;
                }
            }
        }
    }

    public void generate_matrix_energy() {
        final Graph g = new Graph(this.getDimensions().getArea());

        int a = 0;
        for (int i = 0; i < this.getDimensions().getArea(); i++) {
            if(a < this.getDimensions().getMax_loop()) {
                final int finalI = i;
                final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);
                if(casez == null) {
                    System.out.println("Il y a un problème ");
                    return;
                }

                g.addEdge(i, i + 1, -1 + casez.getEnergy());
                System.out.println("addEdge(" + i + "," + (i+1) + ")");
                a++;
            } else {
                a = 0;
            }
        }

        for (int i = 0; i <= Math.ceil(this.getDimensions().getArea()/2)+this.getDimensions().getMax_loop(); i++) {
            final int finalI = i;
            final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);
            if(casez == null) {
                System.out.println("Il y a un problème");
                return;
            }
            g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())), -1 + casez.getEnergy());
            System.out.println("addEdge(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");
        }
        this.matrix_energy = g.getAdjMatrix();
        for (int i = 0; i < this.matrix_energy.length; i++) {
            for (int j = 0; j < this.matrix_energy[0].length; j++) {
                if(this.matrix_energy[i][j] == -1) {
                    this.matrix_energy[i][j] = 40;
                }
                if(this.matrix_energy[i][j] == -11) {
                    this.matrix_energy[i][j] = 50;
                }
                if(this.matrix_energy[i][j] == 0 && i != j) {
                    this.matrix_energy[i][j] = -1;
                }
            }
        }
        System.out.println(g);
    }

    public int[][] getMatrix_distance() {
        return matrix_distance;
    }

    public int[][] getMatrix_energy() {
        return matrix_energy;
    }

    public void generateBestPathWithDistance() {

    }
}
