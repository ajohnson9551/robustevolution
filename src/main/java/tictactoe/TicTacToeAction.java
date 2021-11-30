package tictactoe;

import evolution.Action;

import java.util.Random;

public class TicTacToeAction implements Action {

    private Integer move;

    public TicTacToeAction(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        move = rand.nextInt(9);
    }

    @Override
    public void randomize() {
        mutate();
    }
}
