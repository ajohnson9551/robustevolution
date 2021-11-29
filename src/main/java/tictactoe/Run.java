package tictactoe;

import evolution.Fitness;
import evolution.GeneFactory;
import evolution.GenomeFactory;
import evolution.Population;

public class Run {

    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 10000; // must be at least 100

        int numGames = 200;
        double genomeSizePenalty = 0.05;

        int popSize = 200;
        int numBabies = 8;
        int bestBreederPairs = 2;
        int cull = 10;
        int randomBreederPairs = 2;

        double mutationRate = 0.2;
        int maxMutations = 4;
        int initialGenomeLength = 10;

        GeneFactory<int[], Integer> gf = new TicTacToeGeneFactory();
        GenomeFactory<int[], Integer> gnf = new TicTacToeGenomeFactory();
        Fitness<int[], Integer>  fit = new TicTacToeFitness(numGames, new TicTacToeGame(), genomeSizePenalty);
        maxScore = fit.getMaxScore();
        Population<int[], Integer> pop = new Population<>();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, cull, randomBreederPairs);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
        pop.getChampion().print();

        DisplayChart chart = new DisplayChart(
                "Tic Tac Toe Evolution Data",
                "Score from " + numGames + " Games and Genome Size",
                data,
                maxScore,
                cycles);
        chart.pack();
        chart.setVisible(true);
    }
}
