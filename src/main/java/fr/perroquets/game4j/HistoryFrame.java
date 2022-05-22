package fr.perroquets.game4j;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HistoryFrame extends JFrame {
    private JList list;
    private JPanel panel1;

    public HistoryFrame(List<Game> gameList) {
        super("Game4J - Voir l'historique des parties...");

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Voulez vous retourner au menu sinon quitter ?",
                        "Choix fermeture", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 1) {
                    System.exit(0);
                } else if(confirm == 0) {
                    hidde();
                    final Menu menu = new Menu();
                    menu.setVisible(true);
                }
            }
        });
        this.setContentPane(panel1);
        this.pack();

        final DefaultListModel<String> model = new DefaultListModel<>();
        Collections.reverse(gameList);
        gameList.forEach(g -> {
            if(g.getGameState() == GameState.FINISHED) {
                model.addElement(g.getId() + " - " + g.getPersonnage().getCurrentEnergy() + " - " + g.getStartDateTime() + " - " + g.getTauxBonus() + "/" + g.getTauxObstacle() + " - " + (g.isVictory() ? "Victoire" : "Défaite"));
            }
        });
        this.list.setModel(model);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JList list = (JList) e.getSource();
                if(e.getClickCount() == 2) {
                    final String id = list.getSelectedValue().toString().split(" - ")[0];
                    System.out.println("Replay de la partie: " + id);
                    try {
                        final Game game = Game.restoreGame(id);
                        Game4J.getInstance().setCurrentGame(game);
                        game.getCarte().regeneratePath(game);
                        final ReplayFrame replayFrame = new ReplayFrame();
                        replayFrame.setVisible(true);
                        Game4J.getInstance().setReplayFrame(replayFrame);
                        hidde();
                    } catch (IOException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }

    public void hidde() {
        this.setVisible(false);
    }
}
