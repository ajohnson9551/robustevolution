package tictactoe;

import evolution.DefaultGeneFactory;
import evolution.Gene;
import evolution.GeneFactory;
import evolution.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeGenome implements Genome<int[], Integer> {

    private List<Gene<int[], Integer>> genes = new ArrayList<>();
    private int score;

    @Override
    public void mutate() {
        Random rand = new Random();
        int r = rand.nextInt(4);
        switch (r) {
            case 0:
                if (genes.size() > 1) {
                    int a = rand.nextInt(genes.size());
                    int b = rand.nextInt(genes.size());
                    while (a == b) {
                        b = rand.nextInt(genes.size());
                    }
                    Gene<int[], Integer> A = genes.get(a);
                    Gene<int[], Integer> B = genes.get(b);
                    genes.set(a, B);
                    genes.set(b, A);
                }
                break;
            case 1:
                if (genes.size() > 1) {
                    genes.remove(rand.nextInt(genes.size()));
                }
                break;
            case 2:
                GeneFactory<int[], Integer> gf = new DefaultGeneFactory<>(TicTacToeGene.class);
                genes.add(rand.nextInt(genes.size()), gf.createNew());
                break;
            case 3:
                genes.get(rand.nextInt(genes.size())).mutate();
                break;
        }
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Integer advise(int[] board) {
        for (Gene<int[], Integer> g : genes) {
            Integer adv = g.act(board);
            if (adv != null) {
                return adv;
            }
        }
        return null;
    }

    @Override
    public void addGene(Gene<int[], Integer> g) {
        if (g != null) {
            genes.add(g);
        }
    }

    @Override
    public Gene<int[], Integer> getGene(int index) {
        if (index >= genes.size()) {
            return null;
        }
        return genes.get(index);
    }

    @Override
    public int getGenomeSize() {
        return genes.size();
    }

    @Override
    public void print() {
        int j = 0;
        for (Gene g : genes) {
            System.out.println("");
            System.out.println("Gene " + j + ":");
            g.print();
            System.out.println("");
            j++;
        }
    }
}
