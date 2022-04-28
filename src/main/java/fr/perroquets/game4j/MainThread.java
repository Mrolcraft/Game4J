package fr.perroquets.game4j;

public class MainThread extends Thread{

    @Override
    public void run() {
        Game4J.getInstance().launchGame();
    }
}
