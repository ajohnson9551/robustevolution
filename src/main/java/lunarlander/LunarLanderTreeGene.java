package lunarlander;

import evolution.Action;
import evolution.Gene;
import evolution.Situation;
import evolution.TreeGene;
import tictactoe.TicTacToeTreeGene;

import java.util.Random;

public class LunarLanderTreeGene extends TreeGene {

    private LunarLanderSituation lookFor;
    private LunarLanderAction doThis;

    private LunarLanderAction rejectedDoThis;

    public LunarLanderTreeGene(LunarLanderTreeGene g) {
        this.doThis = g.doThis.clone();
        this.lookFor = g.lookFor.clone();
        this.rejectedDoThis = g.rejectedDoThis.clone();
    }

    public LunarLanderTreeGene() {
        lookFor = new LunarLanderSituation();
        doThis = new LunarLanderAction();
        rejectedDoThis = new LunarLanderAction();
    }

    @Override
    public void randomize() {
        lookFor.randomize();
        doThis.randomize();
        rejectedDoThis.randomize();
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(3);
        if (!isLeaf() || r == 0) {
            lookFor.mutate();
        } else if (r == 1) {
            doThis.mutate();
        } else {
            rejectedDoThis.mutate();
        }
    }

    @Override
    public void print() {
        System.out.println("Altitude range: " + round(lookFor.getAlt()[0])
                + " to " + round(lookFor.getAlt()[1]));
        System.out.println("VelX range: " + round(lookFor.getVelX()[0])
                + " to " + round(lookFor.getVelX()[1]));
        System.out.println("VelY range: " + round(lookFor.getVelY()[0])
                + " to " + round(lookFor.getVelY()[1]));
        System.out.println("Angle range: " + round(lookFor.getAng()[0])
                + " to " + round(lookFor.getAng()[1]));
        if (this.isLeaf()) {
            System.out.println("Thrust = " + doThis.getThrust() + ", Turn = " + doThis.getTurn());
        } else {
            System.out.println("Accept Gene:");
            this.getAppliesGene().print();
            System.out.println("Reject Gene:");
            this.getRejectsGene().print();
        }
    }

    @Override
    public Situation getSit() {
        return lookFor;
    }

    @Override
    public Action getAct() {
        return doThis;
    }

    public double round(double x) {
        return Math.round(10*x)/10.0;
    }

    @Override
    public Action getRejectsAct() {
        return rejectedDoThis;
    }

    @Override
    public TreeGene createAverageWith(TreeGene g) {
        // lazy, just returns copy of this or copy of g
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            return new LunarLanderTreeGene((LunarLanderTreeGene) g);
        } else {
            return new LunarLanderTreeGene(this);
        }
    }
}
