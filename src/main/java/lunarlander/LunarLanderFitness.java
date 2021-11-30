package lunarlander;

import evolution.Fitness;
import evolution.Genome;

public class LunarLanderFitness implements Fitness {

    private final double GENOME_SIZE_PENALTY;
    private final int NUM_GAMES;
    private final boolean RANDOM_START;

    public LunarLanderFitness(int numGames, double genomeSizePenalty, boolean randomStart) {
        this.NUM_GAMES = numGames;
        this.GENOME_SIZE_PENALTY = genomeSizePenalty;
        this.RANDOM_START = randomStart;
    }

    public int score(Genome g, boolean yep) {
        LunarLanderGame game = new LunarLanderGame(yep, yep, false, RANDOM_START);
        int score = game.play((LunarLanderGenome) g).computeScore();
        return (int) (score - g.getGenomeSize()*GENOME_SIZE_PENALTY);
    }

    @Override
    public void score(Genome g) {
        int score = 0;
        for (int i = 0; i < NUM_GAMES; i++) {
            LunarLanderGame game = new LunarLanderGame(false, false, false, RANDOM_START);
            score += game.play((LunarLanderGenome) g).computeScore();
        }
        g.setScore((int) (score - g.getGenomeSize()*GENOME_SIZE_PENALTY));
    }

    @Override
    public int getMaxScore() {
        return NUM_GAMES*(110);
    }
}
