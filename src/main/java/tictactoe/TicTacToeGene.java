package tictactoe;

import evolution.Gene;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeGene implements Gene<int[], Integer> {

    int[] lookFor = new int[9];
    int moveHere = 0;

    public TicTacToeGene(TicTacToeGene g) {
        this.moveHere = g.moveHere;
        this.lookFor = Arrays.copyOfRange(g.lookFor, 0, 9);
    }

    public TicTacToeGene() {
    }

    @Override
    public Integer act(int[] sit) {
        for (int i = 0; i < 9; i++) {
            if (lookFor[i] != 2 && lookFor[i] != sit[i]) {
                return null;
            }
        }
        return moveHere;
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            lookFor[i] = rand.nextInt(4) - 1;
        }
        moveHere = rand.nextInt(9);
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int m = rand.nextInt(10);
        if (m < 9) {
            lookFor[m] = rand.nextInt(4) - 1;
        } else {
            moveHere = rand.nextInt(9);
        }
    }

    @Override
    public void print() {
        for (int i = 2; i >= 0; i--) {
            int a = lookFor[3*i];
            int b = lookFor[3*i + 1];
            int c = lookFor[3*i + 2];
            String A = a >= 0 ? " " + a : "" + a;
            String B = b >= 0 ? " " + b : "" + b;
            String C = c >= 0 ? " " + c : "" + c;
            System.out.println("|" + A + " " + B + " " + C + "|");
        }
        System.out.println("Move at position " + moveHere);
    }
}
