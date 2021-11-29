package tictactoe;

import evolution.Fitness;
import evolution.Genome;

public class TicTacToeFitness implements Fitness<int[], Integer> {

    private final int NUM_GAMES;
    private final TicTacToeGame game;
    private final double GENOME_SIZE_PENALTY;

    public TicTacToeFitness(int NUM_GAMES, TicTacToeGame game, double GENOME_SIZE_PENALTY) {
        this.NUM_GAMES = NUM_GAMES;
        this.game = game;
        this.GENOME_SIZE_PENALTY = GENOME_SIZE_PENALTY;
    }

    @Override
    public void score(Genome<int[], Integer> g) {
        int score = 0;
        for (int i = 0; i < NUM_GAMES; i++) {
            score += game.play(g);
        }
        g.setScore(score - (int) (((double)g.getGenomeSize())*GENOME_SIZE_PENALTY));
    }

    @Override
    public int getMaxScore() {
        return NUM_GAMES;
    }
}
