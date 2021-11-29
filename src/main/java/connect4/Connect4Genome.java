package connect4;

import evolution.*;

import java.util.Random;

public class Connect4Genome extends Genome {

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
                    Gene A = genes.get(a);
                    Gene B = genes.get(b);
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
                GeneFactory gf = new DefaultGeneFactory(Connect4Gene.class);
                genes.add(rand.nextInt(genes.size()), gf.createNew());
                break;
            case 3:
                genes.get(rand.nextInt(genes.size())).mutate();
                break;
        }
    }
}
