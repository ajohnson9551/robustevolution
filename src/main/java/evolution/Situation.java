package evolution;

public interface Situation {

    void mutate();
    void randomize();
    boolean applies(Situation external);
}
