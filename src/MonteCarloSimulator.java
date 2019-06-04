
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class MonteCarloSimulator {
    private Random r = new Random();

    public double getRandomWithDevMean(double stdDev, double mean) {
        double nextGauss = r.nextGaussian();
        return nextGauss * stdDev + mean;
    }

    public PortfolioStats runSimulation(int simulationTimes, int yearCount, double mean, double stdDev,
                                        double initInvestment, double inflation) {
        double[] investmentSimulation = new double[simulationTimes];
        for (int i = 0; i < simulationTimes; i++) {
            double investment = initInvestment;
            for (int j = 0; j < yearCount; j++) {
                investment = investment * (1 + getRandomWithDevMean(stdDev, mean)) * (1 - inflation);
            }
            investmentSimulation[i] = investment;
        }

        DescriptiveStatistics ds = new DescriptiveStatistics(investmentSimulation);
        double bestCase = ds.getPercentile(90);
        double worstCase = ds.getPercentile(10);
        double median = ds.getPercentile(50);

        return new PortfolioStats(bestCase, worstCase, median);
    }

    public static void main(String[] args) {
        MonteCarloSimulator mtc = new MonteCarloSimulator();

        PortfolioStats portfolioStatsAggressive = mtc.runSimulation(10000, 20, 0.094324,
                0.15675, 100000, 0.035);

        PortfolioStats portfolioStatsConservative = mtc.runSimulation(10000, 20, 0.06189,
                0.063438, 100000, 0.035);

        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        System.out.println("===============================");
        System.out.println("Aggressive 20th year Median = " + currencyFormatter.format(portfolioStatsAggressive.getMedian()));
        System.out.println("Aggressive 10% Best Case = " + currencyFormatter.format(portfolioStatsAggressive.getBestCase()));
        System.out.println("Aggressive 10% Worst Case = " + currencyFormatter.format(portfolioStatsAggressive.getWorstCase()));

        System.out.println("===============================");
        System.out.println("Conservative 20th year Median = " + currencyFormatter.format(portfolioStatsConservative.getMedian()));
        System.out.println("Conservative 10% Best Case = " + currencyFormatter.format(portfolioStatsConservative.getBestCase()));
        System.out.println("Conservative 10% Worst Case = " + currencyFormatter.format(portfolioStatsConservative.getWorstCase()));

    }
}
