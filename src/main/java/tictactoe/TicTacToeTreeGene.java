package tictactoe;

import evolution.Action;
import evolution.Situation;
import evolution.TreeGene;
import lunarlander.LunarLanderAction;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeTreeGene extends TreeGene {

    TicTacToeSituation lookFor;
    TicTacToeAction moveHere;

    TicTacToeAction rejectedMoveHere;

    public TicTacToeTreeGene(TicTacToeTreeGene g) {
        this.moveHere = new TicTacToeAction(g.moveHere.getMove());
        this.lookFor = new TicTacToeSituation(Arrays.copyOf(g.lookFor.getBoard(), 9));
        this.rejectedMoveHere = new TicTacToeAction(g.rejectedMoveHere.getMove());
    }

    public TicTacToeTreeGene() {
        this.lookFor = new TicTacToeSituation(new int[9]);
        this.moveHere = new TicTacToeAction(0);
        this.rejectedMoveHere = new TicTacToeAction(0);
    }

    @Override
    public void randomize() {
        lookFor.randomize();
        moveHere.randomize();
        rejectedMoveHere.randomize();
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        if (!isLeaf()) {
            int m = rand.nextInt(2);
            if (m == 0) {
                lookFor.mutate();
            } else {
                TreeGene tempRej = getRejectsGene();
                setRejectsGene(getAppliesGene());
                setAppliesGene(tempRej);
            }
        } else {
            int m = rand.nextInt(4);
            if (m == 0) {
                lookFor.mutate();
            } else if (m == 1) {
                moveHere.mutate();
            } else if (m == 2) {
                rejectedMoveHere.mutate();
            } else {
                TicTacToeAction tempRej = (TicTacToeAction) getRejectsAct();
                rejectedMoveHere = moveHere;
                moveHere = tempRej;
            }
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
        if (this.isLeaf()) {
            System.out.println("Move at position " + moveHere.getMove());
            System.out.println("Or if rejects move at position " + rejectedMoveHere.getMove());
        } else {
            System.out.println("Accept Gene:");
            this.getAppliesGene().print();
            System.out.println("Reject Gene:");
            this.getRejectsGene().print();
        }
    }

    @Override
    public Situation getSit() {
        return lookFor;
    }

    @Override
    public Action getAct() {
        return moveHere;
    }

    @Override
    public Action getRejectsAct() {
        return rejectedMoveHere;
    }

    @Override
    public TreeGene createAverageWith(TreeGene g) {
        // no way to average, so just returns copy of this or copy of g
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            return new TicTacToeTreeGene((TicTacToeTreeGene) g);
        } else {
            return new TicTacToeTreeGene(this);
        }
    }
}
