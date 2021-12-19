package evolution;

public class DefaultTreeGeneFactory implements TreeGeneFactory {

    private Class geneClassName;

    public DefaultTreeGeneFactory(Class geneClassName) {
        this.geneClassName = geneClassName;
    }

    @Override
    public TreeGene createNew() {
        TreeGene g = createFromConstructor();
        g.randomize();
        return g;
    }

    @Override
    public Gene createNew(Gene g) {
        return this.createNew((TreeGene) g);
    }

    @Override
    public Gene createNew(Gene g1, Gene g2) {
        return this.createNew((TreeGene) g1, (TreeGene) g2);
    }

    @Override
    public TreeGene createNew(TreeGene g) {
        return createFromConstructor(g);
    }

    @Override
    public TreeGene createNew(TreeGene g1, TreeGene g2) {
        if (g1 == null) {
            return createFromConstructor(g2);
        } else if (g2 == null) {
            return createFromConstructor(g1);
        }
        return g1.createAverageWith(g2);
    }

    public TreeGene createFromConstructor() {
        try {
            return (TreeGene) geneClassName.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public TreeGene createFromConstructor(TreeGene g) {
        TreeGene[] input = new TreeGene[1];
        input[0] = g;
        try {
            return (TreeGene) geneClassName
                    .getDeclaredConstructor(new Class[]{geneClassName}).newInstance(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
