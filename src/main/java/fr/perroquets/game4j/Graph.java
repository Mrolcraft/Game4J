package fr.perroquets.game4j;

import java.util.Random;

public class Graph {

    private int adjMatrix[][];
    private int numVertices;

    /**
     * Initialise un graphe
     * @param numVertices
     */
    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
    }

    /**
     * Ajoute une arrête au graphe
     * @param i
     * @param j
     */
    public void addEdge(int i, int j) {
        final Random random = new Random();
        final int weight = random.nextInt(99) + 1;
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    /**
     * Ajoute une arrête pondérée au graphe
     * @param i
     * @param j
     * @param weight
     */
    public void addEdge(int i, int j, int weight) {
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    /**
     * Supprime une arrête du graphe
     * @param i
     * @param j
     */
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * Permet d'afficher la matrice
     * @return String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            s.append(i + ": ");
            for (int j : adjMatrix[i]) {
                s.append(j+ " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

}
