package evolution;

public interface Fitness<Situation, Action> {

    void score(Genome<Situation, Action> g);
    int getMaxScore();
}
