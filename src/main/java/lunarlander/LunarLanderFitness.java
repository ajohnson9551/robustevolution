package lunarlander;

import evolution.Fitness;
import evolution.Genome;

public class LunarLanderFitness implements Fitness {

    private final double GENOME_SIZE_PENALTY;
    private final int NUM_GAMES;
    private final int START_MODE;

    public LunarLanderFitness(int numGames, double genomeSizePenalty, int startMode) {
        this.NUM_GAMES = numGames;
        this.GENOME_SIZE_PENALTY = genomeSizePenalty;
        this.START_MODE = startMode;
    }

    @Override
    public void score(Genome g) {
        double score = 0.0;
        for (int i = 0; i < NUM_GAMES; i++) {
            LunarLanderGame game = new LunarLanderGame(false, false, false, START_MODE, i);
            score += game.play((LunarLanderGenome) g).computeScore();
        }
        g.setScore((int) (score - g.getGenomeSize()*GENOME_SIZE_PENALTY));
    }

    @Override
    public int getMaxScore() {
        return NUM_GAMES*(105);
    }
}
