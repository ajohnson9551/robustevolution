package lunarlander;

import evolution.Situation;

import java.util.Arrays;
import java.util.Random;

public class LunarLanderSituation implements Situation {

    private double alt;
    private double[] vel;
    private double ang;
    private double[] tols;

    public LunarLanderSituation(double alt, double[] vel, double ang, double[] tols) {
        this.alt = alt;
        this.vel = vel;
        this.ang = ang;
        this.tols = tols;
    }

    public LunarLanderSituation() {
        this.alt = 0.0;
        this.vel = new double[2];
        this.ang = 0;
        this.tols = new double[3];
    }

    @Override
    public LunarLanderSituation clone() {
        return new LunarLanderSituation(alt, Arrays.copyOf(vel, 2),
                ang, Arrays.copyOf(tols, 3));
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(4);
        switch(r) {
            case 0:
                alt = 400.0*rand.nextDouble();
                break;
            case 1:
                r = rand.nextInt(2);
                vel[r] = 250.0*(2*rand.nextDouble() - 1);
                break;
            case 2:
                ang = 6.28*rand.nextDouble();
                break;
            case 3:
                r = rand.nextInt(3);
                if (r < 2) {
                    tols[r] = 100.0*rand.nextDouble();
                } else {
                    tols[2] = 3.14*rand.nextDouble();
                }
                break;
        }
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        alt = 400.0*rand.nextDouble();
        for (int i = 0; i < 2; i++) {
            vel[i] = 250.0*(2*rand.nextDouble() - 1);
            tols[i] = 100.0*rand.nextDouble();
        }
        ang = 6.28*rand.nextDouble();
        tols[2] = 6.28*rand.nextDouble();
    }

    @Override
    public boolean applies(Situation external) {
        return applies((LunarLanderSituation) external);
    }

    public boolean applies(LunarLanderSituation external) {
        if (Math.abs(alt - external.getAlt()) > tols[0]) {
            return false;
        }
        for (int i = 0; i < 2; i++) {
            if (Math.abs(external.getVel()[i] - vel[i]) > tols[1]) {
                return false;
            }
        }
        return angleCompare(ang, external.getAng(), tols[2]);
    }

    public boolean angleCompare(double ang1, double ang2, double tol) {
        ang1 = ang1 % 6.28;
        ang2 = ang2 % 6.28;
        if (ang1 < 0) {
            ang1 += 6.28;
        }
        if (ang2 < 0) {
            ang2 += 6.28;
        }
        double diff1 = Math.abs(ang1 - ang2);
        ang1 = (ang1 + 3.14) % 6.28;
        ang2 = (ang2 + 3.14) % 6.28;
        double diff2 = Math.abs(ang1 - ang2);
        return Math.min(diff1, diff2) <= tol;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public void setAng(double ang) {
        this.ang = ang;
    }

    public double[] getVel() {
        return vel;
    }

    public double getAng() {
        return ang;
    }

    public void setTols(double[] tols) {
        this.tols = tols;
    }

    public double[] getTols() {
        return tols;
    }
}
