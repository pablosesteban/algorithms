/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_4;

import main.Algorithms;
import java.util.Arrays;

/**
 *
 * @author psantama
 */

/**
 * HEAP SORT:
 *      guarantees N log N performance and it is IN-PLACE
 * 
 *      it is NOT STABLE
 * 
 *      
 */
public final class HeapSort<T extends Comparable<T>> {
    private final T[] elements;
    
    public HeapSort(T[] elements) {
        this.elements = (T[]) new Comparable[elements.length];
        
        /**
         * HEAP CONSTRUCTION:
         *      create the MAX HEAP by picking up the last node with at least one children and start demotioning if necessary
         * 
         */
        for (int i = (elements.length - 1) / 2; i >= 1; i--) {
            demotion(elements, i);
        }
        
        System.out.println("heap max: " + Arrays.toString(elements));
        
        /**
         * SORTDOWN:
         *      delete the largest item recursively
         * 
         */
        for (int i = elements.length - 1; i > 0; i--) {
            this.elements[i] = deleteMax(elements, i);
            
            System.out.println("heap sorted: " + Arrays.toString(elements));
        }
    }
    
    private T deleteMax(T[] elements, int idx) {
        T maxElement = elements[1];
        
        Algorithms.swap(elements, 1, idx);
        
        // if it is not made null, demotion operation would get the largest item back to top!
        elements[idx] = null;
        
        demotion(elements, 1);
        
        return maxElement;
    }
    
    private void demotion(T[] elements, int idx) {
        while(idx <= (elements.length - 1) / 2 && elements[idx*2] != null) {
            T leftChild = elements[idx*2];
            T rightChild = null;
            
            if (idx*2+1 < elements.length) {
                rightChild = elements[idx*2+1];
            }
            
            T largestChild;
            int largestChildIdx;
            
            if (rightChild == null || leftChild.compareTo(rightChild) >= 0) {
                largestChild = leftChild;
                largestChildIdx = idx*2;
            }else {
                largestChild = rightChild;
                largestChildIdx = idx*2+1;
            }
            
            if (largestChild.compareTo(elements[idx]) <= 0) {
                break;
            }
            
            Algorithms.swap(elements, idx, largestChildIdx);
            
            idx = largestChildIdx;
        }
    }
    
    @Override
    public String toString() {
        return "HeapSort{" + "elements=" + Arrays.toString(elements) + '}';
    }
    
    public static void main(String[] args) {
        Integer[] ints = {null, 4, 1, 3, 2, 5, 6, 8, 0, 9, 7};
        
        System.out.println("original: " + Arrays.toString(ints));
        
        HeapSort<Integer> hs = new HeapSort(ints);
        
        System.out.println("heapsort: " + hs);
    }
}
