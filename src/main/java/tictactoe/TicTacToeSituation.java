package tictactoe;

import evolution.Situation;

public class TicTacToeSituation implements Situation {

    private int[] board;

    public TicTacToeSituation(int[] board) {
        this.board = board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoardAt(int i, int a) {
        board[i] = a;
    }

    public int getBoardAt(int i) {
        return board[i];
    }

    @Override
    public boolean applies(Situation external) {
        for (int i = 0; i < 9; i++) {
            int e = ((TicTacToeSituation) external).getBoardAt(i);
            if (board[i] != 2 && e != 2 && board[i] != e) {
                return false;
            }
        }
        return true;
    }
}
