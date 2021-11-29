package evolution;

public interface GenomeFactory {

    Genome createNew(GeneFactory gf, int size);
    Genome createNew(GeneFactory gf, Genome g1, Genome g2);
}
