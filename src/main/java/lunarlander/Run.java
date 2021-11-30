package lunarlander;

import evolution.*;

import java.util.Locale;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 5000; // must be at least 100

        int numGames = 15;
        boolean randomStart = true;
        double genomeSizePenalty = 0.1;

        int popSize = 200;
        int numBabies = 8;
        int bestBreederPairs = 2;
        int cull = 10;
        int randomBreederPairs = 2;

        double mutationRate = 0.2;
        int maxMutations = 4;
        int initialGenomeLength = 10;

        GeneFactory gf = new DefaultGeneFactory(LunarLanderGene.class);
        GenomeFactory gnf = new DefaultGenomeFactory(LunarLanderGenome.class);
        Fitness fit = new LunarLanderFitness(numGames, genomeSizePenalty, randomStart);
        maxScore = fit.getMaxScore();
        Population pop = new Population();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, cull, randomBreederPairs);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
        pop.getChampion().print();
        DataLineChart chart = new DataLineChart(
                "Lunar Lander Evolution Data",
                "Score and Genome Size",
                data,
                -maxScore,
                maxScore,
                100,
                cycles);
        chart.pack();
        chart.setVisible(true);
        Scanner scan = new Scanner(System.in);
        System.out.println("View champion (score = " + pop.getChampion().getScore() + ") piloting the lunar lander? y/n");
        if (scan.nextLine().equalsIgnoreCase("y")) {
            for (int i = 0; i < numGames; i++) {
                LunarLanderGame game = new LunarLanderGame(true, true, false, randomStart);
                game.play((LunarLanderGenome) pop.getChampion());
            }
        }
    }
}
