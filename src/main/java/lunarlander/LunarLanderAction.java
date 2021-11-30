package lunarlander;

import evolution.Action;

import java.util.Random;

public class LunarLanderAction implements Action {

    private boolean thrust = false;
    private int turn = 0;

    public LunarLanderAction() {

    }

    public LunarLanderAction(boolean thrust, int turn) {
        this.thrust = thrust;
        this.turn = turn;
    }

    @Override
    public LunarLanderAction clone() {
        return new LunarLanderAction(this.thrust, this.turn);
    }

    public boolean getThrust() {
        return thrust;
    }

    public int getTurn() {
        return turn;
    }

    public void setThrust(boolean thrust) {
        this.thrust = thrust;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            thrust = rand.nextInt(2) == 0;
        } else {
            turn = rand.nextInt(3) - 1;
        }
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        thrust = rand.nextInt(2) == 0;
        turn = rand.nextInt(3) - 1;
    }
}
