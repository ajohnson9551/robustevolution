package connect4;

import evolution.Situation;

public class Connect4Situation implements Situation {

    private int[] board;

    public Connect4Situation(int[] board) {
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
        for (int i = 0; i < 42; i++) {
            int e = ((Connect4Situation) external).getBoardAt(i);
            if (board[i] != 2 && e != 2 && board[i] != e) {
                return false;
            }
        }
        return true;
    }
}
