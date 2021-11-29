package connect4;

import evolution.Action;

public class Connect4Action implements Action {

    private static Connect4Action[] acts;
    private final Integer move;

    private Connect4Action(Integer move) {
        this.move = move;
    }

    public Integer getMove() {
        return move;
    }

    public static Connect4Action getAction(int move) {
        if (acts == null) {
            acts = new Connect4Action[7];
            for (int i = 0; i < 7; i++) {
                acts[i] = new Connect4Action(i);
            }
        }
        return acts[move];
    }
}
