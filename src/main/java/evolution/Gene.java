package evolution;

public abstract class Gene {

    public abstract void randomize();
    public abstract void mutate();
    public abstract void print();
    public abstract Situation getSit();
    public abstract Action getAct();

    public Action act(Situation sit) {
        return getSit().applies(sit) ? getAct() : null;
    }
}
