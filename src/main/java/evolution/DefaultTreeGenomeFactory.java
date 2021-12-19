package evolution;

import java.util.Random;

public class DefaultTreeGenomeFactory implements TreeGenomeFactory {

    private Class genomeClassName;

    public DefaultTreeGenomeFactory(Class genomeClassName) {
        this.genomeClassName = genomeClassName;
    }

    @Override
    public TreeGenome createNew(TreeGeneFactory gf, int depth) {
        TreeGenome g = createFromConstructor(depth);
        TreeGene root = gf.createNew();
        if (depth > 1) {
            root.setAppliesGene(this.createNew(gf, depth - 1).getRoot());
            root.setRejectsGene(this.createNew(gf, depth - 1).getRoot());
        }
        g.setRoot(root);
        return g;
    }

    public TreeGene createNewRoot(TreeGene g1, TreeGene g2) {
        TreeGene g = g1.createAverageWith(g2);
        if(!g1.isLeaf()) {
            // average left and right
            TreeGene appliesGene = this.createNewRoot(g1.getAppliesGene(), g2.getAppliesGene());
            TreeGene rejectsGene = this.createNewRoot(g1.getRejectsGene(), g2.getRejectsGene());
            g.setAppliesGene(appliesGene);
            g.setRejectsGene(rejectsGene);
        }
        return g;
    }

    @Override
    public TreeGenome createNew(TreeGenome g1, TreeGenome g2) {
        if (g1.getDepth() != g2.getDepth()) {
            throw new IllegalArgumentException();
        }
        TreeGenome g = createFromConstructor(g1.getDepth());
        g.setRoot(this.createNewRoot(g1.getRoot(), g2.getRoot()));
        return g;
    }

    public TreeGenome createFromConstructor(int depth) {
        Integer[] input = {depth};
        try {
            return (TreeGenome) genomeClassName.getDeclaredConstructor(new Class[]{Integer.class}).newInstance(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    @Override
    public Genome createNew(GeneFactory gf, int size) {
        return this.createNew((TreeGeneFactory) gf, size);
    }

    @Override
    public Genome createNew(GeneFactory gf, Genome g1, Genome g2) {
        return this.createNew((TreeGenome) g1, (TreeGenome) g2);
    }
}
