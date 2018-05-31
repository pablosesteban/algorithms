/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_2;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

/**
 *
 * @author psantama
 */
public class Shuffle<T> {
    private T[] elements;
    
    public void shuffleUniform() {
        for (int i = 0; i < elements.length; i++) {
            //UNIFORM: choose a random number between 0 and i
            int rand = StdRandom.uniform(i + 1);
            
            swap(i, rand);
        }
    }
    
    public void shuffleNotUniform() {
        for (int i = 0; i < elements.length; i++) {
            //NOT UNIFORM: choose a random number between 0 and length
            int rand = StdRandom.uniform(elements.length);
            
            swap(i, rand);
        }
    }

    public void setElements(T[] elements) {
        this.elements = elements;
    }

    public T[] getElements() {
        return elements;
    }
    
    //swap elements at those index
    protected void swap(int i, int j) {
        T swap = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = swap;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
    
    public static void main(String[] args) {
        Shuffle<Integer> sort = new Shuffle<>();
        
        sort.setElements(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        
        sort.shuffleUniform();
        
        System.out.println(sort);
        
        sort.setElements(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        
        sort.shuffleNotUniform();
        
        System.out.println(sort);
    }
}
