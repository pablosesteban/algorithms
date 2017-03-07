/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import java.util.Arrays;

/**
 *
 * @author psantama
 * 
 * SELECTION SORT: we'll move an index i from left to right and in the "ith" iteration, find the smallest remaining entry to the right of i or bigger index than i and then swap that with i
 */
public class SortSelection<T extends Comparable<T>> extends Sort<T> {
    /*
    2 invariants implemented by this algorithm:
        the entries onto the left of the "ith" element are never changed and they're in ascending order
        
        no entry to the right of the "ith" element is smaller than any entry to the left of it
    
    That's the way that we set it up and the algorithm maintains those invariants by finding the smallest entry to the right and exchange it with the next one
    
    Uses about N^2 / 2 compares and exactly N exchanges:
        sort is going to use quadratic time because it always has to go through the whole thing to look for the minimum (it doesn't matter what order the input is)
    
        sort does just a linear number of exchanges (every item is put in to it's final position with just one exchange)
    */
    @Override
    public void sort() {
        for (int i = 0; i < elements.length; i++) {
            int minIdx = i;
            
            //looking for the smallest element index between the remaining elements in the array
            for (int j = i + 1; j < elements.length; j++) {
                System.out.println("compare {" + elements[j] + ", " + elements[minIdx] + "}");
                
                if (less(elements[j], elements[minIdx])) {
                    minIdx = j;
                }
            }
            
            System.out.println("swap {" + i + ", " + minIdx + "}");
            
            //swap the "ith" element with the smallest one
            swap(i, minIdx);
        }
    }

    public static void main(String[] args) {
        Sort<Integer> sortSelectionInt = new SortSelection<>();
        
        Integer[] arrInt = {3, 1, 3, 2, 4};
        
        sortSelectionInt.setElements(arrInt);
        
        System.out.println(sortSelectionInt);
        
        sortSelectionInt.sort();
        
        System.out.println(sortSelectionInt);
        
        System.out.println("-----------------");
        
        Sort<String> sortSelectionStr = new SortSelection<>();
        
        String[] arrStr = {"prolo", "funigga", "pablo", "roberT"};
        
        sortSelectionStr.setElements(arrStr);
        
        System.out.println(sortSelectionStr);
        
        sortSelectionStr.sort();
        
        System.out.println(sortSelectionStr);
    }
}
