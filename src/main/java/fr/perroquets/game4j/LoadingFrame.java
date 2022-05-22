package fr.perroquets.game4j;

import javax.swing.*;

public class LoadingFrame extends JFrame{
    private JPanel panel1;
    private JProgressBar progressBar1;

    /**
     * Écran de chargement
     */
    public LoadingFrame() {
        super("Game4J - Chargement...");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
    }

    public void setProgress(int progress) {
        this.progressBar1.setValue(progress);
    }
}
