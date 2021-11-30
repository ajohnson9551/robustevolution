package connect4;

import evolution.Situation;

import java.util.Random;

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
    public void mutate() {
        dropRandom(this);
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        this.setBoard(new int[42]);
        int numMoves = rand.nextInt(42);
        for (int i = 0; i < numMoves; i++) {
            this.mutate();
        }
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


    public void dropRandom(Connect4Situation sit) {
        Random rand = new Random();
        int move = rand.nextInt(4) - 1;
        int moveIndex = rand.nextInt(7);
        while(moveIndex < 42 && sit.getBoard()[moveIndex] != 0) {
            moveIndex += 7;
        }
        if (moveIndex < 42) {
            sit.setBoardAt(moveIndex, move);
        }
    }
}
