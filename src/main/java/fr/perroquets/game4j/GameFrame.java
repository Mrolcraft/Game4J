package fr.perroquets.game4j;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame{
    private JPanel gamePanel;
    private JPanel gameFramePanel;
    private JPanel playerFramePanel;
    private JSplitPane separator;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
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

    public JLabel getMap() {
        return map;
    }
}
