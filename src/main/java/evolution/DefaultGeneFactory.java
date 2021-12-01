package evolution;

import java.util.Random;

public class DefaultGeneFactory implements GeneFactory {

    private Class geneClassName;

    public DefaultGeneFactory(Class geneClassName) {
        this.geneClassName = geneClassName;
    }

    @Override
    public Gene createNew() {
        Gene g = createFromConstructor();
        g.randomize();
        return g;
    }

    @Override
    public Gene createNew(Gene g1, Gene g2) {
        Random rand = new Random();
        if (g1 == null || g2 == null) {
            Gene g = (g1 != null ? g1 : g2);
            return rand.nextInt(2) == 0 ? createFromConstructor(g) : null;
        }
        return createFromConstructor((rand.nextInt(2) == 0 ? g1 : g2));
    }

    public Gene createFromConstructor() {
        try {
            return (Gene) geneClassName.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public Gene createFromConstructor(Gene g) {
        Gene[] input = new Gene[1];
        input[0] = g;
        try {
            return (Gene) geneClassName
                    .getDeclaredConstructor(new Class[]{geneClassName}).newInstance(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

}
