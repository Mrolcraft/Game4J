package fr.perroquets.game4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndFrame extends JFrame{
    private JTextArea resumeGame;
    private JPanel panel1;
    private JButton distanceButton;
    private JButton maxEnergieButton;
    private JButton minEnergieButton;

    /**
     * Permet l'affichage de l'écran de fin
     */
    public EndFrame() {
        super("Game4J - Résumé de jeu");

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocationRelativeTo(null);
        this.pack();
        distanceButton.addActionListener(e -> {
            final ShowPathFrame showPathFrame = new ShowPathFrame("DISTANCE");
            showPathFrame.setVisible(true);
        });
        maxEnergieButton.addActionListener(e -> {
            final ShowPathFrame showPathFrame = new ShowPathFrame("MAXENERGIE");
            showPathFrame.setVisible(true);
        });
        minEnergieButton.addActionListener(e -> {
            final ShowPathFrame showPathFrame = new ShowPathFrame("MINENERGIE");
            showPathFrame.setVisible(true);
        });
    }

    public JTextArea getResumeGame() {
        return resumeGame;
    }

}
