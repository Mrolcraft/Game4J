package fr.perroquets.game4j;

import java.util.List;

public interface Pathfinding {

    /**
     *
     * @param graph
     *            graphe representant les donnees du probleme
     * @param start
     *            depart
     * @param end
     *            destination
     * @return la liste des points a parcourir (point de depart et d'arrivee
     *         inclus), ou null s'il n'existe pas de chemin reliant le point de
     *         depart au point d'arrivee.
     */
    public List<Integer> getPath(final int[][] graph, final int start, final int end);

    /**
     *
     * @param graph
     *            graphe representant les donnees du probleme
     * @param start
     *            depart
     * @param ends
     *            tableau des destinations possibles
     * @return la liste des points a parcourir (point de depart et destination
     *         inclus), telle que le chemin reliant ces deux points est le plus
     *         court parmi toutes les combinaisons existantes entre le point de
     *         depart et les differentes destinations donnes.
     */
    public List<Integer> getPath(final int[][] graph, final int start, final int[] ends);

    /**
     *
     * @param graph
     *            graphe representant les donnees du probleme
     * @param starts
     *            tableau des departs possibles
     * @param ends
     *            tableau des destinations possibles
     * @return la liste des points a parcourir (point de depart et destination
     *         inclus), telle que le chemin reliant ces deux points est le plus
     *         court parmi toutes les combinaisons existantes entre les
     *         differents points de depart et d'arrivee donnes.
     */
    public List<Integer> getPath(final int[][] graph, final int[] starts, final int[] ends);
}
