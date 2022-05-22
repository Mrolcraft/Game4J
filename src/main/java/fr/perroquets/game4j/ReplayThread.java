package fr.perroquets.game4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class ReplayThread extends Thread{

    private int index;

    public ReplayThread() {
        this.index = 0;
        for (int i = 1; i < 26; i++) {
            if(i == 1) {
                Method method = null;
                try {
                    method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + i);
                    final BufferedImage image = ImageIO.read(new File("casejoueur.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(ico);
                } catch (NoSuchMethodException | IOException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if(i == 25) {
                Method method = null;
                try {
                    method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + i);
                    final BufferedImage image = ImageIO.read(new File("casemaison.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(ico);
                } catch (NoSuchMethodException | IOException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Method method = null;
                try {
                    method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + i);
                    final BufferedImage image = ImageIO.read(new File("carre.jpg"));
                    final ImageIcon ico = new ImageIcon(image);
                    ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(ico);
                } catch (NoSuchMethodException | IOException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        Game4J.getInstance().getCurrentGame().getPersonnage().getHistory().sort(Comparator.comparingInt(Movement::getId));
        final Movement movement = Game4J.getInstance().getCurrentGame().getPersonnage().getHistory().get(index);
        final Case caseTo = movement.getTo();
        final Case caseFrom = movement.getFrom();
        final int indFrom = caseFrom.getId()+1;
        final int indTo = caseTo.getId()+1;
        Method method = null;
        try {
            if(caseFrom.getCaseType() == CaseType.OBSTACLE) {
                method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + indFrom);
                final BufferedImage imageFrom = ImageIO.read(new File("caserocher.jpg"));
                final ImageIcon icoFrom = new ImageIcon(imageFrom);
                ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(icoFrom);
            } else if(caseFrom.getCaseType() == CaseType.BONUS) {
                method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + indFrom);
                final BufferedImage imageFrom = ImageIO.read(new File("casecoffre.jpg"));
                final ImageIcon icoFrom = new ImageIcon(imageFrom);
                ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(icoFrom);
            } else {
                method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + indFrom);
                final BufferedImage imageFrom = ImageIO.read(new File("carrevert.jpg"));
                final ImageIcon icoFrom = new ImageIcon(imageFrom);
                ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(icoFrom);
            }
            method = Game4J.getInstance().getReplayFrame().getClass().getDeclaredMethod("getCase" + indTo);
            final BufferedImage imageTo = ImageIO.read(new File("casejoueur.jpg"));
            final ImageIcon icoTo = new ImageIcon(imageTo);
            ((JLabel) method.invoke(Game4J.getInstance().getReplayFrame())).setIcon(icoTo);
        } catch (NoSuchMethodException | IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(Game4J.getInstance().getCurrentGame().getPersonnage().getHistory().size() == index-1) this.interrupt();
        index++;
    }
}
