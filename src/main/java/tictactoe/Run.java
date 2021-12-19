package tictactoe;

import evolution.*;

public class Run {

    public static void main(String[] args) {
        int[][] data;
        int maxScore;

        int cycles = 50000; // must be at least 100

        boolean useTreeGenome = true;
        int treeDepth = 4;

        int numGames = 200;
        double genomeSizePenalty = 0;

        int popSize = 50;
        int numBabies = 5;
        int bestBreederPairs = 4;
        int okayBreederPairs = 2;
        double okayThreshhold = 0.4;
        int cull = 1;
        int randomBreederPairs = 1;
        boolean elitism = false;

        double mutationRate = 0.6;
        int maxMutations = 4;
        int initialGenomeLength = 10;

        GeneFactory gf;
        GenomeFactory gnf;
        if (!useTreeGenome) {
            gf = new DefaultGeneFactory(TicTacToeGene.class);
            gnf = new DefaultGenomeFactory(TicTacToeGenome.class);
        } else {
            gf = new DefaultTreeGeneFactory(TicTacToeTreeGene.class);
            gnf = new DefaultTreeGenomeFactory(TicTacToeTreeGenome.class);
            initialGenomeLength = treeDepth;
            genomeSizePenalty = 0;
        }
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
