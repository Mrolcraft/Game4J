package fr.perroquets.game4j;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReplayFrame extends JFrame{
    private JPanel panel1;
    private JPanel controlPanel;
    private JButton vitesseMoyenneButton;
    private JButton vitesseRapideButton;
    private JButton vitesseLenteButton;
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
    private JButton retourAuMenuButton;

    private int vitesse = 0;
    ScheduledFuture<?> scheduledFuture;

    /**
     * Affiche la fenêtre de replay
     */
    public ReplayFrame() {
        super("Game4J - Replay de la partie...");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        vitesseRapideButton.addActionListener(e -> {
            vitesse = 1;
            if(scheduledFuture != null) scheduledFuture.cancel(false);
            scheduledFuture = exec.scheduleAtFixedRate(new ReplayThread(), 1, vitesse, TimeUnit.SECONDS);
        });
        vitesseMoyenneButton.addActionListener(e -> {
            vitesse = 2;
            if(scheduledFuture != null) scheduledFuture.cancel(false);
            scheduledFuture = exec.scheduleAtFixedRate(new ReplayThread(), 1, vitesse, TimeUnit.SECONDS);
        });
        vitesseLenteButton.addActionListener(e -> {
            vitesse = 3;
            if(scheduledFuture != null) scheduledFuture.cancel(false);
            scheduledFuture = exec.scheduleAtFixedRate(new ReplayThread(), 1, vitesse, TimeUnit.SECONDS);
        });
        retourAuMenuButton.addActionListener(e -> {
            if(scheduledFuture != null) scheduledFuture.cancel(false);
            this.setVisible(false);
            final Menu menu = new Menu();
            menu.setVisible(true);
        });
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
