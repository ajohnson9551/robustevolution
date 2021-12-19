package lunarlander;

import evolution.*;

import java.util.Random;

public class LunarLanderTreeGenome extends TreeGenome {

    public LunarLanderTreeGenome(Integer depth) {
        super(depth);
    }

    @Override
    public void print() {
        System.out.println("Root:");
        this.getRoot().print();
    }
}
