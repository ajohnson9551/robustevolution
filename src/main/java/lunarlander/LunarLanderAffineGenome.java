package lunarlander;

import evolution.Action;
import evolution.AffineGenome;
import evolution.Genome;
import evolution.Situation;

import java.util.Random;

public class LunarLanderAffineGenome extends AffineGenome {

    @Override
    public Action advise(Situation sit) {
        double[] sitArray = new double[4];
        sitArray[0] = ((LunarLanderSituation) sit).getAlt()[0];
        sitArray[1] = ((LunarLanderSituation) sit).getAng()[0];
        sitArray[2] = ((LunarLanderSituation) sit).getVelX()[0];
        sitArray[3] = ((LunarLanderSituation) sit).getVelY()[0];
        int i = maxIndex(computeAffine(sitArray));
        return new LunarLanderAction(i % 2 == 0, i % 3 - 1);
    }

    public LunarLanderAffineGenome(Integer numInputs, Integer numOutputs) {
        super(numInputs, numOutputs);
    }

    public AffineGenome createAverageWith(AffineGenome g2) {
        Random rand = new Random();
        AffineGenome g = new LunarLanderAffineGenome(this.numInputs, this.numOutputs);
        for (int i = 0; i < numOutputs; i++) {
            for (int j = 0; j < numInputs; j++) {
                switch (rand.nextInt(3)) {
                    case 0 -> g.weights[i][j] = g2.weights[i][j];
                    case 1 -> g.weights[i][j] = this.weights[i][j];
                    case 2 -> g.weights[i][j] = (this.weights[i][j] + g2.weights[i][j]) / 2.0;
                }
            }
            switch (rand.nextInt(3)) {
                case 0 -> g.offset[i] = g2.offset[i];
                case 1 -> g.offset[i] = this.offset[i];
                case 2 -> g.offset[i] = (this.offset[i] + g2.offset[i])/2.0;
            }
        }
        return g;
    }
}
