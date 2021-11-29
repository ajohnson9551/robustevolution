package tictactoe;

import evolution.Genome;

import java.util.Random;

public class TicTacToeGame {


    public int play(Genome<int[], Integer> g) {
        Random rand = new Random();
        int[] board = new int[9];
        int lastMoved = 2*rand.nextInt(2) - 1;
        int totalMoves = 0;
        while (totalMoves < 9) {
            if (lastMoved == -1) {
                Integer a = g.advise(board);
                if (a != null && board[a] == 0) {
                    board[a] = 1;
                } else {
                    makeRandomMove(board);
                }
                lastMoved = 1;
            } else {
                flipBoard(board);
                makeRandomMove(board);
                flipBoard(board);
                lastMoved = -1;
            }
            int w = evaluateBoard(board);
            if (w != 0) {
                return w;
            }
            totalMoves++;
        }
        return 0;
    }

    public static void makeRandomMove(int[] board) {
        // makes a random legal move
        int numEmpty = 0;
        for (int a : board) {
            if (a == 0) {
                numEmpty++;
            }
        }
        Random rand = new Random();
        int move = rand.nextInt(numEmpty);
        int k = 0;
        while (move > 0 || board[k] != 0) {
            if (board[k] == 0) {
                move--;
            }
            k++;
        }
        board[k] = 1;
    }

    private static int evaluateBoard(int[] board) {
        // returns 0 for no winner, 1 for X's, -1 for O's
        // first check rows and columns
        for (int i = 0; i < 3; i++) {
            int row = board[3*i] + board[3*i + 1] + board[3*i + 2];
            int col = board[i] + board[i + 3] + board[i + 6];
            if (row == 3 || col == 3) {
                return 1;
            }
            if (row == -3 || col == -3) {
                return -1;
            }
        }
        // check diags
        int d1 = board[0] + board[4] + board[8];
        int d2 = board[2] + board[4] + board[6];
        if (d1 == 3 || d2 == 3) {
            return 1;
        }
        if (d1 == -3 || d2 == -3) {
            return -1;
        }
        return 0;
    }

    private static void flipBoard(int[] board) {
        for (int i = 0; i < 9; i++) {
            board[i] = (-1)*board[i];
        }
    }
}
