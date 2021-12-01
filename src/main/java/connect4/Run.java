package connect4;

import evolution.*;

public class Run {
    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 2000; // must be at least 100

        int numGames = 200;
        double genomeSizePenalty = 0.1;

        int popSize = 200;
        int numBabies = 8;
        int bestBreederPairs = 2;
        int okayBreederPairs = 0;
        double okayThreshhold = 0.3;
        int cull = 10;
        int randomBreederPairs = 2;

        double mutationRate = 0.2;
        int maxMutations = 4;
        int initialGenomeLength = 10;

        GeneFactory gf = new DefaultGeneFactory(Connect4Gene.class);
        GenomeFactory gnf = new DefaultGenomeFactory(Connect4Genome.class);
        Fitness fit = new Connect4Fitness(numGames, new Connect4Game(), genomeSizePenalty);
        maxScore = fit.getMaxScore();
        Population pop = new Population();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, okayBreederPairs, cull,
                randomBreederPairs, true, okayThreshhold);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
        pop.getAllTimeChampion().print();

        DataLineChart chart = new DataLineChart(
                "Connect 4 Evolution Data",
                "Score from " + numGames + " Games and Genome Size",
                data,
                0,
                maxScore,
                10,
                cycles);
        chart.pack();
        chart.setVisible(true);
    }
}
