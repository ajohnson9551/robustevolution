package evolution;

public abstract class AffineGenomeFactory implements GenomeFactory {

    private final int numInputs;
    private final int numOutputs;
    private final Class affineGenomeClassName;

    public AffineGenomeFactory(Class affineGenomeClassName, int numInputs, int numOutputs) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.affineGenomeClassName = affineGenomeClassName;
    }

    @Override
    public Genome createNew(GeneFactory gf, int size) {
        // ignores gf and size
        try {
            Integer[] input = new Integer[]{numInputs, numOutputs};
            return (Genome) affineGenomeClassName.getDeclaredConstructor(Integer.class, Integer.class)
                    .newInstance(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
