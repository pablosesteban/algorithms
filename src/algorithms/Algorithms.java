/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.Arrays;

/**
 *
 * @author psantama
 */
public class Algorithms {
    public static void printArray(Comparable[] arr, int lowIdx, int highIdx) {
        System.out.print("{");
        
        for (int i = lowIdx; i < highIdx; i++) {
            System.out.print(arr[i] + ", ");
        }
        
        System.out.println(arr[highIdx] + "}");
    }
    
    //swap elements at those index
    public static void swap(Object[] elements, int i, int j) {
        Object swap = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = swap;
    }
}
