/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Algorithms {
    public static void printArray(Comparable[] arr, int lowIdx, int highIdx) {
        System.out.print("{");
        
        for (int i = lowIdx; i < highIdx; i++) {
            System.out.print(arr[i] + ", ");
        }
        
        System.out.println(arr[highIdx] + "}");
    }
    
    // swap elements at those index
    public static void swap(Object[] elements, int i, int j) {
        Object swap = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = swap;
    }
    
    // count triples that sum to 0.
    public static int count(int[] a) {
        int N = a.length;
        
        int cnt = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                    }
                }
            }
        }
        
        return cnt;
    }
    
    
    // Time ThreeSum.count() for N random 6-digit ints.
    public static double timeTrial(int N) {
        int MAX = 1000000;
        
        int[] a = new int[N];
        
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform(-MAX, MAX);
        }
        
        long start = System.currentTimeMillis();
        
        int cnt = count(a);
        
        return (System.currentTimeMillis() - start) / 1000.0;
    }
    
    public static void main(String[] args) {
        // Print time for problem size N.
        for (int N = 250; true; N += N) {
            double time = timeTrial(N);
            
            StdOut.printf("%7d %5.1f\n", N, time);
        }
    }
}
