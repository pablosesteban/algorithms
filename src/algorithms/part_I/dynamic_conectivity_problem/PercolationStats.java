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
    Percolation percolation;
    List<Double> percolationThresholds = new ArrayList<>();
    int trials;
    
    //perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        
        monteCarloSimulation(n, trials);
    }
    
    private void monteCarloSimulation(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            
            while (percolation.percolates() == false) {
                percolation.randomOpen();
            }
            
            percolationThresholds.add((double) percolation.getNumberOfOpenSites() / (n * n));
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
        PercolationStats percolationStats = new PercolationStats(10, 30);
        
        System.out.println("Percolation Mean: " + percolationStats.mean());
        System.out.println("Percolation Stdev: " + percolationStats.stddev());
        System.out.println("95% Confidence Interval: [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
