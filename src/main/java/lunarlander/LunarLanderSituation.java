package lunarlander;

import evolution.Situation;

import java.util.Arrays;
import java.util.Random;

public class LunarLanderSituation implements Situation {

    private double[] alt;
    private double[] velX;
    private double[] velY;
    private double[] ang;

    public LunarLanderSituation(double[] alt, double[] velX, double[] velY, double[] ang) {
        this.alt = alt;
        this.velX = velX;
        this.velY = velY;
        this.ang = ang;
    }

    public LunarLanderSituation() {
        this.alt = new double[2];
        this.velX = new double[2];
        this.velY = new double[2];
        this.ang = new double[2];
    }

    @Override
    public LunarLanderSituation clone() {
        return new LunarLanderSituation(Arrays.copyOf(this.alt, 2),
                Arrays.copyOf(this.velX, 2),
                Arrays.copyOf(this.velY, 2),
                Arrays.copyOf(this.ang, 2));
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(4);
        int j = rand.nextInt(2);
        switch(r) {
            case 0:
                alt[j] = mutateValue(alt[j]);
                break;
            case 1:
                velX[j] = mutateValue(velX[j]);
                break;
            case 2:
                velY[j] = mutateValue(velY[j]);
                break;
            case 3:
                ang[j] = mutateAngle(ang[j]);
                break;
        }
    }

    public double mutateValue(double x) {
        Random rand = new Random();
        return x * (1.0 + (0.1*(2.0*rand.nextDouble() - 1.0)));
    }
    public double mutateAngle(double x) {
        Random rand = new Random();
        return x + (3.14/8) * (2.0*rand.nextDouble() - 1.0);
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        for (int j = 0; j < 2; j++) {
            alt[j] = 300.0*rand.nextDouble();
            velX[j] = 150.0*(2*rand.nextDouble() - 1);
            velY[j] = 100.0*(2*rand.nextDouble() - 1);
            ang[j] = 6.28*rand.nextDouble();
        }
    }

    @Override
    public boolean applies(Situation external) {
        return applies((LunarLanderSituation) external);
    }

    public boolean applies(LunarLanderSituation external) {
        if (alt[0] > external.getAlt()[0] || alt[1] < external.getAlt()[1]) {
            return false;
        }
        if (velX[0] > external.getVelX()[0] || velX[1] < external.getVelX()[1]) {
            return false;
        }
        if (velY[0] > external.getVelY()[0] || velY[1] < external.getVelY()[1]) {
            return false;
        }
        return angleCompare(ang, external.getAng());
    }

    public boolean angleCompare(double[] angBig, double[] angSmall) {
        double min = goodAngMod(angBig[0]);
        double max = goodAngMod(angBig[1]);
        double low = goodAngMod(angSmall[0]);
        double high = goodAngMod(angSmall[1]);
        return clockwise(min, low, high) && clockwise(low, high, max);
    }

    public boolean clockwise(double a, double b, double c) {
        b = goodAngMod(b - a);
        c = goodAngMod(c - a);
        return b <= c;
    }

    public double goodAngMod(double x) {
        double y = x % 6.28;
        return y < 0.0 ? y + 6.28 : y;
    }

    public double[] getAlt() {
        return alt;
    }

    public double[] getVelX() {
        return velX;
    }

    public double[] getVelY() {
        return velY;
    }

    public double[] getAng() {
        return ang;
    }

    public void setVel(int i, int j, double vel) {
        switch (i) {
            case 0 -> velX[j] = vel;
            case 1 -> velY[j] = vel;
        }
    }

    public void setAlt(int j, double alt) {
        this.alt[j] = alt;
    }

    public void setAng(int j, double ang) {
        this.ang[j] = ang;
    }
}
