/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_2;

import java.util.Arrays;

/**
 *
 * @author psantama
 */
public abstract class Sort<T extends Comparable<T>> {
    protected boolean debug;
    protected T[] elements;
    
    public Sort(boolean debug) {
        this.debug = debug;
    }
    
    public abstract void sort();
    
    //check if the element1 is smaller or equal than element2
    protected boolean less(T element1, T element2) {
        return element1.compareTo(element2) <= 0;
    }
    
    //swap elements at those index
    protected void swap(int i, int j) {
        T swap = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = swap;
    }
    
    public T[] getElements() {
        return elements;
    }

    public void setElements(T[] elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
