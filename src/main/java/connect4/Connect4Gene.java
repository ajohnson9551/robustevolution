package connect4;

import evolution.Action;
import evolution.Gene;
import evolution.Situation;

import java.util.Arrays;
import java.util.Random;

public class Connect4Gene extends Gene {

    private Connect4Situation lookFor;
    private Connect4Action moveHere;

    public Connect4Gene(Connect4Gene g) {
        this.moveHere = g.moveHere;
        this.lookFor = new Connect4Situation(Arrays.copyOfRange(g.lookFor.getBoard(), 0, 42));
    }

    public Connect4Gene() {
        lookFor = new Connect4Situation(new int[42]);
        moveHere = Connect4Action.getAction(0);
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        lookFor.setBoard(new int[42]);
        int numMoves = rand.nextInt(42);
        for (int i = 0; i < numMoves; i++) {
            dropRandom(lookFor);
        }
        moveHere = Connect4Action.getAction(rand.nextInt(7));
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

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(2);
        if (r == 0) {
            dropRandom(lookFor);
        } else {
            moveHere = Connect4Action.getAction(rand.nextInt(7));
        }
    }

    @Override
    public void print() {
        for (int i = 5; i >= 0; i--) {
            int a = lookFor.getBoardAt(7*i);
            int b = lookFor.getBoardAt(7*i + 1);
            int c = lookFor.getBoardAt(7*i + 2);
            int d = lookFor.getBoardAt(7*i + 3);
            int e = lookFor.getBoardAt(7*i + 4);
            int f = lookFor.getBoardAt(7*i + 5);
            int g = lookFor.getBoardAt(7*i + 6);
            String A = a >= 0 ? " " + a : "" + a;
            String B = b >= 0 ? " " + b : "" + b;
            String C = c >= 0 ? " " + c : "" + c;
            String D = d >= 0 ? " " + d : "" + d;
            String E = e >= 0 ? " " + e : "" + e;
            String F = f >= 0 ? " " + f : "" + f;
            String G = g >= 0 ? " " + g : "" + g;
            System.out.println("|" + A + " " + B + " " + C + " " + D + " " + E + " " + F + " " + G + "|");
        }
        System.out.println("Move at position " + moveHere.getMove());
    }

    @Override
    public Situation getSit() {
        return lookFor;
    }

    @Override
    public Action getAct() {
        return moveHere;
    }
}
