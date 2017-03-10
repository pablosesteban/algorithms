/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.Arrays;

/**
 *
 * @author psantama
 */
public class Algorithms {
    private static void printArray(Comparable[] arr, int lowIdx, int highIdx) {
        System.out.print("{");
        for (int i = lowIdx; i < highIdx; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println(arr[highIdx] + "}");
    }
    
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        
        int mid = lo + (hi - lo) / 2;
        
        sort(a, aux, lo, mid);
        
        System.out.println("LEFT SIDE: {lowIdx: " + lo + ", highIdx: " + hi + "}");
        
        printArray(a, lo, hi);
        
        sort(a, aux, mid+1, hi);
        
        System.out.println("RIGHT SIDE: {lowIdx: " + lo + ", highIdx: " + hi + "}");
        
        printArray(a, lo, hi);
        
//        merge(a, aux, lo, mid, hi);
    }
    
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        
        sort(a, aux, 0, a.length - 1);
        
        System.out.println("a: " + Arrays.toString(a));
        System.out.println("aux: " + Arrays.toString(aux));
    }
    
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        
        int i = lo, j = mid+1;
        
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            
            else if (j > hi)               a[k] = aux[i++];
            
            else if (aux[j].compareTo(aux[i]) <= 0) a[k] = aux[j++];
            
            else                           a[k] = aux[i++];
        }
    }
    
    public static void main(String[] args) {
        Comparable[] elements = {6,7,8,9,10,1,2,3,4,5};
        
        sort(elements);
    }
}
