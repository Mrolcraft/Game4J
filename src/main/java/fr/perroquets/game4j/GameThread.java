package fr.perroquets.game4j;

public class GameThread extends Thread{

    @Override
    public void run() {
        Game4J.getInstance().getCurrentGame().onGame();
    }
}
