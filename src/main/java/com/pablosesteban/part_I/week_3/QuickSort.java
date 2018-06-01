/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_3;

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

BEST CASE: N logN
    will divide everything exactly in half and that makes it kind of like MergeSort

WORST CASE: N^2
    if the random shuffle winds up putting the items exactly in order, then partitioning doesn't really do anything except find the smallest, peel off the smallest item (discover that everything to the right is greater)
    
    if we shuffled randomly, it's extremely unlikely to happen

AVERAGE CASE: N logN
    faster than MergeSort in practice because of less data movement

NOT STABLE: make it stable using extra space

IMPROVEMENTS:
    Median of Sample:
        try to estimate the partitioning element to be near the middle rather than just arbitrarily using the first element
        
        sample the items, and then take a median of the sample
        
        that's actually not worth the cost for enlarged samples (media of three)
*/
public class QuickSort<T extends Comparable<T>> {
    private T[] elements;
    private boolean debug;
    
    public QuickSort(T[] elements, boolean debug) {
        this.elements = elements;
        this.debug = debug;
    }
    
    public void sort() {
        // SHUFFLE: to choose a random origin point to compare in order to avoid worst case!
        StdRandom.shuffle(elements);
        
        if (debug) {
            System.out.println(this);
        }
        
        quickSort(0, elements.length - 1);
    }
    
    public void sortDijkstra3way() {
        // SHUFFLE: to choose a random origin point to compare in order to avoid worst case!
        StdRandom.shuffle(elements);
        
        sortDijkstra3way(0, elements.length - 1);
    }
    
    /*
    when we have significant numbers of duplicate keys to sort:
        get the array into three parts, put equal elements into the middle and recursively go through both sides
    
    maintain the invariants:
        everything to the left of leftIdx is known to be less than partitioning element
    
        everything between pivotIdx and leftIdx is known to be equal than partitioning element
    
        everything to the right of rightIdx is known to be greater than partitioning element
    
    this improvement is entropy-optimal, i.e. whatever the distribution of equal keys in there, this thing is going to use a number of compares that's proportional to the best that you could possibly do
    
    the bottom line is that if you randomize the order and use three-way partitioning then there's lot of applications where your sort routine is going to be LINEAR not N logN
    */
    private void sortDijkstra3way(int lowIdx, int hiIdx) {
        if (hiIdx <= lowIdx) {
            return;
        }
        
        // split the array in three parts
        int leftIdx = lowIdx;
        int rightIdx = hiIdx;
        
        int i = lowIdx;
        
        if (debug) {
            System.out.println("PARTITION:");
        }
        
        //3-WAY PARTITION: scan i from left to right, i.e. until i and rightIdx are equals
        while(i <= rightIdx) {
            if (debug) {
                System.out.println("\t-----------------------------");
                
                System.out.println("\telements: " + Arrays.toString(elements));
                
                System.out.print("\tindex   : {");
                
                int k = 0;
                for (int j = 0; j < elements.length - 1; j++) {
                    if (k > 9) {
                        k = 0;
                    }
                    
                    System.out.print(k + ", ");
                    
                    k++;
                }
                
                System.out.println(k + "}");
                
                System.out.println("\tleftIdx: " + leftIdx + ", i: " + i + ", rightIdx: " + rightIdx);
            }
            
            if (elements[leftIdx].compareTo(elements[i]) > 0) {
                if (debug) {
                    System.out.println("\telements[" + lowIdx + "]: " + elements[lowIdx] + " > " + "elements[" + i + "]: " + elements[i]);
                    
                    System.out.println("\texchange {elements[" + leftIdx + "]: " + elements[leftIdx] + ", elements[" + i + "]: " + elements[i] + "}");
                }
                
                exchange(leftIdx, i);
                
                leftIdx++;
                i++;
            }else if (elements[leftIdx].compareTo(elements[i]) < 0) {
                if (debug) {
                    System.out.println("\telements[" + leftIdx + "]: " + elements[leftIdx] + " < " + "elements[" + i + "]: " + elements[i]);
                    
                    System.out.println("\texchange {elements[" + i + "]: " + elements[i] + ", elements[" + rightIdx + "]: " + elements[rightIdx] + "}");
                }
                
                exchange(i, rightIdx);
                
                rightIdx--;
            }else {
                if (debug) {
                    System.out.println("\telements[" + leftIdx + "]: " + elements[leftIdx] + " == " + "elements[" + i + "]: " + elements[i]);
                }
                
                i++;
            }
            
            if (debug) {
                System.out.println("\telements: " + Arrays.toString(elements));
                
                System.out.print("\tindex   : {");
                
                int k = 0;
                for (int j = 0; j < elements.length - 1; j++) {
                    if (k > 9) {
                        k = 0;
                    }
                    
                    System.out.print(k + ", ");
                    
                    k++;
                }
                
                System.out.println(k + "}");
                
                System.out.println("\t-----------------------------");
            }
        }
        
        // SORT LEFT
        sortDijkstra3way(lowIdx, leftIdx - 1);
        
        // SORT RIGHT
        sortDijkstra3way(rightIdx+1, hiIdx);
    }
    
    private void quickSort(int pivotIdx, int rightIdx) {
        if (rightIdx <= pivotIdx) {
            return;
        }
        
        // PARTITION
        int idx = partition(pivotIdx, rightIdx);
        
        if (debug) {
            System.out.println("sort left: {pivotIdx: " + pivotIdx + ", rightIdx: " + (idx - 1) + "}");
        }
        
        // SORT LEFT
        quickSort(pivotIdx, idx - 1);
        
        if (debug) {
            System.out.println("sort right: {pivotIdx: " + (idx + 1) + ", rightIdx: " + rightIdx + "}");
        }
        
        // SORT RIGHT
        quickSort(idx + 1, rightIdx);
    }
    
    // return the index of the element which satisfy the invariant
    private int partition(int pivotIdx, int rightIdx) {
        int leftIdx = pivotIdx + 1;
        
        if (debug) {
            System.out.println();
            
            System.out.println("start partition: {pivotIdx: " + pivotIdx + ", leftIdx: " + leftIdx + ", rightIdx: " + rightIdx + "}");
        }
        
        if (debug) {
            System.out.println("finding partition point...");
        }
        
        /*
        PARTITION PHASE 1: repeat until i and j pointers cross
            Scan i from left to right so long as (a[i] < a[lo])
            
            Scan j from right to left so long as (a[j] > a[lo])
            
            Exchange a[i] with a[j]
        */
        while (true) {
            if (debug) {
                System.out.println("\t-----------------------");
            }
            
            T originElement = elements[pivotIdx];
            
            // if you did not stop the partitioning on equal keys it would run in quadratic time
            // increment left pointer
            while (originElement.compareTo(elements[leftIdx]) > 0) {
                // check if leftIdx reach the last index
                if (leftIdx == rightIdx) {
                    break;
                }
                
                leftIdx++;
                
                if (debug) {
                    System.out.println("\tincrement leftIdx: " + leftIdx);
                }
            }
            
            // if you did not stop the partitioning on equal keys it would run in quadratic time
            // decrement right pointer
            while (originElement.compareTo(elements[rightIdx]) < 0) {
                // check if rightIdx reach the beginning index
                if (rightIdx == pivotIdx) {
                    break;
                }
                
                rightIdx--;
                
                if (debug) {
                    System.out.println("\tdecrement rightIdx: " + rightIdx);
                }
            }
            
            if (leftIdx >= rightIdx) {
                break;
            }
            
            if (debug) {
                System.out.println("\texchange: {pivotIdx: " + pivotIdx + ", leftIdx: " + leftIdx + ", rightIdx: " + rightIdx + "}");
            }
            
            exchange(leftIdx, rightIdx);
            
            if (debug) {
                System.out.println("\t" + Arrays.toString(elements));
                
                System.out.println("\t-----------------------");
            }
        }
        
        if (debug) {
            System.out.println("exchange: {pivotIdx: " + pivotIdx + ", rightIdx: " + rightIdx + "}");
        }
        
        /*
        PARTITION PHASE 2: when pointers cross
            Exchange a[lo] with a[j]
        */
        exchange(rightIdx, pivotIdx);
        
        
        if (debug) {
            System.out.println(Arrays.toString(elements));
            
            System.out.println("partition point: " + rightIdx);
            
            System.out.println("");
        }
        
        return rightIdx;
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
        Integer[] integerArr = {0, 1, 4, 3, 4, 5, 6, 4, 3, 3};
        String[] stringArr = {"P", "A", "B", "X", "W", "P", "P", "V", "P", "D", "P", "C", "Y", "Z"};
        
        QuickSort<String> qs = new QuickSort<>(stringArr, true);
        
        qs.sortDijkstra3way();
        
        System.out.println(qs);
    }
}
