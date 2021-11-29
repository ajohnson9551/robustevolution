package tictactoe;

import evolution.Action;

public class TicTacToeAction implements Action {

    private static TicTacToeAction[] acts;
    private final Integer move;

    private TicTacToeAction(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    public static TicTacToeAction getAction(int move) {
        if (acts == null) {
            acts = new TicTacToeAction[9];
            for (int i = 0; i < 9; i++) {
                acts[i] = new TicTacToeAction(i);
            }
        }
        return acts[move];
    }
}
