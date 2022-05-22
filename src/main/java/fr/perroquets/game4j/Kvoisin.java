package fr.perroquets.game4j;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kvoisin {
    double obstacle;
    double bonus;
    int victoire;

    public Kvoisin(double obstacle, double bonus, int victoire) {
        this.obstacle = obstacle;
        this.bonus = bonus;
        this.victoire = victoire;
    }

    /**
     * permet de recuperer un jeu de donnee .scv et le mettre dans une liste
     * @return ArrayList<Kvoisin>
     */
    public static ArrayList<Kvoisin> lectureData() {
        ArrayList<Kvoisin> jeuTest = new ArrayList<Kvoisin>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("dataSet.csv")));
            String line;
            while((line = br.readLine()) != null) {
                String [] entries = line.split(";");
                int victoire = entries[2].equalsIgnoreCase("yes")?1:0;
                System.out.println(victoire);
                Kvoisin jeu = new Kvoisin(Double.parseDouble(entries[0]),Double.parseDouble(entries[1]),victoire);
                jeuTest.add(jeu);



            }
        } catch(IOException e)

        {
            e.printStackTrace();
        }

        return jeuTest;
    }

    /**
     * permet de recuperer les @param kk prochs voisins afin de ralisesr l'alorithme de k proche voisins.
     * @param kk
     */
    public static void algoKVoisin(int kk) {
        List<Kvoisin> jeuTest = lectureData();
        double compteurVic = 0;

        for (int i = 43; i< jeuTest.size();i++) {
            List<double[]> tabResultat = new ArrayList<>();
            for (int j =0; j < 43;j++) {
              double differenceOne = (jeuTest.get(i).obstacle-jeuTest.get(j).obstacle)*(jeuTest.get(i).obstacle-jeuTest.get(j).obstacle);
              double differenceTwo = (jeuTest.get(i).bonus-jeuTest.get(j).bonus)*(jeuTest.get(i).bonus-jeuTest.get(j).bonus);
              double euclidienDistance = Math.sqrt(differenceOne + differenceTwo);
              double[] result = {euclidienDistance,jeuTest.get(j).victoire};
              tabResultat.add(result);

            }
            tabResultat.sort(Comparator.comparingDouble(value -> value[0]));
            tabResultat = tabResultat.subList(0,kk);
            int  calculVictoire = 0;
            for (int k = 0; k < tabResultat.size(); k++) {
                calculVictoire += tabResultat.get(k)[1];

            }
            if (calculVictoire >=kk-1) {
                System.out.println("prediction de l'IA : Victoire");

            }else {
                System.out.println("prediction de l'IA ; Defaite");
            }
            if (jeuTest.get(i).victoire == 1) {

                System.out.println("realite : Victoire");
                System.out.println("--------------------");

            }else {

                System.out.println("realite : Defaite");
                System.out.println("--------------------");
            }
            if (calculVictoire >= kk-1 && jeuTest.get(i).victoire == 1 || calculVictoire < kk-1 && jeuTest.get(i).victoire == 0) {
                compteurVic += 1;
            }




        }
        compteurVic = (compteurVic / (0.2*jeuTest.size()))*100;
        System.out.println(compteurVic);



    }

    public static void main(String[] args) {
    algoKVoisin(4);
    // Le plus optimum est de 4 proches voisins.
    }

    public int getVictoire() {
        return victoire;
    }
}
