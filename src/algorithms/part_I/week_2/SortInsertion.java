/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

/**
 *
 * @author psantama
 * 
 * INSERTION SORT: we'll move an index i from left to right and in the "ith" iteration, we're going to move elements[i] into position among the elements to its left if it is smaller
 */
public class SortInsertion<T extends Comparable<T>> extends Sort<T> {
    /*
    2 invariants implemented by this algorithm:
        the entries onto the left of the "ith" element are in ascending order
        
        entries of the right of the "ith" element have not yet been seen
    
    That's the way that we set it up and the algorithm maintains those invariants by moving from right to left, exchanging the "ith" element with every larger elements to its left
    
    Uses about N^2 / 4 compares, and about the same number of exchanges, ON THE AVERAGE:
        for a large array that's randomly ordered, the element that we put into place (go backwards) is going to go about halfway back on the average
        
        it's going to be about twice as fast as selection sort, so we can do about twice as many items in the trace in the same amount of time
    
    BEST CASE:
        if the array happens to be already sorted, all insertion sort does is really validate that each element has got smaller elements to its left
        
        it does no exchanges, it gets the sorting job done with just N minus 1 compares, i.e. LINEAR time
        
        much faster than selection sort
    
    WORST CASE:
        if the array is in descending order and has no duplicates, then every element goes all the way back
    
        it takes N^2 / 2 compares and exchanges
    
        slower than selection sort, because it uses about the same number of compares, but it uses many more exchanges
    
    AVERAGE CASE:
        when the array is partially sorted, i.e. if its number of inversions is less than some constant times N
    
        an inversion is just a pair of keys that are out of order in the array
        
        partially sorted arrays appear often in practice and it runs in LINEAR time for them
    */
    @Override
    public void sort() {
        for (int i = 0; i < elements.length; i++) {
            //go backwards swapping the "ith" element with its left neighbor as long as it is smaller
            for (int j = i; j > 0; j--) {
                System.out.println("compare {" + j + ", " + (j - 1) + "}");
                
                //if left neighbor is smaller, there is no need to keep going because of invariants
                if (!less(elements[j], elements[j - 1])) {
                    break;
                }
                
                System.out.println("swap {" + elements[j] + ", " + elements[j - 1] + "}");
                
                swap(j, j - 1);
                
                System.out.println(this);
            }
        }
    }
    
    public static void main(String[] args) {
        Sort<Integer> SortInsertionInt= new SortInsertion<>();
        
        Integer[] arrInt = {3, 1, 3, 2, 4};
        
        SortInsertionInt.setElements(arrInt);
        
        System.out.println(SortInsertionInt);
        
        SortInsertionInt.sort();
        
        System.out.println(SortInsertionInt);
        
        System.out.println("-----------------");
        
        Sort<String> SortInsertionStr = new SortInsertion<>();
        
        String[] arrStr = {"prolo", "funigga", "pablo", "roberT"};
        
        SortInsertionStr.setElements(arrStr);
        
        System.out.println(SortInsertionStr);
        
        SortInsertionStr.sort();
        
        System.out.println(SortInsertionStr);
    }
}
