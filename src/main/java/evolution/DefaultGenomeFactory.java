package evolution;

public class DefaultGenomeFactory implements GenomeFactory {

    private Class genomeClassName;

    public DefaultGenomeFactory(Class genomeClassName) {
        this.genomeClassName = genomeClassName;
    }

    @Override
    public Genome createNew(GeneFactory gf, int size) {
        Genome g = createFromConstructor();
        for (int i = 0; i < size; i++) {
            g.addGene(gf.createNew());
        }
        return g;
    }

    @Override
    public Genome createNew(GeneFactory gf, Genome g1, Genome g2) {
        Genome g = createFromConstructor();
        int size1 = g1.getGenomeSize();
        int size2 = g2.getGenomeSize();
        for (int i = 0; i < Math.max(size1, size2); i++) {
            Gene e1 = g1.getGene(i);
            Gene e2 = g2.getGene(i);
            g.addGene(gf.createNew(e1, e2));
        }
        return g;
    }

    public Genome createFromConstructor() {
        try {
            return (Genome) genomeClassName.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
