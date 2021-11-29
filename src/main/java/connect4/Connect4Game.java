package connect4;

import evolution.Action;
import evolution.Genome;

import java.util.Random;

public class Connect4Game {

    public int play(Genome g) {
        Connect4Situation sit = new Connect4Situation(new int[42]);
        int[] board = sit.getBoard();
        Random rand = new Random();
        int lastMoved = 2*rand.nextInt(2) - 1;
        int totalMoves = 0;
        while (totalMoves < 42) {
            int move;
            if (lastMoved == -1) {
                Connect4Action act = (Connect4Action) g.advise(sit);
                if (act != null && board[35 + act.getMove()] == 0) {
                    move = dropFromAbove(board, act.getMove());
                } else {
                    move = makeRandomMove(board);
                }
                lastMoved = 1;
            } else {
                flipBoard(board);
                move = makeRandomMove(board);
                flipBoard(board);
                lastMoved = -1;
            }
            int w = evaluateBoard(board, move);
            if (w != 0) {
                return w;
            }
            totalMoves++;
        }
        return 0;
    }

    public static int dropFromAbove(int[] board, int drop) {
        // assumes there is space
        int move = drop;
        while(board[move] != 0) {
            move += 7;
        }
        board[move] = 1;
        return move;
    }

    public static int makeRandomMove(int[] board) {
        Random rand = new Random();
        int drop;
        do {
            drop = rand.nextInt(7);
        } while(board[35 + drop] != 0);
        return dropFromAbove(board, drop);
    }

    private static int evaluateBoard(int[] board, int lastMove) {
        // assumes no winner from before lastMove, so can just evaluate from lastMove
        int lookfor = board[lastMove];
        // searches row
        int found = 1;
        int left = lastMove - 1;
        while (left >= 7*(lastMove / 7) && board[left] == lookfor) {
            left--;
            found++;
        }
        int right = lastMove + 1;
        while (right < 7*(1 + (lastMove / 7)) && board[right] == lookfor) {
            right++;
            found++;
        }
        if (found >= 4) {
            return lookfor;
        }
        // searches column
        found = 1;
        int down = lastMove - 7;
        while (down >= 0 && board[down] == lookfor) {
            down -= 7;
            found++;
        }
        int up = lastMove + 7;
        while (up < 42 && board[up] == lookfor) {
            up += 7;
            found++;
        }
        if (found >= 4) {
            return lookfor;
        }
        // searches positive diagonal
        found = 1;
        int upright = lastMove + 8;
        while (upright < 42 && upright % 7 != 0 && board[upright] == lookfor) {
            upright += 8;
            found++;
        }
        int downleft = lastMove - 8;
        while (downleft >= 0 && (downleft + 1) % 7 != 0 && board[downleft] == lookfor) {
            downleft -= 8;
            found++;
        }
        if (found >= 4) {
            return lookfor;
        }
        // searches negative diagonal
        found = 1;
        int downright = lastMove - 6;
        while (downright >= 0 && downright % 7 != 0 && board[downright] == lookfor) {
            downright -= 6;
            found++;
        }
        int upleft = lastMove + 6;
        while (upleft < 42 && (upleft + 1) % 7 != 0 && board[upleft] == lookfor) {
            upleft += 6;
            found++;
        }
        if (found >= 4) {
            return lookfor;
        }
        return 0;
    }

    private static void flipBoard(int[] board) {
        for (int i = 0; i < 42; i++) {
            board[i] = (-1)*board[i];
        }
    }
}
