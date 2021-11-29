package evolution;

import java.util.Random;

public class DefaultGeneFactory<Situation, Action> implements GeneFactory<Situation, Action> {

    Class geneClassName;

    public DefaultGeneFactory(Class geneClassName) {
        this.geneClassName = geneClassName;
    }

    @Override
    public Gene<Situation, Action> createNew() {
        Gene<Situation, Action> g = createFromConstructor();
        g.randomize();
        return g;
    }

    @Override
    public Gene<Situation, Action> createNew(Gene<Situation, Action> g1, Gene<Situation, Action> g2) {
        Random rand = new Random();
        if (g1 == null || g2 == null) {
            Gene<Situation, Action> g = (g1 != null ? g1 : g2);
            return rand.nextInt(2) == 0 ? createFromConstructor(g) : null;
        }
        return createFromConstructor((rand.nextInt(2) == 0 ? g1 : g2));
    }

    public Gene<Situation, Action> createFromConstructor() {
        try {
            return (Gene<Situation, Action>) geneClassName.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public Gene<Situation, Action> createFromConstructor(Gene g) {
        try {
            return (Gene<Situation, Action>) geneClassName
                    .getDeclaredConstructor(new Class[]{geneClassName}).newInstance(new Gene[]{g});
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

}
