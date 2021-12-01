package lunarlander;

import evolution.*;

import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 20000; // must be at least 100

        int numGames = 30; // should be exactly 30 if startMode is 2
        int startMode = 2; // 0 = fixed, 1 = random, 2 = preset range of 30 starts to iterate through
        double genomeSizePenalty = 0;

        int popSize = 100;
        int numBabies = 12;
        int bestBreederPairs = 3;
        int okayBreederPairs = 3;
        double okayThreshhold = 0.5; // above this percentile, genomes are considered "okay"
        int cull = 0;
        int randomBreederPairs = 1;
        boolean elitism = false; // if true, culls and replaces bottom, else culls and replaces randomly

        double mutationRate = 0.9;
        int maxMutations = 5;
        int initialGenomeLength = 30;

        GeneFactory gf = new DefaultGeneFactory(LunarLanderGene.class);
        GenomeFactory gnf = new DefaultGenomeFactory(LunarLanderGenome.class);
        Fitness fit = new LunarLanderFitness(numGames, genomeSizePenalty, startMode);
        maxScore = fit.getMaxScore();
        Population pop = new Population();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, okayBreederPairs,
                cull, randomBreederPairs, elitism, okayThreshhold);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
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
        LunarLanderGenome champ = (LunarLanderGenome) pop.getAllTimeChampion();
        champ.print();
        System.out.println("Max score = " + pop.getAllTimeMaxScore());
        System.out.println("View champion (score = " + pop.getAllTimeChampion().getScore() + ") piloting the lunar lander? y/n");
        if (scan.nextLine().equalsIgnoreCase("y")) {
            for (int i = 0; i < numGames; i++) {
                LunarLanderGame game = new LunarLanderGame(true, true, false, startMode, i);
                game.play(champ);
            }
        }
    }
}
