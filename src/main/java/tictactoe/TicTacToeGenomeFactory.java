package tictactoe;

import evolution.Gene;
import evolution.GeneFactory;
import evolution.Genome;
import evolution.GenomeFactory;

@Deprecated
public class TicTacToeGenomeFactory implements GenomeFactory<int[], Integer> {

    @Override
    public Genome<int[], Integer> createNew(GeneFactory<int[], Integer> gf, int size) {
        Genome<int[], Integer> g = new TicTacToeGenome();
        for (int i = 0; i < size; i++) {
            g.addGene(gf.createNew());
        }
        return g;
    }

    @Override
    public Genome<int[], Integer> createNew(GeneFactory<int[], Integer> gf, Genome<int[], Integer> g1, Genome<int[], Integer> g2) {
        Genome<int[], Integer> g = new TicTacToeGenome();
        int size1 = g1.getGenomeSize();
        int size2 = g2.getGenomeSize();
        for (int i = 0; i < Math.max(size1, size2); i++) {
            Gene<int[], Integer> e1 = g1.getGene(i);
            Gene<int[], Integer> e2 = g2.getGene(i);
            g.addGene(gf.createNew(e1, e2));
        }
        return g;
    }
}
