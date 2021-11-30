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
        System.out.println("Altitude: " + round(lookFor.getAlt())
                + ", tolerance " + round(lookFor.getTols()[0]));
        System.out.println("Velocity: (" + round(lookFor.getVel()[0]) + "," + round(lookFor.getVel()[1])
                + "), tolerances (" + round(lookFor.getTols()[1]) + "," + round(lookFor.getTols()[2]) + ")");
        System.out.println("Angle: " + round(lookFor.getAng())
                + ", tolerance " + round(lookFor.getTols()[3]));
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
