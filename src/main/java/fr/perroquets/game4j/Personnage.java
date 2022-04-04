public class Personnage{
  private int initialEnergy;
  private int lostEnergy;
  private int wonEnergy;
  private int maxUndoCount;
  private int currentUndoCount;
  private String direction;
  private Case position;

  public Personnage(int initialEnergy, int lostEnergy, int wonEnergy, int maxUndoCount, int currentUndoCount, String direction, Case position){
    this.initialEnergy=initialEnergy;
    this.lostEnergy=lostEnergy;
    this.wonEnergy=wonEnergy;
    this.maxUndoCount=maxUndoCount;
    this.currentUndoCount=currentUndoCount;
    this.direction=direction;
    this.position=position;
  }

  public void move(String direction){
    if(direction=="UP"){
      this.position=position.getNorth();
    }
    if(direction=="DOWN"){
      this.position.position.getSouth();
    }
    if(direction=="RIGHT"){
      this.position.position.getEast();
    }
    if(direction=="LEFT"){
      this.position.position.getWest();
    }
  }

  public int getInitialEnergy() {
    return initialEnergy;
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

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public Case getPosition() {
    return position;
  }


  
}
