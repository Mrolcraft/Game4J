package fr.perroquets.game4j.carte;import fr.perroquets.game4j.*;import java.util.ArrayList;import java.util.List;import java.util.Random;public class Carte {    private Dimensions dimensions;    private List<Case> cases = new ArrayList<>();    private int[][] matrix_distance;    private int[][] matrix_energy;    private int[][] matrix_maxEnergy;    public Carte(Dimensions dimensions) {        this.dimensions = dimensions;    }    public Carte(Dimensions dimensions, List<Case> cases, int[][] matrix_distance, int[][] matrix_energy) {        this.dimensions = dimensions;        this.cases = cases;        this.matrix_distance = matrix_distance;        this.matrix_energy = matrix_energy;    }    public Dimensions getDimensions() {        return dimensions;    }    /**     * TODO:     * Generate map     */    public void generateCarte(Game game) {        int id = 0;        for (int i = 0; i < this.getDimensions().getSize(); i++) {            for (int j = 0; j < this.getDimensions().getSize(); j++) {                final Random random = new Random();                final double maxIndexBonus = game.getTauxBonus() * 100;                final double maxIndexObstacle = maxIndexBonus + (game.getTauxObstacle() * 100);                final int randomNumber = random.nextInt(100 - 1) + 1;                if(i == j && j == 0) { // CASE DE DEPART                    this.addCase(new Case(j, i, id, -1, CaseType.BLANK, true));                    if(Game4J.getInstance().isDebug()) System.out.println(id + " -> BLANK");                } else if(i == j && j == this.getDimensions().getSize() - 1) {                    this.addCase(new Case(j, i, id, -1, CaseType.WIN, true));                    if(Game4J.getInstance().isDebug()) System.out.println(id + " -> WIN");                } else if(randomNumber <= maxIndexBonus) {                    this.addCase(new Case(j, i, id, 9, CaseType.BONUS, true));                    if(Game4J.getInstance().isDebug()) System.out.println(id + " -> BONUS");                } else if(randomNumber <= maxIndexObstacle) {                    this.addCase(new Case(j, i, id, -11, CaseType.OBSTACLE, true));                    if(Game4J.getInstance().isDebug()) System.out.println(id + " -> OBSTACLE");                } else {                    this.addCase(new Case(j, i, id, -1, CaseType.BLANK, true));                    if(Game4J.getInstance().isDebug()) System.out.println(id + " -> BLANK");                }                id++;            }        }        try {            this.generate_matrix_distance();            this.generate_matrix_energy();            this.generate_matrix_energyBestPath();            final Dijkstra dijkstra = new Dijkstra();            List<Integer> path = dijkstra.getPath(game.getCarte().getMatrix_distance(), 0, 15);            if(Game4J.getInstance().isDebug()) System.out.println("Best path in distance");            for (Integer i: path) {                if(Game4J.getInstance().isDebug()) System.out.println(i);            }            int distance = 0;            for (int i = 0; i < path.size() - 1; i++) {                if(Game4J.getInstance().isDebug()) System.out.println("Mvt " + path.get(i) + " -> " + path.get(i+1) + " : " + this.matrix_distance[path.get(i)][path.get(i+1)] + " m.");                distance += this.matrix_distance[path.get(i)][path.get(i+1)];            }            if(Game4J.getInstance().isDebug()) System.out.println("Distance total minimum: " + distance);            List<Integer> energyPath = dijkstra.getPath(game.getCarte().getMatrix_energy(), 0, 15);            if(Game4J.getInstance().isDebug()) {                System.out.println("Best path in energy");                for (Integer i: energyPath) {                    System.out.println(i);                }            }            int costEnergy = 0;            for (int i = 0; i < energyPath.size() - 1; i++) {                if(Game4J.getInstance().isDebug()) System.out.println("Mvt " + energyPath.get(i) + " -> " + energyPath.get(i+1) + " : " + this.getCases().get(energyPath.get(i+1)).getEnergy() + " ue.");                costEnergy += this.getCases().get(energyPath.get(i+1)).getEnergy();            }            if(Game4J.getInstance().isDebug()) System.out.println("Energie totale minimum: " + costEnergy +" ue.");            if(costEnergy > game.getPersonnage().getInitialEnergy()) {                if(Game4J.getInstance().isDebug()) System.out.println("Not enough energy, needed: " + costEnergy + " had: " + game.getPersonnage().getInitialEnergy());                this.generateCarte(game);            }            List<Integer> maxEnergyPath = dijkstra.getPath(game.getCarte().getMatrix_maxEnergy(), 0, 15);            if(Game4J.getInstance().isDebug()) {                System.out.println("Best path to max energy");                for (Integer i: maxEnergyPath) {                    System.out.println(i);                }            }            int maxEnergy = 0;            for (int i = 0; i < maxEnergyPath.size() - 1; i++) {                if(Game4J.getInstance().isDebug()) System.out.println("Mvt " + maxEnergyPath.get(i) + " -> " + maxEnergyPath.get(i+1) + " : " + this.getCases().get(maxEnergyPath.get(i+1)).getEnergy() + " ue.");                maxEnergy += this.getCases().get(maxEnergyPath.get(i+1)).getEnergy();            }            if(Game4J.getInstance().isDebug()) System.out.println("Energie totale maximum: " + maxEnergy +" ue.");        } catch (ArrayIndexOutOfBoundsException e) {            if(Game4J.getInstance().isDebug()) {                System.out.println("No path founded !");                this.generateCarte(game);            }        }    }    public int[][] getMatrix_maxEnergy() {        return matrix_maxEnergy;    }    public List<Case> getCases() {        return cases;    }    public void afficherCarte() {        for (int i = 0; i < this.getDimensions().getSize(); i++) {            for (int j = 0; j < this.getDimensions().getSize(); j++) {                int indexI = i;                int indexJ = j;                final Case cases = this.getCases().stream().filter(c -> c.getPosition()[0] == indexJ && c.getPosition()[1] == indexI).findFirst().orElse(null);                String toPrint = "";                if(cases.getId() == Game4J.getInstance().getCurrentGame().getPersonnage().getPosition().getId()) {                     toPrint = "P";                } else {                    if(cases.isHidden() && cases.getCaseType() != CaseType.WIN) {                        toPrint = "?";                    } else {                        toPrint = cases.getCaseType().getToPrint();                    }                }                System.out.print(toPrint + " ");            }            System.out.println("\n");        }    }    public void addCase(Case caseToAdd) {        this.cases.add(caseToAdd);    }    public void removeCase(Case caseToRemove) {        this.cases.remove(caseToRemove);    }    public void generate_matrix_distance() {        final Graph g = new Graph(this.getDimensions().getArea());                int a = 0;        for (int i = 0; i < this.getDimensions().getArea(); i++) {            int index = i;            if(a < this.getDimensions().getMax_loop() && this.getCases().stream().filter(c -> c.getId() == (index+1)).findFirst().orElse(null).getCaseType() != CaseType.OBSTACLE && this.getCases().stream().filter(c -> c.getId() == (index)).findFirst().orElse(null).getCaseType() != CaseType.OBSTACLE ) {                g.addEdge(i, i + 1);                if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (i+1) + ")");                a++;            } else if(a < this.getDimensions().getMax_loop()){                g.addEdge(i, i + 1, 0);                if(Game4J.getInstance().isDebug()) System.out.println("addEdgeObstacle(" + i + "," + (i+1) + ")");                a++;            } else {                a = 0;            }        }        for (int i = 0; i <= Math.ceil(this.getDimensions().getArea()/2)+this.getDimensions().getMax_loop(); i++) {            int index = i;            if(this.getCases().stream().filter(c -> c.getId() == (index)).findFirst().orElse(null).getCaseType() != CaseType.OBSTACLE &&                    this.getCases().stream().filter(c -> c.getId() != (int) (index+ Math.sqrt(this.getDimensions().getArea()))).findFirst().orElse(null).getCaseType() != CaseType.OBSTACLE) {                g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())));                if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");            } else {                g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())), 0);                if(Game4J.getInstance().isDebug()) System.out.println("addEdgeObstacle(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");            }        }        this.matrix_distance = g.getAdjMatrix();        for (int i = 0; i < this.matrix_distance.length; i++) {            for (int j = 0; j < this.matrix_distance[0].length; j++) {                if(this.matrix_distance[i][j] == 0 && i != j) {                    this.matrix_distance[i][j] = -1;                }            }        }        if(Game4J.getInstance().isDebug()) System.out.println("distance matrix");        if(Game4J.getInstance().isDebug()) System.out.println(g);    }    public void generate_matrix_energy() {        final Graph g = new Graph(this.getDimensions().getArea());        int a = 0;        for (int i = 0; i < this.getDimensions().getArea(); i++) {            if(a < this.getDimensions().getMax_loop()) {                final int finalI = i;                final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);                if(casez == null) {                    if(Game4J.getInstance().isDebug()) System.out.println("Il y a un problème ");                    return;                }                g.addEdge(i, i + 1, casez.getEnergy());                if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (i+1) + ")");                a++;            } else {                a = 0;            }        }        for (int i = 0; i <= Math.ceil(this.getDimensions().getArea()/2)+this.getDimensions().getMax_loop(); i++) {            final int finalI = i;            final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);            if(casez == null) {                if(Game4J.getInstance().isDebug()) System.out.println("Il y a un problème");                return;            }            g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())), casez.getEnergy());            if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");        }        this.matrix_energy = g.getAdjMatrix();        for (int i = 0; i < this.matrix_energy.length; i++) {            for (int j = 0; j < this.matrix_energy[0].length; j++) {                int indexI = i;                int indexJ = j;                if(this.matrix_energy[i][j] == -1) {                    this.matrix_energy[i][j] = 20;                }                if(this.matrix_energy[i][j] == 9) {                    this.matrix_energy[i][j] =  35;                }                if(this.matrix_energy[i][j] == -11) {                    this.matrix_energy[i][j] = 50;                }                if(this.matrix_energy[i][j] == 0 && i != j) {                    this.matrix_energy[i][j] = -1;                }                if(Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(c -> c.getId() == indexI || c.getId() == indexJ).findFirst().orElse(null).getCaseType() == CaseType.OBSTACLE) {                    this.matrix_energy[i][j] = -1;                }            }        }        if(Game4J.getInstance().isDebug()) System.out.println("energy matrix");        if(Game4J.getInstance().isDebug()) System.out.println(g);    }    public void generate_matrix_energyBestPath() {        final Graph g = new Graph(this.getDimensions().getArea());        int a = 0;        for (int i = 0; i < this.getDimensions().getArea(); i++) {            if(a < this.getDimensions().getMax_loop()) {                final int finalI = i;                final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);                if(casez == null) {                    if(Game4J.getInstance().isDebug()) System.out.println("Il y a un problème ");                    return;                }                g.addEdge(i, i + 1, casez.getEnergy());                if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (i+1) + ")");                a++;            } else {                a = 0;            }        }        for (int i = 0; i <= Math.ceil(this.getDimensions().getArea()/2)+this.getDimensions().getMax_loop(); i++) {            final int finalI = i;            final Case casez = this.getCases().stream().filter(c -> c.getId() == finalI).findFirst().orElse(null);            if(casez == null) {                if(Game4J.getInstance().isDebug()) System.out.println("Il y a un problème");                return;            }            g.addEdge(i, (int) (i+ Math.sqrt(this.getDimensions().getArea())), casez.getEnergy());            if(Game4J.getInstance().isDebug()) System.out.println("addEdge(" + i + "," + (int) (i+ Math.sqrt(this.getDimensions().getArea())) + ")");        }        this.matrix_maxEnergy = g.getAdjMatrix();        for (int i = 0; i < this.matrix_maxEnergy.length; i++) {            for (int j = 0; j < this.matrix_maxEnergy[0].length; j++) {                int indexI = i;                int indexJ = j;                if(this.matrix_maxEnergy[i][j] == -1) {                    this.matrix_maxEnergy[i][j] = 40;                }                if(this.matrix_maxEnergy[i][j] == 9) {                    this.matrix_maxEnergy[i][j] =  20;                }                if(this.matrix_maxEnergy[i][j] == -11) {                    this.matrix_maxEnergy[i][j] = 50;                }                if(this.matrix_maxEnergy[i][j] == 0 && i != j) {                    this.matrix_maxEnergy[i][j] = -1;                }                if(Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(c -> c.getId() == indexI || c.getId() == indexJ).findFirst().orElse(null).getCaseType() == CaseType.OBSTACLE) {                    this.matrix_maxEnergy[i][j] = -1;                }            }        }        if(Game4J.getInstance().isDebug()) System.out.println("max energy matrix");        if(Game4J.getInstance().isDebug()) System.out.println(g);    }    public int[][] getMatrix_distance() {        return matrix_distance;    }    public int[][] getMatrix_energy() {        return matrix_energy;    }    public void generateBestPathWithDistance() {    }}