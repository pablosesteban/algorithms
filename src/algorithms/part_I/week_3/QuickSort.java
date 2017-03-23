/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_3;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 */

/*
QUICK SORT:
    1) Shuffle the array
    
    2) Partition so that, for some j
        entry a[j] is in place
        
        Invariant: no larger entry to the left of j, no smaller entry to the right of j
        
    3) Sort each piece recursively
*/
public class QuickSort<T extends Comparable<T>> {
    private T[] elements;
    private boolean debug;
    
    public QuickSort(T[] elements, boolean debug) {
        this.elements = elements;
        this.debug = debug;
    }
    
    public void sort() {
        // SHUFFLE
        StdRandom.shuffle(elements);
        
        quickSort(0, elements.length - 1);
    }
    
    private void quickSort(int lowIdx, int highIdx) {
        if (highIdx <=  lowIdx) {
            return;
        }
        
        // PARTITION
        int idx = partition(lowIdx, highIdx);
        
        // SORT LEFT
        quickSort(lowIdx, idx - 1);
        
        // SORT RIGHT
        quickSort(idx + 1, highIdx - 1);
    }
    
    // return the index of the element which satisfy the invariant
    private int partition(int lowIdx, int highIdx) {
        int i = lowIdx + 1;
        
        if (debug) {
            System.out.println("");
            
            System.out.println("start partition: {low: " + lowIdx + ", i: " + i + ", j: " + highIdx + "}");
        }
        
        /*
        PARTITION PHASE 1: repeat until i and j pointers cross
            Scan i from left to right so long as (a[i] < a[lo])
            
            Scan j from right to left so long as (a[j] > a[lo])
            
            Exchange a[i] with a[j]
        */
        while (i < highIdx) {
            if (debug) {
                System.out.println("-----------------------");
            }
            
            while (elements[lowIdx].compareTo(elements[i]) > 0) {
                i++;
            }
            
            while (elements[lowIdx].compareTo(elements[highIdx]) < 0) {
                highIdx--;
            }
            
            if (debug) {
                System.out.println("exchange: {low: " + lowIdx + ", i: " + i + ", j: " + highIdx + "}");
            }
            
            if (i < highIdx) {
                exchange(i, highIdx);
            }
            
            if (debug) {
                System.out.println(Arrays.toString(elements));
                
                System.out.println("-----------------------");
            }
        }
        
        /*
        PARTITION PHASE 2: when pointers cross
            Exchange a[lo] with a[j]
        */
        exchange(highIdx, lowIdx);
        
        if (debug) {
            System.out.println(Arrays.toString(elements));
            
            System.out.println("");
        }
        
        return highIdx;
    }
    
    private void exchange(int i, int j) {
        T old = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = old;
    }

    @Override
    public String toString() {
        return "QuickSort{" + "elements=" + Arrays.toString(elements) + '}';
    }
    
    public static void main(String[] args) {
        String[] arr = {"K", "R", "A", "T", "E", "L", "E", "P", "U", "I", "M", "Q", "C", "X", "O", "S"};
        
        System.out.println("original: " + Arrays.toString(arr));
        System.out.print("index:    {");
        int j = 0;
        for (int i = 0; i < arr.length-1; i++) {
            if (j == 10) {
                j = 0;
            }
            
            System.out.print(j + ", ");
            
            j++;
        }
        System.out.println(j + "}");
        
        QuickSort<String> qs = new QuickSort<>(arr, true);
        
//        qs.partition(0, arr.length - 1);

        qs.sort();
        
        System.out.println(qs);
    }
}
