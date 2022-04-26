package fr.perroquets.game4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Personnage{

    private int initialEnergy;
    private int lostEnergy;
    private int wonEnergy;
    private int currentEnergy;
    private int maxUndoCount;
    private int distance;
    private int currentUndoCount;
    private Direction direction = Direction.NULL;
    private Case position;

    private List<Movement> history = new ArrayList<>();

    public Personnage(int initialEnergy, int lostEnergy, int wonEnergy, int maxUndoCount, int currentUndoCount, int distance, int currentEnergy, Case position){
        this.initialEnergy=initialEnergy;
        this.lostEnergy=lostEnergy;
        this.wonEnergy=wonEnergy;
        this.maxUndoCount=maxUndoCount;
        this.currentUndoCount=currentUndoCount;
        this.position=position;
        this.distance = distance;
        this.currentEnergy = currentEnergy;
    }

    public Personnage(int initialEnergy, int lostEnergy, int wonEnergy, int currentEnergy, int maxUndoCount, int distance, int currentUndoCount, Direction direction, Case position, List<Movement> history) {
        this.initialEnergy = initialEnergy;
        this.lostEnergy = lostEnergy;
        this.wonEnergy = wonEnergy;
        this.currentEnergy = currentEnergy;
        this.maxUndoCount = maxUndoCount;
        this.distance = distance;
        this.currentUndoCount = currentUndoCount;
        this.direction = direction;
        this.position = position;
        this.history = history;
    }

    public void undoMove() {
        if(this.maxUndoCount == this.currentUndoCount) {
            System.out.println("Vous ne pouvez plus annuler de déplacement !");
            return;
        }

        this.position = this.history.get(this.history.size() - 1).getFrom();
        this.history.remove(this.history.size() - 1);
        this.currentUndoCount--;
        System.out.println("Vous avez reculé d'une case ! Vous pouvez encore effectuer " + this.currentUndoCount + " marche(s) arrière(s).");
    }

    public void move(Direction direction){
        if(direction == Direction.NORTH){
            this.history.add(new Movement(this.position, position.getNorth()));
            this.position=position.getNorth();
            chechHistory();
        }
        if(direction == Direction.SOUTH){
            this.history.add(new Movement(this.position, position.getSouth()));
            this.position = position.getSouth();
            chechHistory();
        }
        if(direction == Direction.EAST){
            this.history.add(new Movement(this.position, position.getEast()));
            this.position = position.getEast();
            chechHistory();
        }
        if(direction == Direction.WEST) {
            this.history.add(new Movement(this.position, position.getWest()));
            this.position = position.getWest();
            chechHistory();
        }
        this.setCurrentEnergy(this.position.getEnergy());
        if(this.position.getEnergy() > 0) {
            this.setWonEnergy(this.position.getEnergy() + this.getWonEnergy());
        } else {
            this.setLostEnergy(this.position.getEnergy() + this.getLostEnergy());
        }

        this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getPosition()[0]][this.position.getPosition()[1]];
        checkWin();
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getDistance() {
        return distance;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    private void checkWin() {
        if(this.position.getPosition()[0] == Game4J.getInstance().getCurrentGame().getCarte().getDimensions().getArea() - 1 && this.position.getPosition()[1] == Game4J.getInstance().getCurrentGame().getCarte().getDimensions().getArea() - 1) {
            System.out.println("Félicitations ! Vous avez gagné !");
            System.out.println("==================================");
            System.out.println("Vous avez parcouru " + this.getDistance() + " mètres.");
            System.out.println("Il vous reste " + this.getCurrentEnergy());
            System.out.println("Vous avez gagné au cours de la partie " + this.getWonEnergy());
            System.out.println("Vous avez perdu au cours de la partie " + this.getLostEnergy());
            System.out.println("==================================");
            System.out.println("Votre chemin utilisé: ");
            this.getHistory().forEach(mvt -> System.out.println(mvt.getFrom() + " -> " + mvt.getTo()));
            System.out.println("==================================");

        }
    }

    private void chechHistory() {
        if(this.history.stream().filter(h -> h.getTo().getId() == this.position.getId()).count() > 1) {
            System.out.println("Vous avez déjà visité cette case dans le passé !");
            final List<Movement> temp_history = new ArrayList<>();
            Collections.copy(temp_history, this.history);
            Collections.reverse(temp_history);
            System.out.println("Voici la boucle: ");
            System.out.println("(" + temp_history.get(0).getFrom().getPosition()[0] + "," + temp_history.get(0).getFrom().getPosition()[1] + ") -> (" + temp_history.get(0).getTo().getPosition()[0] + "," + temp_history.get(0).getTo().getPosition()[1] + ")");
            for (int i = 1; i < temp_history.size(); i++) {
                System.out.println("(" + temp_history.get(i).getFrom().getPosition()[0] + "," + temp_history.get(i).getFrom().getPosition()[1] + ") -> (" + temp_history.get(i).getTo().getPosition()[0] + "," + temp_history.get(i).getTo().getPosition()[1] + ")");
                if(temp_history.get(i).getTo().getId() == this.position.getId()) break;
            }
        }
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public void initPosition() {
        this.position = Game4J.getInstance().getCurrentGame().getCarte().getCases().stream().filter(c -> c.getPosition()[0] == 0 && c.getPosition()[1] == 1).findFirst().orElse(null);
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public int getLostEnergy() {
        return lostEnergy;
    }

    public void setLostEnergy(int lostEnergy) {
        this.lostEnergy = lostEnergy;
    }

    public int getWonEnergy() {
        return wonEnergy;
    }

    public void setWonEnergy(int wonEnergy) {
        this.wonEnergy = wonEnergy;
    }

    public int getMaxUndoCount() {
        return maxUndoCount;
    }

    public int getCurrentUndoCount() {
        return currentUndoCount;
    }

    public void setCurrentUndoCount(int currentUndoCount) {
        this.currentUndoCount = currentUndoCount;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Case getPosition() {
        return position;
    }

    public List<Movement> getHistory() {
        return history;
    }
}
