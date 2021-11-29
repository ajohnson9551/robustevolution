package connect4;

import evolution.Gene;

import java.util.Arrays;
import java.util.Random;

public class Connect4Gene implements Gene<int[], Integer> {

    int[] lookFor = new int[42];
    int moveHere = 0;

    public Connect4Gene(Connect4Gene g) {
        this.moveHere = g.moveHere;
        this.lookFor = Arrays.copyOfRange(g.lookFor, 0, 42);
    }

    public Connect4Gene() {
    }

    @Override
    public Integer act(int[] sit) {
        for (int i = 0; i < 42; i++) {
            if (lookFor[i] != 2 && lookFor[i] != sit[i]) {
                return null;
            }
        }
        return moveHere;
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        lookFor = new int[42];
        int numMoves = rand.nextInt(42);
        for (int i = 0; i < numMoves; i++) {
            dropRandom(lookFor);
        }
        moveHere = rand.nextInt(7);
    }

    public void dropRandom(int[] board) {
        Random rand = new Random();
        int move = rand.nextInt(4) - 1;
        int moveHere = rand.nextInt(7);
        while(moveHere < 42 && board[moveHere] != 0) {
            moveHere += 7;
        }
        if (moveHere < 42) {
            board[moveHere] = move;
        }
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(2);
        if (r == 0) {
            dropRandom(lookFor);
        } else {
            moveHere = rand.nextInt(7);
        }
    }

    @Override
    public void print() {
        for (int i = 5; i >= 0; i--) {
            int a = lookFor[7*i];
            int b = lookFor[7*i + 1];
            int c = lookFor[7*i + 2];
            int d = lookFor[7*i + 3];
            int e = lookFor[7*i + 4];
            int f = lookFor[7*i + 5];
            int g = lookFor[7*i + 6];
            String A = a >= 0 ? " " + a : "" + a;
            String B = b >= 0 ? " " + b : "" + b;
            String C = c >= 0 ? " " + c : "" + c;
            String D = d >= 0 ? " " + d : "" + d;
            String E = e >= 0 ? " " + e : "" + e;
            String F = f >= 0 ? " " + f : "" + f;
            String G = g >= 0 ? " " + g : "" + g;
            System.out.println("|" + A + " " + B + " " + C + " " + D + " " + E + " " + F + " " + G + "|");
        }
        System.out.println("Move at position " + moveHere);
    }
}
