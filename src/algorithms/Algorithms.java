/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.Sort;
import algorithms.part_I.week_2.SortInsertion;
import algorithms.part_I.week_2.SortSelection;
import algorithms.part_I.week_2.SortShell;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 *
 * @author psantama
 */
public class Algorithms {
    private static void testSortingAlgorithms(int n, Sort sortImpl) {
        Integer[] elements = new Integer[n];
        for (int i = 0; i < elements.length; i++) {
            //random elements
            elements[i] = StdRandom.uniform(n);
            
            //sorted elements
//            elements[i] = i;
            
            //backwards sorted elements
//            elements[n - 1 - i] = i;
        }
        
        Sort<Integer> sort = sortImpl;
        sort.setElements(elements);
        System.out.println(sort);
        
        long start = System.currentTimeMillis();

        sort.sort();

        long end = System.currentTimeMillis();
        
        System.out.println(sort);
        System.out.println("time: " + ((end - start) / 1000.0) + "s");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numOfElements = 50000;
        
        System.out.println("SORT SELECTION");
        testSortingAlgorithms(numOfElements, new SortSelection(false));
        
        System.out.println("SORT INSERTION");
        testSortingAlgorithms(numOfElements, new SortInsertion(false));
        
        System.out.println("SORT SHELL");
        testSortingAlgorithms(numOfElements, new SortShell(false));
    }
}
