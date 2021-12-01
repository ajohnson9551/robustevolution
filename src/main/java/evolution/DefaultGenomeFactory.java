package evolution;

import java.util.Random;

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
        // now with small chance to take both, small chance to take neither
        Random rand = new Random();
        Genome g = createFromConstructor();
        int size1 = g1.getGenomeSize();
        int size2 = g2.getGenomeSize();
        for (int i = 0; i < Math.max(size1, size2); i++) {
            Gene e1 = g1.getGene(i);
            Gene e2 = g2.getGene(i);
            double r = rand.nextDouble();
            if (r < 0.01 && e1 != null && e2 != null) {
                g.addGene(gf.createNew(e1, null));
                g.addGene(gf.createNew(e2, null));
            } else if (r > 0.99) {
                g.addGene(null);
            } else {
                g.addGene(gf.createNew(e1, e2));
            }
        }
        if (g.getGenomeSize() == 0) {
            g.addGene(gf.createNew());
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
