package evolution;

public interface GeneFactory {

    Gene createNew();
    Gene createNew(Gene g1, Gene g2);
}
