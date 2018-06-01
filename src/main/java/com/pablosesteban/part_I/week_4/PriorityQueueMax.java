/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_4;

import com.pablosesteban.Algorithms;
import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 */

/**
 * 
 * HEAPS:
 *      are a specialized tree-based data structure that satisfies the heap property:
 *          if A is a parent node of B, then the key (the value) of node A is ordered with respect to the key of node B (the same ordering applying across the heap)
 *      
 *      MAX HEAPS: the keys of parent nodes are always greater than or equal to those of the children and the highest key is in the root node
 *  
 *      MIN HEAPS: the keys of parent nodes are less than or equal to those of the children and the lowest key is in the root node
 * 
 *      are one maximally efficient implementation of an abstract data type called a Priority Queues, often referred to as "heaps", regardless of how they may be implemented
 * 
 *      a common implementation of a heap is the Binary Heap, in which the tree is a complete binary tree
 * 
 */
public class PriorityQueueMax<T extends Comparable<T>> {
    /**
     * array representation of a binary heap:
     *      parent of node at index k is at index k/2
     * 
     *      childrens of node at index k are at indexes k*2 and k*2+1
     * 
     */
    private T[] elements;
    private int nextIdx;
    
    // make it with resizing array!!!
    public PriorityQueueMax(int capacity) {
        elements = (T[]) new Comparable[capacity + 1];
    }
    
    /**
     * add the next element at the end and then promote it if necessary
     * 
     * running time: log N
     * 
     */
    public void insert(T elem) {
        if (nextIdx >= elements.length - 1) {
            throw new IllegalStateException("full");
        }
        
        // starting at index 1 to make easier calculations
        elements[++nextIdx] = elem;
        
        // promoting the each added element makes sure that the first element of the heap is going to be the largest
        promotion(nextIdx);
    }
    
    /**
     * in order to keep the largest element at the beginning:
     *      swap first element with the last one
     *      
     *      demotion the first element
     * 
     * running time: log N
     */
    public T deleteMax() {
        if (nextIdx == 0) {
            throw new IllegalStateException("empty");
        }
        
        T maxElement = elements[1];
        
        Algorithms.swap(elements, 1, nextIdx);
        
        demotion(1);
        
        // avoid loitering
        elements[nextIdx] = null;
        
        --nextIdx;
        
        return maxElement;
    }
    
    public boolean isEmpty() {
        return nextIdx == 0;
    }
    
    public T max() {
        return elements[1];
    }
    
    public int size() {
        return nextIdx;
    }
    
    /**
     * INVARIANT: parent nodes are NO SMALLER than childrens (MAX HEAP)
     * 
     * check if parent is smaller and go up until the invariant is satisfied
     * 
     * used when you insert one element at the end to the binary heap
     * 
     */
    private void promotion(int idx) {
        while(idx > 1 && elements[idx].compareTo(elements[idx / 2]) > 0) {
            Algorithms.swap(elements, idx / 2, idx);
            
            idx = idx / 2;
        }
    }
    
    /**
     * INVARIANT: parent nodes are NO SMALLER than childrens (MAX HEAP)
     * 
     * check if children nodes are larger, exchange with the largest one and go down until the invariant is satisfied
     * 
     * used when you want to delete the largest element of the binary heap
     * 
     */
    private void demotion(int idx) {
        if (idx*2 >= elements.length - 1 || elements[idx*2] == null) {
            return;
        }
        
        T leftChild = elements[idx*2];
        T rightChild = elements[idx*2+1];
        
        T largestChild;
        int largestChildIdx;
        
        if (rightChild == null || leftChild.compareTo(rightChild) > 0) {
            largestChild = leftChild;
            largestChildIdx = idx*2;
        }else{
            largestChild = rightChild;
            largestChildIdx = idx*2+1;
        }
        
        if (largestChild.compareTo(elements[idx]) < 0) {
            return;
        }
        
        Algorithms.swap(elements, idx, largestChildIdx);
        
        demotion(largestChildIdx);
    }
    
    @Override
    public String toString() {
        return "PriorityQueueMax{" + "elements=" + Arrays.toString(elements) + '}';
    }
    
    public static void main(String[] args) {
        PriorityQueueMax<Integer> pqm = new PriorityQueueMax(10);
        
        System.out.println("isEmpty: " + pqm.isEmpty());
        
        pqm.insert(10);
        pqm.insert(20);
        pqm.insert(4);
        pqm.insert(43);
        pqm.insert(66);
        pqm.insert(6);
        pqm.insert(7);
        pqm.insert(0);
        pqm.insert(40);
        pqm.insert(99);
        
        System.out.println(pqm);
        
        System.out.println("size: " + pqm.size());
        
        System.out.println("delete max: " + pqm.deleteMax());
        
        System.out.println("size: " + pqm.size());
        
        System.out.println(pqm);
        
        System.out.println("delete max: " + pqm.deleteMax());
        
        System.out.println("size: " + pqm.size());
        
        System.out.println(pqm);
        
        System.out.println("insert: 99");
        pqm.insert(99);
        
        System.out.println("size: " + pqm.size());
        
        System.out.println("isEmpty: " + pqm.isEmpty());
        
        System.out.println(pqm);
        
        System.out.println("insert: 99");
        pqm.insert(99);
        
        System.out.println("size: " + pqm.size());
        
        System.out.println(pqm);
    }
}
