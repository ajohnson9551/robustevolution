package lunarlander;

import evolution.Action;
import evolution.Gene;
import evolution.Situation;

import java.util.Random;

public class LunarLanderGene extends Gene {

    private LunarLanderSituation lookFor;
    private LunarLanderAction doThis;

    public LunarLanderGene(LunarLanderGene g) {
        this.doThis = g.doThis.clone();
        this.lookFor = g.lookFor.clone();
    }

    public LunarLanderGene() {
        lookFor = new LunarLanderSituation();
        doThis = new LunarLanderAction();
    }

    @Override
    public void randomize() {
        lookFor.randomize();
        doThis.randomize();
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            lookFor.mutate();
        } else {
            doThis.mutate();
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
        System.out.println("Thrust = " + doThis.getThrust() + ", Turn = " + doThis.getTurn());
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
}
