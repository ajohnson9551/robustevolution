package evolution;

import java.util.Random;

public abstract class AffineGenome extends Genome {

    public double[][] weights;
    public double[] offset;

    protected final int numInputs;
    protected final int numOutputs;

    @Override
    public abstract Action advise(Situation sit);

    public AffineGenome(Integer numInputs, Integer numOutputs) {
        this.weights = new double[numOutputs][numInputs];
        this.offset = new double[numOutputs];
        this.randomize();
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
    }

    @Override
    public int getGenomeSize() {
        // affine genomes do not have genes, they are just an affine mapping
        return 1;
    }

    @Override
    public void print() {
        // it doesn't really make as much sense printing this genome, won't be readable
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt((numOutputs+1)*numInputs);
        if (r < numInputs) {
            offset[r] = randVal();
        } else {
            r = r - numInputs;
            weights[r % numOutputs][r / numOutputs] = randVal();
        }
    }

    public void randomize() {
        for (int i = 0; i < numOutputs; i++) {
            for (int j = 0; j < numInputs; j++) {
                weights[i][j] = randVal();
            }
            offset[i] = randVal();
        }
    }

    public int maxIndex(double[] y) {
        int i = 0;
        double max = Double.MIN_VALUE;
        for (int j = 0; j < y.length; j++) {
            if (y[j] > max) {
                max = y[j];
                i = j;
            }
        }
        return i;
    }

    public double randVal() {
        Random rand = new Random();
        return 2.0*rand.nextDouble() - 1.0;
    }

    public double[] computeAffine(double[] x) {
        double[] y = new double[numOutputs];
        for (int i = 0; i < numOutputs; i++) {
            for (int j = 0; j < numInputs; j++) {
                y[i] += weights[i][j]*x[j];
            }
            y[i] += offset[i];
        }
        return y;
    }
}
