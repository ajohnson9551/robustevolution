package evolution;

import java.util.Random;

public abstract class TreeGenome extends Genome {

    private TreeGene root;
    private final int depth;

    @Override
    public Action advise(Situation sit) {
        return root.act(sit);
    }

    public int getDepth() {
        return depth;
    }

    public TreeGenome(int depth) {
        this.depth = depth;
    }

    @Override
    public int getGenomeSize() {
        return (int) Math.pow(2, depth);
    }

    public TreeGene getRoot() {
        return root;
    }

    public void setRoot(TreeGene root) {
        this.root = root;
    }

    public void mutate() {
        // randomly chooses which node to mutate
        Random rand = new Random();
        int mutateNode = rand.nextInt((int) Math.pow(2, this.getDepth()) - 1);
        this.getRoot().mutate(mutateNode);
    }
}
