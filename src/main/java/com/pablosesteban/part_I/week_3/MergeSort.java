/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_3;

import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 * MERGE SORT:
 *  divide an array into two halves recursively, sort each of the halves and then merge the result
 * 
 *  implements DIVIDE AND CONQUER programming paradigm: solve a problem by dividing it into two halves, solving the two halves, and then putting the solutions together to get the appropriate answer
 * 
 *  N log N
 * 
 *  Improvement 1:
 *      Mergesort is too complicated to use for tiny arrays:
 *          so say the subarrays are only of two, or three, or four there's too much overhead with the recursive calls and so forth to get that done efficiently
 * 
 *          what's worse is, the recursive nature of the sort definitely means that there's going to be lots of subarrays to be sorted
 *  
 *      just cut off and use Insertion Sort which is simple and efficient for small subarrays
 *  
 *  Improvement 2:
 *      we can make that'll improve the performance for cases when the array is partly sorted, is to just stop if it's already sorted
 * 
 *      that's going to happen in the case where the biggest element in the first half is less or equal to the smallest item in the second half
 * @param <T> type of elements for sorting
 */

//Merge Sort is used in Java for sorting Objects
public class MergeSort<T extends Comparable<T>> {
    private boolean debug;
    private T[] elements;
    
    public MergeSort(T[] elements, boolean debug) {
        this.debug = debug;
        
        this.elements = elements;
    }
    
    public void sort() {
        mergeSort(0, elements.length - 1);
    }
    
    private void mergeSort(int lowIdx, int highIdx) {
        if (debug) {
            T[] arr = Arrays.copyOfRange(elements, lowIdx, highIdx + 1);
            
            System.out.println("MERGE SORT: " + Arrays.toString(arr));
        }
        
        //an array with only one element is sorted
        if (lowIdx == highIdx) {
            if (debug) {
                System.out.println("\tstop recursion!");
                
                System.out.println("\t----------------");
            }
            
            return;
        }
        
        //computes the midpoint
        int midIdx = lowIdx + (highIdx - lowIdx) / 2;
        
        if(debug) {
            T[] left = Arrays.copyOfRange(elements, lowIdx, midIdx + 1);
            
            System.out.println("\tleft side: " + Arrays.toString(left));
        }
        
        //left half of the elements
        mergeSort(lowIdx, midIdx);
        
        if(debug) {
            T[] right = Arrays.copyOfRange(elements, midIdx + 1, highIdx + 1);
            
            System.out.println("\tright side: " + Arrays.toString(right));
        }
        
        //right half of the elements
        mergeSort(midIdx + 1, highIdx);
        
        //IMPROVEMENT 2
        if (elements[midIdx].compareTo(elements[midIdx + 1]) <= 0) {
            if (debug) {
                System.out.println("\tAlready sorted!");
            }
            
            return;
        }
        
        //merge halves
        merge(lowIdx, midIdx + 1, highIdx);
    }
    
    //assuming both halves of the array are sorted each one
    private void merge(int lowIdx, int midIdx, int highIdx){
        //DO NOT use Arrays class
        T[] left = Arrays.copyOfRange(elements, lowIdx, midIdx);
        T[] right = Arrays.copyOfRange(elements, midIdx, highIdx + 1);
        
        int leftPointer = 0, rightPointer = 0;
        
        if(debug) {
            System.out.println("MERGING: {" + "left: " + Arrays.toString(left) + ", right: " + Arrays.toString(right) + "}");
        }
        
        while (leftPointer < left.length && rightPointer < right.length) {
            T leftElem = left[leftPointer];
            T rightElem = right[rightPointer];
            
            if (leftElem.compareTo(rightElem) <= 0) {
                elements[lowIdx++] = leftElem;
                
                leftPointer++;
            }else {
                elements[lowIdx++] = rightElem;
                
                rightPointer++;
            }
            
            if(debug) {
                System.out.println("\tleftPointer: " + leftPointer);
                System.out.println("\trightPointer: " + rightPointer);
                System.out.println("\t----------------");
            }
        }
        
        //copying the remaining array elements if any
        while (leftPointer < left.length) {
            elements[lowIdx++] = left[leftPointer++];
        }
        
        while (rightPointer < right.length) {
            elements[lowIdx++] = right[rightPointer++];
        }
    }

    public T[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "elements: " + Arrays.toString(elements);
    }
    
    public static void main(String[] args) {
        String[] elements = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
//        String[] elements = {"X", "J", "Z", "A"};
        
        int midIdx = elements.length / 2;
        int highIdx = elements.length - 1;
        
        MergeSort<String> mergeSort = new MergeSort<>(elements, true);
        
        mergeSort.sort();
        
//        mergeSort.merge(0, midIdx, highIdx);

        System.out.println(mergeSort);
    }
}
