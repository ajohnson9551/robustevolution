package tictactoe;

import evolution.Gene;
import evolution.GeneFactory;

import java.util.Random;

@Deprecated
public class TicTacToeGeneFactory implements GeneFactory<int[], Integer> {

    @Override
    public Gene<int[], Integer> createNew() {
        Gene<int[], Integer> g = new TicTacToeGene();
        g.randomize();
        return g;
    }

    @Override
    public Gene<int[], Integer> createNew(Gene<int[], Integer> g1, Gene<int[], Integer> g2) {
        Random rand = new Random();
        if (g1 == null || g2 == null) {
            TicTacToeGene g = (TicTacToeGene) (g1 != null ? g1 : g2);
            return rand.nextInt(2) == 0 ? new TicTacToeGene(g) : null;
        }
        return new TicTacToeGene((TicTacToeGene) (rand.nextInt(2) == 0 ? g1 : g2));
    }
}
