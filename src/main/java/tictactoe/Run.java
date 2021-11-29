package tictactoe;

import evolution.*;

public class Run {

    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 5000; // must be at least 100

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

        GeneFactory gf = new DefaultGeneFactory(TicTacToeGene.class);
        GenomeFactory gnf = new DefaultGenomeFactory(TicTacToeGenome.class);
        Fitness fit = new TicTacToeFitness(numGames, new TicTacToeGame(), genomeSizePenalty);
        maxScore = fit.getMaxScore();
        Population pop = new Population();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, cull, randomBreederPairs);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
        pop.getChampion().print();

        DataLineChart chart = new DataLineChart(
                "Tic Tac Toe Evolution Data",
                "Score from " + numGames + " Games and Genome Size",
                data,
                maxScore,
                cycles);
        chart.pack();
        chart.setVisible(true);
    }
}
