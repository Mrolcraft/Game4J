package fr.perroquets.game4j;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
            System.out.println("Vous avez decide de commencer une nouvelle partie...");
            System.out.println("Generation de la partie...");
            final LoadingFrame loadingFrame = new LoadingFrame();
            loadingFrame.setVisible(true);
            Game4J.getInstance().setLoadingFrame(loadingFrame);
            Game4J.getInstance().initNewGame();
        });
        continuerUnePartieExistanteButton.addActionListener(e -> {
            this.setVisible(false);
            try {
                final ResumeGameFrame resumeGameFrame = new ResumeGameFrame(Game.restoreAllGames());
                resumeGameFrame.setVisible(true);
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        });
        voirLHistoriqueDesButton.addActionListener(e -> {
            /*
           TODO:
           Afficher l'historique des parties + pouvoir lancer les replays
            */
        });
    }
}
