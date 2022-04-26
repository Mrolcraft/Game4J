package fr.perroquets.game4j;

import java.util.Random;

public class Graph {

    private int adjMatrix[][];
    private int numVertices;

    // Initialize the matrix
    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
    }

    // Add edges
    public void addEdge(int i, int j) {
        final Random random = new Random();
        final int weight = random.nextInt(99) + 1;
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    public void addEdge(int i, int j, int weight) {
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight;
    }

    // Remove edges
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    // Print the matrix
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
