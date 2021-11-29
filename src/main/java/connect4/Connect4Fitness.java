package connect4;

import evolution.Fitness;
import evolution.Genome;

public class Connect4Fitness implements Fitness<int[], Integer> {

    private final double GENOME_SIZE_PENALTY;
    private final int NUM_GAMES;
    private final Connect4Game game;


    public Connect4Fitness(int numGames, Connect4Game connect4Game, double genomeSizePenalty) {
        this.NUM_GAMES = numGames;
        this.GENOME_SIZE_PENALTY = genomeSizePenalty;
        this.game = connect4Game;
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
