package evolution;

public interface Genome<Situation, Action> {

    void setScore(int score);
    int getScore();
    Action advise(Situation sit);
    void addGene(Gene<Situation, Action> g);
    Gene<Situation, Action> getGene(int index);
    int getGenomeSize();
    void print();
    void mutate();
}