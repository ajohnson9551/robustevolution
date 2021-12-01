package evolution;

import lunarlander.LunarLanderFitness;

import java.util.*;

public class Population {

    private int POP_SIZE;
    private int NUM_BABIES;
    private int INITIAL_GENOME_LENGTH;
    private int BEST_BREEDER_PAIRS;
    private int RANDOM_BREEDER_PAIRS;
    private int OKAY_BREEDER_PAIRS;
    private double OKAY_THRESHHOLD;
    private double MUTATION_RATE;
    private int MAX_MUTATIONS;
    private int CULL;
    private boolean ELITISM;
    private GeneFactory gf;
    private GenomeFactory gnf;
    private Fitness fit;
    private List<Genome> pop = new ArrayList<>();
    private Genome allTimeChampion;
    private boolean configuredDependencies = false;
    private boolean configuredGenetics = false;
    private boolean configuredPopulation = false;
    private int[][] data;
    private int largestGenome = 0;
    private int maxEverScore = Integer.MIN_VALUE;

    public Population() {

    }

    public void setDependencies(GeneFactory gf, GenomeFactory gnf, Fitness fit) {
        this.gf = gf;
        this.gnf = gnf;
        this.fit = fit;
        configuredDependencies = true;
    }

    public void setPopulationParams(int POP_SIZE, int NUM_BABIES, int BEST_BREEDER_PAIRS, int OKAY_BREEDER_PAIRS,
                                    int CULL, int RANDOM_BREEDER_PAIRS, boolean ELITISM, double OKAY_THRESHHOLD) {
        if (POP_SIZE < (RANDOM_BREEDER_PAIRS + BEST_BREEDER_PAIRS + OKAY_BREEDER_PAIRS)*(2 + NUM_BABIES) + CULL) {
            throw new IllegalArgumentException("Population size too small!");
        }
        this.CULL = CULL;
        this.POP_SIZE = POP_SIZE;
        this.NUM_BABIES = NUM_BABIES;
        this.BEST_BREEDER_PAIRS = BEST_BREEDER_PAIRS;
        this.OKAY_BREEDER_PAIRS = OKAY_BREEDER_PAIRS;
        this.RANDOM_BREEDER_PAIRS = RANDOM_BREEDER_PAIRS;
        this.ELITISM = ELITISM;
        this.OKAY_THRESHHOLD = OKAY_THRESHHOLD;
        configuredPopulation = true;
    }

    public void setGeneticsParams(double MUTATION_RATE, int MAX_MUTATIONS, int INITIAL_GENOME_LENGTH) {
        this.MUTATION_RATE = MUTATION_RATE;
        this.INITIAL_GENOME_LENGTH = INITIAL_GENOME_LENGTH;
        this.MAX_MUTATIONS = MAX_MUTATIONS;
        configuredGenetics = true;
    }

    public void genPop() {
        for (int i = 0; i < POP_SIZE; i++) {
            pop.add(gnf.createNew(gf, INITIAL_GENOME_LENGTH));
        }
    }

    public Genome getAllTimeChampion() {
        fit.score(allTimeChampion);
        return allTimeChampion;
    }

    public int[][] evolve(int cycles, boolean printInfo) {
        data = new int[5][cycles];
        if(configuredGenetics && configuredPopulation && configuredDependencies) {
            for (int i = 0; i < cycles; i++) {
                cycle(i, printInfo);
                if (printInfo) {
                    System.out.println("Completed cycle " + i + "/" + cycles);
                }
            }
        } else {
            throw new IllegalStateException("Population class not fully configured yet!");
        }
        data[4][0] = maxEverScore;
        data[4][1] = largestGenome;
        return data;
    }

    private void cycle(int cycleNo, boolean printInfo) {
        Random rand = new Random();
        int totalScore = 0;
        int totalGenomeSize = 0;
        int maxGenomeSize = 0;
        int maxScore = Integer.MIN_VALUE;
        for (Genome g : pop) {
            fit.score(g);
            int genomeSize = g.getGenomeSize();
            totalGenomeSize += genomeSize;
            if (genomeSize > maxGenomeSize) {
                maxGenomeSize = genomeSize;
            }
            int score = g.getScore();
            if (score > maxScore) {
                maxScore = score;
                if (score > maxEverScore) {
                    maxEverScore = score;
                    allTimeChampion = g;
                }
            }
            totalScore += score;
        }
        if (maxGenomeSize > largestGenome) {
            largestGenome = maxGenomeSize;
        }
        data[0][cycleNo] = maxScore;
        data[1][cycleNo] = totalScore / POP_SIZE;
        data[2][cycleNo] = maxGenomeSize;
        data[3][cycleNo] = totalGenomeSize / POP_SIZE;
        pop.sort(new GenomeComparator());
        if (printInfo) {
            System.out.println("Max score = " + maxScore + "/" + fit.getMaxScore());
        }
        List<Genome> babies = new ArrayList<>();
        for (int i = 0; i < BEST_BREEDER_PAIRS*2; i+=2) {
            for (int j = 0; j < NUM_BABIES; j++) {
                Genome baby = breed(pop.get(POP_SIZE - i - 1), pop.get(POP_SIZE - i - 2));
                babies.add(baby);
            }
        }
        int okayThreshhold = ((int) (((double) POP_SIZE)*OKAY_THRESHHOLD));
        int headRoom = POP_SIZE - okayThreshhold;
        for (int i = 0; i < OKAY_BREEDER_PAIRS; i++) {
            int p1 = rand.nextInt(headRoom);
            int p2;
            do {
                p2 = rand.nextInt(headRoom);
            } while (p1 == p2);
            for (int j = 0; j < NUM_BABIES; j++) {
                Genome baby = breed(pop.get(okayThreshhold + p1), pop.get(okayThreshhold + p2));
                babies.add(baby);
            }
        }
        for (int i = 0; i < RANDOM_BREEDER_PAIRS; i++) {
            int j = rand.nextInt(POP_SIZE);
            int k = rand.nextInt(POP_SIZE);
            while (j == k) {
                k = rand.nextInt(POP_SIZE);
            }
            for (int r = 0; r < NUM_BABIES; r++) {
                Genome baby = breed(pop.get(j), pop.get(k));
                babies.add(baby);
            }
        }
        for (int i = 0; i < (BEST_BREEDER_PAIRS + RANDOM_BREEDER_PAIRS + OKAY_BREEDER_PAIRS)*NUM_BABIES; i++) {
            int j = ELITISM ? 0 : rand.nextInt(pop.size());
            pop.remove(j);
        }
        pop.addAll(babies);
        for (int i = 0; i < CULL; i++) {
            int j = ELITISM ? i : rand.nextInt(pop.size());
            pop.set(j, gnf.createNew(gf, INITIAL_GENOME_LENGTH));
        }
    }

    private Genome breed(Genome g1, Genome g2) {
        Random rand = new Random();
        Genome baby = gnf.createNew(gf, g1, g2);
        if (rand.nextDouble() < MUTATION_RATE) {
            int mutations = rand.nextInt(MAX_MUTATIONS) + 1;
            for (int i = 0; i < mutations; i++) {
                baby.mutate();
            }
        }
        return baby;
    }

    public int getAllTimeMaxScore() {
        return maxEverScore;
    }
}
