package evolution;

public interface GeneFactory<Situation, Action> {

    Gene<Situation, Action> createNew();
    Gene<Situation, Action> createNew(Gene<Situation, Action> g1, Gene<Situation, Action> g2);
}
