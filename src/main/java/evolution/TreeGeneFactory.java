package evolution;

public interface TreeGeneFactory extends GeneFactory {

    TreeGene createNew();
    TreeGene createNew(TreeGene g);
    TreeGene createNew(TreeGene g1, TreeGene g2);
}
