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
        this.moveHere = g.moveHere;
        this.lookFor = new TicTacToeSituation(Arrays.copyOfRange(g.lookFor.getBoard(), 0, 9));
    }

    public TicTacToeGene() {
        this.lookFor = new TicTacToeSituation(new int[9]);
        this.moveHere = TicTacToeAction.getAction(0);
    }

    @Override
    public void randomize() {
        Random rand = new Random();
        lookFor.setBoard(new int[9]);
        for (int i = 0; i < 9; i++) {
            lookFor.setBoardAt(i,rand.nextInt(4) - 1);
        }
        moveHere = TicTacToeAction.getAction(rand.nextInt(9));
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int m = rand.nextInt(10);
        if (m < 9) {
            lookFor.setBoardAt(m,rand.nextInt(4) - 1);
        } else {
            moveHere = TicTacToeAction.getAction(rand.nextInt(9));
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
