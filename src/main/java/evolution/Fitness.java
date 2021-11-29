package evolution;

public interface Fitness {

    void score(Genome g);
    int getMaxScore();
}
