package evolution;

public class DefaultGenomeFactory<Situation, Action> implements GenomeFactory<Situation, Action> {

    Class genomeClassName;

    public DefaultGenomeFactory(Class genomeClassName) {
        this.genomeClassName = genomeClassName;
    }

    @Override
    public Genome<Situation, Action>createNew(GeneFactory<Situation, Action> gf, int size) {
        Genome<Situation, Action> g = createFromConstructor();
        for (int i = 0; i < size; i++) {
            g.addGene(gf.createNew());
        }
        return g;
    }

    @Override
    public Genome<Situation, Action> createNew(GeneFactory<Situation, Action> gf, Genome<Situation, Action> g1, Genome<Situation, Action> g2) {
        Genome<Situation, Action> g = createFromConstructor();
        int size1 = g1.getGenomeSize();
        int size2 = g2.getGenomeSize();
        for (int i = 0; i < Math.max(size1, size2); i++) {
            Gene<Situation, Action> e1 = g1.getGene(i);
            Gene<Situation, Action> e2 = g2.getGene(i);
            g.addGene(gf.createNew(e1, e2));
        }
        return g;
    }

    public Genome<Situation, Action> createFromConstructor() {
        try {
            return (Genome<Situation, Action>) genomeClassName.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
