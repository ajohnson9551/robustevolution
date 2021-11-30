package tictactoe;

import evolution.Action;
import evolution.Gene;
import evolution.Situation;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeGene extends Gene {

    TicTacToeSituation lookFor;
    TicTacToeAction moveHere;

    public TicTacToeGene(TicTacToeGene g) {
        this.moveHere = new TicTacToeAction(g.moveHere.getMove());
        this.lookFor = new TicTacToeSituation(Arrays.copyOf(g.lookFor.getBoard(), 9));
    }

    public TicTacToeGene() {
        this.lookFor = new TicTacToeSituation(new int[9]);
        this.moveHere = new TicTacToeAction(0);
    }

    @Override
    public void randomize() {
        lookFor.randomize();
        moveHere.randomize();
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int m = rand.nextInt(2);
        if (m == 0) {
            lookFor.mutate();
        } else {
            moveHere.mutate();
        }
    }

    @Override
    public void print() {
        for (int i = 2; i >= 0; i--) {
            int a = lookFor.getBoardAt(3*i);
            int b = lookFor.getBoardAt(3*i + 1);
            int c = lookFor.getBoardAt(3*i + 2);
            String A = a >= 0 ? " " + a : "" + a;
            String B = b >= 0 ? " " + b : "" + b;
            String C = c >= 0 ? " " + c : "" + c;
            System.out.println("|" + A + " " + B + " " + C + "|");
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
