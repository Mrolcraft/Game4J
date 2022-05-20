package fr.perroquets.game4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton commencerUneNouvellePartieButton;
    private JButton continuerUnePartieExistanteButton;
    private JButton voirLHistoriqueDesButton;
    private JPanel menuPanel;

    public Menu() {
        super("Game4J - Menu");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(menuPanel);
        this.setLocationRelativeTo(null);
        this.pack();
        commencerUneNouvellePartieButton.addActionListener(e -> {
            this.setVisible(false);
            Game4J.getInstance().initNewGame();
        });
        continuerUnePartieExistanteButton.addActionListener(e -> {
            /*
           TODO:
           Récupérer la partie + lancer la fenêtre de jeu
            */
        });
        voirLHistoriqueDesButton.addActionListener(e -> {
            /*
           TODO:
           Afficher l'historique des parties + pouvoir lancer les replays
            */
        });
    }
}
