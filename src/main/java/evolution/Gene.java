package evolution;

public interface Gene<Situation, Action> {

    Action act(Situation sit);
    void randomize();
    void mutate();
    void print();
}
