package fr.perroquets.game4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShowPathFrame extends JFrame{
    private JPanel gameFramePanel;
    private JLabel case24;
    private JLabel case23;
    private JLabel case22;
    private JLabel case21;
    private JLabel case16;
    private JLabel case17;
    private JLabel case18;
    private JLabel case19;
    private JLabel case11;
    private JLabel case12;
    private JLabel case13;
    private JLabel case14;
    private JLabel case6;
    private JLabel case7;
    private JLabel case8;
    private JLabel case9;
    private JLabel case10;
    private JLabel case15;
    private JLabel case20;
    private JLabel case25;
    private JLabel case1;
    private JLabel case2;
    private JLabel case3;
    private JLabel case4;
    private JLabel case5;

    public ShowPathFrame(String pathType) {
        super("Game4J - Résumé de partie");

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(gameFramePanel);
        this.pack();
        if(pathType.equalsIgnoreCase("DISTANCE")) {
            for(int id : Game4J.getInstance().getCurrentGame().getCarte().getBestPathInDistance()) {
                System.out.println(id);
                try {
                    final int ind = id+1;
                    Method method = this.getClass().getDeclaredMethod("getCase" + ind);
                    final BufferedImage image = ImageIO.read(new File("carrevert.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(this)).setIcon(ico);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else if(pathType.equalsIgnoreCase("MINENERGIE")) {
            for(int id : Game4J.getInstance().getCurrentGame().getCarte().getBestPathInEnergy()) {
                System.out.println(id);
                try {
                    final int ind = id+1;
                    Method method = this.getClass().getDeclaredMethod("getCase" + ind);
                    final BufferedImage image = ImageIO.read(new File("carrevert.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(this)).setIcon(ico);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else if(pathType.equalsIgnoreCase("MAXENERGIE")) {
            for(int id : Game4J.getInstance().getCurrentGame().getCarte().getBestPathToMaxEnergy()) {
                System.out.println(id);
                try {
                    final int ind = id+1;
                    Method method = this.getClass().getDeclaredMethod("getCase" + ind);
                    final BufferedImage image = ImageIO.read(new File("carrevert.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(this)).setIcon(ico);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JLabel getCase24() {
        return case24;
    }

    public JLabel getCase23() {
        return case23;
    }

    public JLabel getCase22() {
        return case22;
    }

    public JLabel getCase21() {
        return case21;
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

    public JLabel getCase10() {
        return case10;
    }

    public JLabel getCase15() {
        return case15;
    }

    public JLabel getCase20() {
        return case20;
    }

    public JLabel getCase25() {
        return case25;
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
}
