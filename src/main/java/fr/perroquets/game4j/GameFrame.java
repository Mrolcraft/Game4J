package fr.perroquets.game4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame{
    private JPanel gamePanel;
    private JPanel gameFramePanel;
    private JPanel playerFramePanel;
    private JSplitPane separator;
    private JLabel case6;
    private JLabel case7;
    private JLabel case8;
    private JLabel case9;
    private JLabel case1;
    private JLabel case2;
    private JLabel case3;
    private JLabel case4;
    private JLabel case5;
    private JLabel case10;
    private JLabel case11;
    private JLabel case12;
    private JLabel case13;
    private JLabel case14;
    private JLabel case15;
    private JLabel case16;
    private JLabel case17;
    private JLabel case18;
    private JLabel case19;
    private JLabel case20;
    private JLabel case21;
    private JLabel case22;
    private JLabel case23;
    private JLabel case24;
    private JLabel case25;
    private JLabel lastEvent;
    private JLabel distance;
    private JLabel lastEvent2;
    private JLabel energie;
    private JLabel map;

    public GameFrame() {
        super("Game4J - En jeu...");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        separator.setEnabled(false);
        this.pack();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(Game4J.getInstance().getCurrentGame().getGameState() == GameState.INGAME) {
                    if (e.getKeyCode() == 38) { //AVANCER
                        Game4J.getInstance().getCurrentGame().getPersonnage().move(Direction.NORTH);
                    } else if (e.getKeyCode() == 40) { //RECULER
                        Game4J.getInstance().getCurrentGame().getPersonnage().move(Direction.SOUTH);
                    } else if (e.getKeyCode() == 37) { //GAUCHE
                        Game4J.getInstance().getCurrentGame().getPersonnage().move(Direction.WEST);
                    } else if (e.getKeyCode() == 39) { //DROITE
                        Game4J.getInstance().getCurrentGame().getPersonnage().move(Direction.EAST);
                    } else if(e.getKeyChar() == 'r') {
                        Game4J.getInstance().getCurrentGame().getPersonnage().undoMove();
                    }
                }
            }
        });
    }

    public JLabel getCase6() {
        return case6;
    }

    public JLabel getCase7() {
        return case7;
    }

    public JLabel getCase8() {
        return case8;
    }

    public JLabel getCase9() {
        return case9;
    }

    public JLabel getEnergie() {
        return energie;
    }

    public JLabel getCase1() {
        return case1;
    }

    public JLabel getCase2() {
        return case2;
    }

    public JLabel getCase3() {
        return case3;
    }

    public JLabel getCase4() {
        return case4;
    }

    public JLabel getCase5() {
        return case5;
    }

    public JLabel getCase10() {
        return case10;
    }

    public JLabel getCase11() {
        return case11;
    }

    public JLabel getCase12() {
        return case12;
    }

    public JLabel getCase13() {
        return case13;
    }

    public JLabel getCase14() {
        return case14;
    }

    public JLabel getCase15() {
        return case15;
    }

    public JLabel getCase16() {
        return case16;
    }

    public JLabel getCase17() {
        return case17;
    }

    public JLabel getCase18() {
        return case18;
    }

    public JLabel getCase19() {
        return case19;
    }

    public JLabel getCase20() {
        return case20;
    }

    public JLabel getCase21() {
        return case21;
    }

    public JLabel getCase22() {
        return case22;
    }

    public JLabel getCase23() {
        return case23;
    }

    public JLabel getCase24() {
        return case24;
    }

    public JLabel getCase25() {
        return case25;
    }

    public JLabel getMap() {
        return map;
    }

    public JLabel getDistance() {
        return distance;
    }

    public JLabel getLastEvent() {
        return lastEvent;
    }

    public JLabel getLastEvent2() {
        return lastEvent2;
    }
}
