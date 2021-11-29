package evolution;

public interface Situation {

    boolean applies(Situation external);
}
