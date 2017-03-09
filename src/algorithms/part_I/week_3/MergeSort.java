/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_3;

import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 * DIVIDE AND CONQUER: solve a problem by dividing it into two halves, solving the two halves, and then putting the solutions together to get the appropriate answer
 */
public class MergeSort<T extends Comparable<T>> {
    private boolean debug;
    private T[] elements;
    
    public MergeSort(boolean debug) {
        this.debug = debug;
    }
    
//    public void sort() {
//        mergeSort(elements, 0, elements.length);
//    }
    
//    private void mergeSort(T[] elements, int lowIdx, int highIdx) {
//        //an array with only one element is sorted
//        if (highIdx == lowIdx) {
//            return;
//        }
//        
//        int midIdx = (lowIdx + (highIdx - lowIdx) / 2) - 1;
//        
//        if(debug) {
//            System.out.println("\tLEFT SIDE: {lowIdx: " + lowIdx + ", highIdx: " + highIdx + "}");
//        }
//        
//        //left half of the elements
//        mergeSort(elements, lowIdx, midIdx);
//        
//        if(debug) {
//            System.out.println("\tRIGHT SIDE: {lowIdx: " + lowIdx + ", highIdx: " + highIdx + "}");
//        }
//        mergeSort(elements, midIdx, highIdx);
//    }
    
    private void sort1(Comparable[] a, Comparable[] aux, int lo, int hi)
   {
      if (hi <= lo) return;
      int mid = lo + (hi - lo) / 2;
      
      System.out.println("sort left: {lo: " + lo + ", hi: " + hi + "}");
      System.out.println("\telements: " + Arrays.toString(Arrays.copyOfRange(elements, lo, hi + 1)));
      
      MergeSort.this.sort1(a, aux, lo, mid);
      
      System.out.println("sort right: {lo: " + lo + ", hi: " + hi + "}");
      System.out.println("\telements: " + Arrays.toString(Arrays.copyOfRange(elements, lo, hi + 1)));
      
      MergeSort.this.sort1(a, aux, mid+1, hi);
      
      System.out.println("merge: {lo: " + lo + ", mid: " + mid + ", hi: " + hi + "}");
      System.out.println("\telements left: " + Arrays.toString(Arrays.copyOfRange(elements, lo, mid)));
      System.out.println("\telements right: " + Arrays.toString(Arrays.copyOfRange(elements, mid+1, hi + 1)));
      
      merge1(a, aux, lo, mid, hi);
   }
   public void sort1(Comparable[] a)
   {
      T[] aux = (T[]) new Comparable[a.length];
      
      MergeSort.this.sort1(a, aux, 0, a.length - 1);
      
      System.out.println(Arrays.toString(a));
   }
   
   private void merge1(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
   {
       // precondition: a[mid+1..hi] sorted
       for (int k = lo; k <= hi; k++)
           aux[k] = a[k];
       
       int i = lo, j = mid+1;
       
       for (int k = lo; k <= hi; k++)
       {
           if      (i > mid)              a[k] = aux[j++];
       
           else if (j > hi)               a[k] = aux[i++];
           
           else if (aux[j].compareTo(aux[i]) <= 0) a[k] = aux[j++];
           
           else                           a[k] = aux[i++];
       }
   }
    //assuming both halves are sorted each one
    private T[] merge(T[] elements, int midIdx){
        T[] mergedElements = (T[]) new Comparable[elements.length];
        
        int leftPointer = 0, rightPointer = midIdx, mergedPointer = 0;
        
        if(debug)
            System.out.println("merging halves: " + Arrays.toString(elements));
        
        while (leftPointer <= midIdx && rightPointer < elements.length) {
            T leftElem = elements[leftPointer];
            T rightElem = elements[rightPointer];
            
            if (leftElem.compareTo(rightElem) <= 0) {
                mergedElements[mergedPointer] = leftElem;
                
                leftPointer++;
            }else {
                mergedElements[mergedPointer] = rightElem;
                
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
            mergedElements[mergedPointer] = elements[pointer];
            
            pointer++;
            
            mergedPointer++;
        }
        
        return mergedElements;
    }

    public T[] getElements() {
        return elements;
    }

    public void setElements(T[] elements) {
        this.elements = elements;
    }
    
    public static void main(String[] args) {
        Integer[] elements = {5, 2};
        
        MergeSort<Integer> mergeSort = new MergeSort<>(true);
        
        System.out.println(Arrays.toString(mergeSort.merge(elements, 0)));
    }
}
