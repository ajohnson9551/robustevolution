package connect4;

import evolution.Action;

import java.util.Random;

public class Connect4Action implements Action {

    private Integer move;

    public Connect4Action(Integer move) {
        this.move = move;
    }

    public Integer getMove() {
        return move;
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        this.move = rand.nextInt(7);
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        this.move = rand.nextInt(7);
    }
}
