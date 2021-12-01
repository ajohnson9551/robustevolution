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
        this.tols = new double[4];
    }

    @Override
    public LunarLanderSituation clone() {
        return new LunarLanderSituation(this.alt, Arrays.copyOf(this.vel, 2),
                this.ang, Arrays.copyOf(this.tols, 4));
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(4);
        switch(r) {
            case 0:
                if (rand.nextInt(4) == 0) {
                    alt = 400.0*rand.nextDouble();
                } else {
                    alt = mutateValue(alt);
                }
                break;
            case 1:
                r = rand.nextInt(2);
                if (rand.nextInt(4) == 0) {
                    vel[r] = 200.0*(2.0*rand.nextDouble() - 1.0);
                } else {
                    vel[r] = mutateValue(vel[r]);
                }
                break;
            case 2:
                if (rand.nextInt(4) == 0) {
                    ang = 6.28*rand.nextDouble();
                } else {
                    ang += (3.14/8.0)*(2.0*rand.nextDouble() - 1.0);
                }
                break;
            case 3:
                r = rand.nextInt(4);
                if (rand.nextInt(4) == 0) {
                    if (r > 0 && r < 3) {
                        tols[r] = 100.0*rand.nextDouble();
                    } else if (r == 0) {
                        tols[0] = 100.0*rand.nextDouble();
                    } else {
                        tols[3] = 3.14*rand.nextDouble();
                    }
                } else {
                    if (r < 3) {
                        tols[r] = mutateValue(tols[r]);
                    } else {
                        tols[3] += (3.14/4.0)*(2.0*rand.nextDouble() - 1.0);
                    }
                }
                break;
        }
    }

    public double mutateValue(double x) {
        Random rand = new Random();
        return x * (1.0 + (0.1*(2.0*rand.nextDouble() - 1.0)));
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        alt = 400.0*rand.nextDouble();
        tols[0] = 100.0*rand.nextDouble();
        for (int i = 0; i < 2; i++) {
            vel[i] = 200.0*(2*rand.nextDouble() - 1);
            tols[1 + i] = 100.0*rand.nextDouble();
        }
        ang = 6.28*rand.nextDouble();
        tols[3] = 3.14*rand.nextDouble();
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
            if (Math.abs(external.getVel()[i] - vel[i]) > tols[1 + i]) {
                return false;
            }
        }
        return angleCompare(ang, external.getAng(), tols[3]);
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
