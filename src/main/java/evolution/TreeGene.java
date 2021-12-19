package evolution;

import java.util.Random;

public abstract class TreeGene extends Gene {

    private TreeGene appliesGene;
    private TreeGene rejectsGene;

    public abstract Action getRejectsAct();

    public TreeGene getAppliesGene() {
        return appliesGene;
    }

    public TreeGene getRejectsGene() {
        return rejectsGene;
    }

    public abstract TreeGene createAverageWith(TreeGene g);

    public void setAppliesGene(TreeGene g) {
        this.appliesGene = g;
    }

    public void setRejectsGene(TreeGene g) {
        this.rejectsGene = g;
    }

    public boolean isLeaf() {
        return appliesGene == null;
    }

    public void mutate(int node) {
        // mutates at this node
        Random rand = new Random();
        if (node == 0) {
            this.mutate();
        } else if (node % 2 == 0) {
            this.getAppliesGene().mutate((node/2) - 1);
        } else {
            this.getRejectsGene().mutate((node - 1)/2);
        }
    }

    @Override
    public Action act(Situation sit) {
        if (this.getSit().applies(sit)) {
            if (appliesGene == null) {
                return this.getAct();
            } else {
                return appliesGene.act(sit);
            }
        } else {
            if (rejectsGene == null) {
                return this.getRejectsAct();
            } else {
                return rejectsGene.act(sit);
            }
        }
    }
}
