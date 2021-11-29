package evolution;

import java.util.Comparator;

public class GenomeComparator implements Comparator<Genome> {

    @Override
    public int compare(Genome g1, Genome g2) {
        return g1.getScore() - g2.getScore();
    }
}
