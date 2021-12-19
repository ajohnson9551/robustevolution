package lunarlander;

import evolution.AffineGenomeFactory;
import evolution.GeneFactory;
import evolution.Genome;

public class LunarLanderAffineGenomeFactory extends AffineGenomeFactory {
    public LunarLanderAffineGenomeFactory(Class<LunarLanderAffineGenome> lunarLanderAffineGenomeClass,
                                          int numInputs, int numOutputs) {
        super(lunarLanderAffineGenomeClass, numInputs, numOutputs);
    }

    @Override
    public Genome createNew(GeneFactory gf, Genome g1, Genome g2) {
        return ((LunarLanderAffineGenome) g1).createAverageWith((LunarLanderAffineGenome) g2);
    }
}
