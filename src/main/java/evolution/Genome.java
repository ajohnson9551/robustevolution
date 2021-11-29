package evolution;

import java.util.ArrayList;
import java.util.List;

public abstract class Genome {

    protected List<Gene> genes = new ArrayList<>();
    private int score;

    public abstract void print();
    public abstract void mutate();

    public Action advise(Situation sit) {
        for (Gene g : genes) {
            Action adv = g.act(sit);
            if (adv != null) {
                return adv;
            }
        }
        return null;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void addGene(Gene g) {
        if (g != null) {
            genes.add(g);
        }
    }

    public Gene getGene(int index) {
        if (index >= genes.size()) {
            return null;
        }
        return genes.get(index);
    }

    public int getGenomeSize() {
        return genes.size();
    }
}