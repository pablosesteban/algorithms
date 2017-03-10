/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_3;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 * DIVIDE AND CONQUER: solve a problem by dividing it into two halves, solving the two halves, and then putting the solutions together to get the appropriate answer
 * @param <T> type of elements for sorting
 */
public class MergeSort<T extends Comparable<T>> {
    private boolean debug;
    private T[] elements;
    private T[] merged;
    
    public MergeSort(boolean debug) {
        this.debug = debug;
    }
    
    public void sort() {
        mergeSort(elements, merged, 0, elements.length - 1);
    }
    
    private void mergeSort(T[] elements, T[] merged, int lowIdx, int highIdx) {
        //an array with only one element is sorted
        if (highIdx == lowIdx) {
            return;
        }
        
        int midIdx = lowIdx + (highIdx - lowIdx) / 2;
        
        //left half of the elements
        mergeSort(elements, merged, lowIdx, midIdx);
        
        if(debug) {
            System.out.println("LEFT SIDE");
            
            System.out.println("\tindexes: {lowIdx: " + lowIdx + ", midIdx: " + midIdx + ", highIdx: " + highIdx + "}");
        }
        
        //right half of the elements
        mergeSort(elements, merged, midIdx + 1, highIdx);
        
        if(debug) {
            System.out.println("RIGHT SIDE");
            
            System.out.println("\tindexes: {lowIdx: " + lowIdx + ", midIdx: " + (midIdx + 1) + ", highIdx: " + highIdx + "}");
        }
        
        //merge halves
        merge(elements, merged, lowIdx, midIdx + 1, highIdx);
    }
    
    private void printArray(T[] arr, int lowIdx, int highIdx) {
        System.out.print("{");
        
        for (int i = lowIdx; i < highIdx - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        
        System.out.print(arr[highIdx - 1] + "}");
    }
    
    //assuming both halves are sorted each one
    private void merge(T[] elements, T[] merged, int lowIdx, int midIdx, int highIdx){
        int leftPointer = lowIdx, rightPointer = midIdx, mergedPointer = 0;
        
        if(debug) {
            System.out.println("MERGING: {lowIdx: " + lowIdx + ", highIdx: " + midIdx + "}, {lowIdx: " + midIdx + ", highIdx: " + highIdx + "}");
        }
        
        while (leftPointer < midIdx && rightPointer <= highIdx) {
            T leftElem = elements[leftPointer];
            T rightElem = elements[rightPointer];
            
            if (leftElem.compareTo(rightElem) <= 0) {
                merged[mergedPointer] = leftElem;
                
                leftPointer++;
            }else {
                merged[mergedPointer] = rightElem;
                
                rightPointer++;
            }
            
            if(debug) {
                System.out.println("\tleftPointer: " + leftPointer);
                System.out.println("\trightPointer: " + rightPointer);
                System.out.println("\tmergedPointer: " + mergedPointer);
                System.out.println("\t----------------");
            }
            
            mergedPointer++;
        }
        
        //copying the remaining array elements
        int pointer = 0, length = 0;
        if (leftPointer < midIdx) {
            pointer = leftPointer;
            
            length = midIdx;
        }else {
            pointer = rightPointer;
            
            length = elements.length;
        }
        
        while (pointer < length) {
            merged[mergedPointer] = elements[pointer];
            
            pointer++;
            
            mergedPointer++;
        }
    }

    public T[] getElements() {
        return elements;
    }

    public void setElements(T[] elements) {
        this.elements = elements;
        
        merged = (T[]) new Comparable[elements.length];
    }

    @Override
    public String toString() {
        return Arrays.toString(merged);
    }
    
    public static void main(String[] args) {
        Integer[] elements = {6,7,8,9,10,1,2,3,4,5};
        
        MergeSort<Integer> mergeSort = new MergeSort<>(true);
        mergeSort.setElements(elements);
        
        System.out.println(Arrays.toString(mergeSort.elements));
        
        mergeSort.sort();
        
        System.out.println(mergeSort);
    }
}
