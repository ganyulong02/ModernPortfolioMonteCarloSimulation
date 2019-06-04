public class PortfolioStats {
    private double bestCase, worstCase, median;

    PortfolioStats(double bestCase, double worstCase, double median) {
        this.bestCase = bestCase;
        this.worstCase = worstCase;
        this.median = median;
    }

    public double getBestCase() {
        return bestCase;
    }

    public double getWorstCase() {
        return worstCase;
    }

    public double getMedian() {
        return median;
    }
}
