package fr.perroquets.game4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private int mvtId = 0;

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

    private void incrementMovement() {
        this.mvtId++;
    }

    /*
    TODO:
     */
    public void move(Direction direction){
        if(direction == Direction.NORTH){
            if(position.getNorth() == null) return;
            if(position.getNorth().getCaseType() == CaseType.OBSTACLE) {
                System.out.println("C'est con vous avez touche un obstacle ! -10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getNorth()));
                this.history.add(new Movement(this.mvtId, position.getNorth(), this.position));
                this.setCurrentEnergy(this.getCurrentEnergy() + position.getNorth().getEnergy());
                this.position.getNorth().setHidden(false);
                this.setLostEnergy(this.position.getNorth().getEnergy() + this.getLostEnergy());
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getNorth().getId()];
                chechHistory();
            } else if(position.getNorth().getCaseType() == CaseType.BONUS) {
                System.out.println("Vous avez eu un bonus ! +10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getNorth()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getNorth().getEnergy());
                this.setWonEnergy(this.position.getNorth().getEnergy() + this.getWonEnergy());
                this.position.getNorth().setCaseType(CaseType.BLANK);
                this.position.getNorth().setHidden(false);
                this.position.getNorth().setEnergy(-1);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getNorth().getId()];
                this.position = position.getNorth();
                chechHistory();
            }  else {
                this.history.add(new Movement(this.mvtId, this.position, position.getNorth()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getNorth().getEnergy());
                this.setLostEnergy(this.position.getNorth().getEnergy() + this.getLostEnergy());
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getNorth().getId()];
                this.position=position.getNorth();
                chechHistory();
            }
        }
        if(direction == Direction.SOUTH){
            if(position.getSouth() == null) return;
            if(position.getSouth().getCaseType() == CaseType.OBSTACLE) {
                System.out.println("C'est con vous avez touche un obstacle ! -10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getSouth()));
                this.history.add(new Movement(this.mvtId, position.getSouth(), this.position));
                this.setCurrentEnergy(this.getCurrentEnergy() + position.getSouth().getEnergy());
                this.setLostEnergy(this.position.getSouth().getEnergy() + this.getLostEnergy());
                this.position.getSouth().setHidden(false);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getSouth().getId()];
                chechHistory();
            } else if(position.getSouth().getCaseType() == CaseType.BONUS) {
                System.out.println("Vous avez eu un bonus ! +10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getSouth()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getSouth().getEnergy());
                this.setWonEnergy(this.position.getSouth().getEnergy() + this.getWonEnergy());
                this.position.getSouth().setCaseType(CaseType.BLANK);
                this.position.getSouth().setHidden(false);
                this.position.getSouth().setEnergy(-1);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getSouth().getId()];
                this.position = position.getSouth();
                chechHistory();
            } else {
                this.history.add(new Movement(this.mvtId, this.position, position.getSouth()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getSouth().getEnergy());
                this.setLostEnergy(this.position.getSouth().getEnergy() + this.getLostEnergy());
                this.position.getSouth().setHidden(false);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getSouth().getId()];
                this.position=position.getSouth();
                chechHistory();
            }
        }
        if(direction == Direction.EAST){
            if(position.getEast() == null) return;
            if(position.getEast().getCaseType() == CaseType.OBSTACLE) {
                System.out.println("C'est con vous avez touche un obstacle ! -10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getEast()));
                this.history.add(new Movement(this.mvtId, position.getEast(), this.position));
                this.setCurrentEnergy(this.getCurrentEnergy() + position.getEast().getEnergy());
                this.setLostEnergy(this.position.getEast().getEnergy() + this.getLostEnergy());
                this.position.getEast().setHidden(false);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getEast().getId()];
                chechHistory();
            } else if(position.getEast().getCaseType() == CaseType.BONUS) {
                System.out.println("Vous avez eu un bonus ! +10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getEast()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getEast().getEnergy());
                this.setWonEnergy(this.position.getEast().getEnergy() + this.getWonEnergy());
                this.position.getEast().setCaseType(CaseType.BLANK);
                this.position.getEast().setHidden(false);
                this.position.getEast().setEnergy(-1);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getEast().getId()];
                this.position = position.getEast();
                chechHistory();
            } else {
                this.history.add(new Movement(this.mvtId, this.position, position.getEast()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getEast().getEnergy());
                this.setLostEnergy(this.position.getEast().getEnergy() + this.getLostEnergy());
                this.position.getEast().setHidden(false);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getEast().getId()];
                this.position=position.getEast();
                chechHistory();
            }
        }
        if(direction == Direction.WEST) {
            if(position.getWest() == null) return;
            if(position.getWest().getCaseType() == CaseType.OBSTACLE) {
                System.out.println("C'est con vous avez touche un obstacle ! -10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getWest()));
                this.history.add(new Movement(this.mvtId, position.getWest(), this.position));
                this.setCurrentEnergy(this.getCurrentEnergy() + position.getWest().getEnergy());
                this.position.getWest().setHidden(false);
                this.setLostEnergy(this.position.getWest().getEnergy() + this.getLostEnergy());
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getWest().getId()];
                chechHistory();
            } else if(position.getWest().getCaseType() == CaseType.BONUS) {
                System.out.println("Vous avez eu un bonus ! +10 énergie");
                this.history.add(new Movement(this.mvtId, this.position, position.getWest()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getWest().getEnergy());
                this.setWonEnergy(this.position.getWest().getEnergy() + this.getWonEnergy());
                this.position.getWest().setCaseType(CaseType.BLANK);
                this.position.getWest().setHidden(false);
                this.position.getWest().setEnergy(-1);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getWest().getId()];
                this.position = position.getWest();
                chechHistory();
            } else {
                this.history.add(new Movement(this.mvtId, this.position, position.getWest()));
                this.setCurrentEnergy(getCurrentEnergy() + position.getWest().getEnergy());
                this.setLostEnergy(this.position.getWest().getEnergy() + this.getLostEnergy());
                this.position.getWest().setHidden(false);
                this.distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[this.position.getId()][this.position.getWest().getId()];
                this.position=position.getWest();
                chechHistory();
            }
        }
        System.out.println("Distance parcourue: " + this.distance);
        this.position.setHidden(false);
        this.incrementMovement();
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
        if(this.position.getCaseType() == CaseType.WIN) {
            System.out.println("Félicitations ! Vous avez gagné !");
            System.out.println("==================================");
            System.out.println("Vous avez parcouru " + this.getDistance() + " mètres.");
            System.out.println("Il vous reste " + this.getCurrentEnergy());
            System.out.println("Vous avez gagné au cours de la partie " + this.getWonEnergy());
            System.out.println("Vous avez perdu au cours de la partie " + this.getLostEnergy());
            System.out.println("==================================");
            System.out.println("Votre chemin utilisé: ");
            this.getHistory().sort(Comparator.comparingInt(Movement::getId));
            this.getHistory().forEach(mvt -> System.out.println(mvt.getFrom().getId() + " -> " + mvt.getTo().getId()));
            System.out.println("==================================");
            System.out.println("==================================");
            System.out.println("Le meilleur chemin en terme de distance: ");
            int distance = 0;
            for (int i = 0; i < Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().size() - 1; i++) {
                System.out.println("Mvt " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i) + " -> " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i+1) + " : " + Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i+1)] + " m.");
                distance += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_distance()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance().get(i+1)];
            }
            System.out.println("Distance totale minimum: " + distance + " m");
            System.out.println("==================================");
            System.out.println("Le meilleur chemin en terme d'energie: ");
            int costEnergy = 0;
            for (int i = 0; i < Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().size() - 1; i++) {
                System.out.println("Mvt " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i) + " -> " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i+1) + " : " + Game4J.getInstance().getCurrentGame().getCarte().getMatrix_energy()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i+1)] + " ue.");
                costEnergy += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_energy()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy().get(i+1)];
            }
            System.out.println("Cout total minimum: " + costEnergy + " ue.");
            System.out.println("==================================");
            System.out.println("Le meilleur chemin pour maximiser l'energie: ");
            int costMaxEnergy = 0;
            for (int i = 0; i < Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().size() - 1; i++) {
                System.out.println("Mvt " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i) + " -> " + Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i+1) + " : " + Game4J.getInstance().getCurrentGame().getCarte().getMatrix_maxEnergy()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i+1)] + " ue.");
                costMaxEnergy += Game4J.getInstance().getCurrentGame().getCarte().getMatrix_maxEnergy()[Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i)][Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy().get(i+1)];
            }
            System.out.println("Distance totale minimum: " + costMaxEnergy + " ue.");
            System.out.println("==================================");
            Game4J.getInstance().getCurrentGame().setGameState(GameState.FINISHED);
        }
    }

    private void chechHistory() {
        this.history.forEach(c -> System.out.println(c.getFrom().getId() + " -> " + c.getTo().getId()));
        if(this.history.stream().filter(h -> h.getTo() != null && h.getTo().getId() == this.position.getId()).count() > 1) {
            System.out.println("Vous avez déjà visité cette case dans le passé !");
            final List<Movement> temp_history = this.history;
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
