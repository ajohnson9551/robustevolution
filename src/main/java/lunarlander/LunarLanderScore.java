package lunarlander;

public class LunarLanderScore {

    private double remainingFuel;
    private double finalSpeed;

    public LunarLanderScore(double remainingFuel, double finalSpeed) {
        this.remainingFuel = remainingFuel;
        this.finalSpeed = finalSpeed;
    }

    public int computeScore() {
        return (int)((100 - finalSpeed) + 0.05*remainingFuel);
    }
}
