package tictactoe;

import evolution.DefaultGeneFactory;
import evolution.Gene;
import evolution.GeneFactory;
import evolution.TreeGenome;

import java.util.Random;

public class TicTacToeTreeGenome extends TreeGenome {

    public TicTacToeTreeGenome(Integer depth) {
        super(depth);
    }

    @Override
    public void print() {
        System.out.println("Root:");
        this.getRoot().print();
    }
}
