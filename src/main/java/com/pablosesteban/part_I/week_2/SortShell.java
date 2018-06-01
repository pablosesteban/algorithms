/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_2;

/**
 *
 * @author psantama
 * 
 * SHELL SORT: the idea is use Insertion Sort algorithm that we'll move entries several positions (h) at a time and the way we're going to do it, it's called h-sorting the array
 */
public class SortShell<T extends Comparable<T>> extends Sort<T> {
    public SortShell(boolean debug) {
        super(debug);
    }
    
    /*
    Insertion Sort is inefficient because elements really move only one position at the time even when we're kind of know that they have a long way to go
    
    we just use Insertion Sort but instead of going "one" back every time we come with a new item, we go "h" back
    
    two considerations:
        if the increments (h) are big then the size of the sub arrays that we're sorting are pretty small so any sorting method including Insertion Sort is going to work well
        
        if the increments (h) are small because we've done previous h-sorts for bigger values of h, the array is partially sorted and so Insertions Sort is going to be fast
    
    each h-sort pass is going to leave the elements more sorted and the last one (1-sort pass) will terminate the sorting
    
    WORST CASE: the number of comparison is O(N3/2) for the 3x + 1 increments
    
    it's very useful in practice because it's pretty fast EXCEPT for very huge arrays
    */
    @Override
    public void sort() {
        int h = 1;
        
        /*
        what increment sequence should we use for Shellsort?
        
        Knuth in the 60s proposed the increment sequence 3x + 1 and that's good because it's easy to compute
        */
        while (h <= elements.length / 3) {
            h = 3 * h + 1;
        }
        
        //the last h-sort pass is going to be like Insertion Sort (h = 1), but the elements are going to be more or less sorted
        while (h >= 1) {
            if (debug)
                System.out.println(h + "-sort");
            
            //iterate over each element starting at h (is the first element that has a neighbor h positions before) and moving forward visiting each element from here
            for (int i = h; i < elements.length; i++) {
                if (debug)
                    System.out.println("\tmoving forward at index: " + i);
                
                /*
                iterate over each element starting at h and go backwards to swap elements if the current element:
                    has a negighbor h positions before
                    
                    this neighbor is greater than the current element
                
                and moving backwards h positions swapping current element and its neighbor if necessary
                */
                    
                for (int j = i; j >= h && less(elements[j], elements[j - h]); j -= h) {
                    if (debug) {
                        System.out.println("\t\tmoving backwards at index: " + j);
                        
                        System.out.println("\t\tswap: {" + elements[j] + ", " + elements[j - h] + "}");
                    }
                    
                    swap(j, j - h);
                }
                
                if (debug)
                    System.out.println("\t" + this);
            }
            
            //decrement the increment sequence
            h = h / 3;
            
            if (debug) {
                System.out.println(this);
                
                System.out.println("---------------------------------");
            }
        }
    }
    
    public static void main(String[] args) {
        Sort<Integer> sort = new SortShell<>(true);
        
        Integer[] elements = {5, 2, 3, 9, 0, 1, 22, 42, 4, 5, 55, 555};
        
        sort.setElements(elements);
        
        System.out.println(sort);
        
        sort.sort();
    }
}
