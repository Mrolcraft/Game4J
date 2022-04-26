package fr.perroquets.game4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DijkstraEnergy implements Pathfinding {

    private int[][] graph;

    private int[] distanceFromStart;

    private boolean[] activesNodes;

    private int dim;

    private int[] precedences;

    private void activeAdjacents(final int node) {
        int distanceTo;
        for (int to = 0; to < this.dim; to++)
            if (this.isAdjacent(node, to) && (distanceTo = this.distanceFromStart[node] + this.graph[node][to]) < this.distanceFromStart[to])
                this.activeNode(node, to, distanceTo);
    }

    private void activeNode(final int from, final int node, final int distance) {
        this.distanceFromStart[node] = distance;
        this.precedences[node] = from;
        this.activesNodes[node] = true;
    }

    private List<Integer> buildPath(final int end) {
        final List<Integer> path = new ArrayList<Integer>();
        path.add(end);

        // utilisation d'une boucle do-while pour conserver le point de depart
        // et d'arrivee dans la liste même lorsque le point de depart correspond
        // au point d'arrivee
        int position = end;
        do {
            path.add(0, this.precedences[position]);
            position = path.get(0);
        } while (this.distanceFromStart[position] != 0);

        return path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getPath(final int[][] graph, final int start, final int end) {
        return this.getPath(graph, new int[] { start }, new int[] { end });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getPath(final int[][] graph, final int start, final int[] ends) {
        return this.getPath(graph, new int[] { start }, ends);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getPath(final int[][] graph, final int[] starts, final int[] ends) {
        Arrays.sort(ends);

        // initialisation des variables necessaires a la resolution du probleme
        this.init(graph, starts);

        // calcul des distances par rapport au point de depart et recuperation
        // du point d'arrivee
        final int end = this.processDistances(ends);

        // construit et retourne l'itineraire
        return this.buildPath(end);
    }

    private void init(final int[][] graph, final int[] start) {
        this.graph = graph;
        this.dim = graph.length;
        this.activesNodes = new boolean[this.dim];

        this.precedences = new int[this.dim];
        Arrays.fill(this.precedences, -1);

        this.distanceFromStart = new int[this.dim];
        Arrays.fill(this.distanceFromStart, Integer.MAX_VALUE);

        for (final int value : start)
            this.activeNode(value, value, 0);
    }

    private boolean isAdjacent(final int from, final int to) {
        return this.graph[from][to] >= 0;
    }

    private int processDistances(final int[] ends) {
        // selectionne le prochain noeud a analyser (noeud courant)
        final int next = this.selectNextNode();

        // return -1 s'il n'y a plus de noeud a analyser, donc qu'il n'existe
        // pas de chemin satisfaisant la recherche
        if (next == -1)
            return -1;

        // retourne la position du noeud actuel s'il appartient au tableau des
        // destinations possibles
        if (Arrays.binarySearch(ends, next) >= 0)
            return next;

        // active les prochains noeuds a analyser a partir du noeud courant
        this.activeAdjacents(next);

        // desactive le noeud courant
        this.activesNodes[next] = false;

        // appel recursif de la methode pour traiter le prochain noeud
        return this.processDistances(ends);
    }

    private int selectNextNode() {
        int nextNode = -1;
        for (int node = 0; node < this.dim; node++)
            if (this.activesNodes[node] && (nextNode == -1 || this.distanceFromStart[node] < this.distanceFromStart[nextNode]))
                nextNode = node;

        return nextNode;
    }
}

