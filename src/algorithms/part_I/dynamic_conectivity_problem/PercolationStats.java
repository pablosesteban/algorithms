/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author psantama
 */
public class PercolationStats {
    private Percolation percolation;
    private List<Double> percolationThresholds = new ArrayList<>();
    private int trials;
    
    //perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        
        monteCarloSimulation(n, trials);
    }
    
    /*
    We initialize the whole grid to be blocked all
    
    Then we randomly fill in open sites and every time we add an open site, we check to see if it makes the system percolate
    
    We keep going until we get to a point where the system percolates
    
    We can show that the vacancy percentage at the time that it percolates is an estimate of this threshold value
    */
    private void monteCarloSimulation(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            
            while (percolation.percolates() == false) {
                percolation.randomOpen();
            }
            
            percolationThresholds.add((double) percolation.numberOfOpenSites() / (n * n));
        }
    }
    
    //sample mean of percolation threshold
    public double mean(){
        double sum = 0;
        
        for (Double percolationThreshold : percolationThresholds) {
            sum += percolationThreshold;
        }
        
        return sum / trials;
    }
    
    //sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        
        double percolationMean = mean();
        
        for (Double percolationThreshold : percolationThresholds) {
            sum += Math.pow(percolationThreshold - percolationMean, 2);
        }
        
        return Math.sqrt(sum / (trials - 1));
    }
    
    //low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }
    
    //high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }
    
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(1000, 100);
        
        System.out.println("Percolation Mean: " + percolationStats.mean());
        System.out.println("Percolation Stdev: " + percolationStats.stddev());
        System.out.println("95% Confidence Interval: [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
