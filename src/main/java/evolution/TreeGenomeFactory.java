package evolution;

public interface TreeGenomeFactory extends GenomeFactory {

    TreeGenome createNew(TreeGeneFactory gf, int depth);
    TreeGenome createNew(TreeGenome g1, TreeGenome g2);
}
