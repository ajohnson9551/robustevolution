package evolution;

public interface GenomeFactory<Situation, Action> {

    Genome<Situation, Action> createNew(GeneFactory<Situation, Action> gf, int size);
    Genome<Situation, Action> createNew(GeneFactory<Situation, Action> gf, Genome<Situation, Action> g1, Genome<Situation, Action> g2);
}
