package tictactoe;

import evolution.*;

public class Run {

    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 10000; // must be at least 100

        int numGames = 200;
        double genomeSizePenalty = 1;

        int popSize = 80;
        int numBabies = 8;
        int bestBreederPairs = 3;
        int okayBreederPairs = 2;
        double okayThreshhold = 0.3;
        int cull = 5;
        int randomBreederPairs = 1;
        boolean elitism = false;

        double mutationRate = 0.2;
        int maxMutations = 6;
        int initialGenomeLength = 10;

        GeneFactory gf = new DefaultGeneFactory(TicTacToeGene.class);
        GenomeFactory gnf = new DefaultGenomeFactory(TicTacToeGenome.class);
        Fitness fit = new TicTacToeFitness(numGames, new TicTacToeGame(), genomeSizePenalty);
        maxScore = fit.getMaxScore();
        Population pop = new Population();
        pop.setDependencies(gf, gnf, fit);
        pop.setPopulationParams(popSize, numBabies, bestBreederPairs, okayBreederPairs, cull,
                randomBreederPairs, elitism, okayThreshhold);
        pop.setGeneticsParams(mutationRate, maxMutations, initialGenomeLength);
        pop.genPop();
        data = pop.evolve(cycles, true);
        pop.getAllTimeChampion().print();

        DataLineChart chart = new DataLineChart(
                "Tic Tac Toe Evolution Data",
                "Score from " + numGames + " Games and Genome Size",
                data,
                0,
                maxScore,
                10,
                cycles);
        chart.pack();
        chart.setVisible(true);

        System.out.println("Max score = " + pop.getAllTimeMaxScore());
    }
}
