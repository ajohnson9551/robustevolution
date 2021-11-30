package lunarlander;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LunarLanderGame implements KeyListener {

    // constants
    static final double thrust = 10000.0;
    static final double startFuel = 100.0;
    static final double fuelConsumptionRate = 7.0;
    static final double turnRate = 3.0;
    static final double fuelDensity = 1.0;
    static final double shipMass = 100.0;
    static final double gravity = 5000.0;
    double[] startPos = {100.0, 400.0};
    double[] startVel = {150.0, 0.0};
    double startAng = 3.1416*3/2;
    static final int tickSpeed = 50; // ticks per second, when user is controlling, must be <1000 and 1000 div by this
    static final int ticksBeforeThink = 10; // how many ticks until computer thinks

    // ticks
    boolean ticking;

    // data
    double fuel;
    double[] pos;
    double[] vel;
    double[] acc = new double[2];
    double ang; // 0 = straight up, measured clockwise in radians
    double cos = Math.cos(startAng);
    double sin = Math.sin(startAng);

    LunarLanderSituation sit = new LunarLanderSituation();

    // controls
    boolean thrusting;
    int turning; // 0 = stationary, -1 = left, 1 = right

    // drawing
    boolean drawing;
    int width = 800;
    int height = 600;
    Frame frame;
    LunarLanderCanvas canvas;

    public static void main (String[] args) {
        // user can play game
        LunarLanderGame llg = new LunarLanderGame(true,true, true, false);
        System.out.println(llg.play(null).computeScore());
    }

    public LunarLanderGame(boolean draw, boolean tick, boolean playerControl, boolean randomStart) {
        if (randomStart) {
            randomizeStart();
        }
        fuel = startFuel;
        pos = Arrays.copyOf(startPos, 2);
        vel = Arrays.copyOf(startVel, 2);
        ang = startAng;
        drawing = draw;
        ticking = tick;
        if (draw) {
            frame = new Frame("Lunar Lander Game");
            canvas = new LunarLanderCanvas(width, height, startPos, vel);
            frame.add(canvas);
            frame.setLayout(null);
            frame.setSize(width, height);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent we){
                    System.exit(0);
                }
            });
            if (playerControl) {
                canvas.addKeyListener(this);
            }
        }
    }

    public LunarLanderScore play(LunarLanderGenome g) {
        // if genome is null, gives player control
        long lastTick = 0L;
        int tickStep = 1000 / tickSpeed;
        int sinceLastThink = 0;
        long t0, t1;
        while (true) {
            t0 = System.currentTimeMillis();
            if (!ticking || lastTick > tickStep) {
                if (g != null && sinceLastThink >= ticksBeforeThink) {
                    updateSituation();
                    sinceLastThink = 0;
                    doThink(g);
                }
                lastTick -= tickStep;
                doTick();
                sinceLastThink++;
                if (checkIfGameOver()) {
                    break;
                }
            }
            t1 = System.currentTimeMillis();
            lastTick += (int) (t1 - t0);
        }
        LunarLanderScore llscore = new LunarLanderScore(fuel, getSpeed());
        // freeze canvas
        if (drawing) {
            canvas.removeKeyListener(this);
            acc[0] = 0;
            acc[1] = 0;
            canvas.updateInfo(pos, cos, sin, fuel);
            canvas.setScore(llscore.computeScore());
            canvas.repaint();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.dispose();
        }
        return llscore;
    }

    public double getSpeed() {
        return Math.sqrt(vel[0]*vel[0] + vel[1]*vel[1]);
    }

    public boolean checkIfGameOver() {
        return pos[1] <= 0;
    }

    public void updateSituation() {
        // note vel is passed by reference since it is an array
        sit.setAlt(pos[1]);
        sit.setAng(ang);
    }

    public void doThink(LunarLanderGenome g) {
        LunarLanderAction act = (LunarLanderAction) g.advise(sit);
        if (act != null) {
            turning = act.getTurn();
            toggleEngine(act.getThrust());
        } else {
            turning = 0;
            toggleEngine(false);
        }
    }

    public void randomizeStart() {
        Random rand = new Random();
        startPos[0] = 100 + 100*rand.nextInt(5);
        startPos[1] = 200 + 100*rand.nextInt(3);
        startVel[0] = 50*(rand.nextInt(7) - 3);
        startVel[1] = 10*(rand.nextInt(5) - 2);
        startAng = 6.28*rand.nextDouble();
        cos = Math.cos(startAng);
        sin = Math.sin(startAng);
    }

    public void doTick() {
        // update angle
        updateAng();
        // update velocity
        updateAcc();
        vel[0] += acc[0] / tickSpeed;
        vel[1] += acc[1] / tickSpeed;
        // update position
        pos[0] += vel[0] / tickSpeed;
        pos[1] += vel[1] / tickSpeed;
        // update fuel
        if (fuel > 0 && thrusting) {
            fuel -= fuelConsumptionRate / tickSpeed;
        }
        if (fuel <= 0) {
            toggleEngine(false);
            fuel = 0;
        }
        if (drawing) {
            canvas.updateInfo(pos, cos, sin, fuel);
            canvas.repaint();
        }
    }

    public void updateAng() {
        ang += (turning * turnRate) / tickSpeed;
        if (turning != 0) {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
    }

    public void updateAcc() {
        // F = m * a
        double mass = fuel * fuelDensity + shipMass;
        double engineForce = thrusting ? thrust : 0;
        acc[0] = sin*engineForce / mass;
        acc[1] = ((cos*engineForce) - gravity) / mass;
    }

    public void toggleEngine(boolean engaged) {
        if (fuel > 0) {
            thrusting = engaged;
        } else {
            thrusting = false;
        }
        if (drawing) {
            canvas.drawEngine = thrusting;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 38 -> toggleEngine(true);
            case 37 -> turning = (turning == 1) ? 0 : -1;
            case 39 -> turning = (turning == -1) ? 0 : 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 38 -> toggleEngine(false);
            case 37 -> turning = (turning == -1) ? 0 : turning;
            case 39 -> turning = (turning == 1) ? 0 : turning;
        }
    }
}
