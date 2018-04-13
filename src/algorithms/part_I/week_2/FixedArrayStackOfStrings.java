package algorithms.part_I.week_2;

import algorithms.adt.Stack;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 * 
 * An Abstract Data Type for a fixed capacity stack of strings
 * 
 * The primary performance characteristic of this implementation is that the push and pop operations take time independent of the stack size
 */
public class FixedArrayStackOfStrings implements Stack<String> {
    private final String[] elements;
    private int pointer;
    
    public FixedArrayStackOfStrings(int capacity) {
        elements = new String[capacity];
    }
    
    @Override
    public void push(String item) {
        if (size() == elements.length - 1) {
            throw new IllegalStateException("Stack is full of elements");
        }
        
        if (item == null) {
            throw new IllegalArgumentException("null items are not allowed");
        }
        
        elements[pointer++] = item;
    }

    @Override
    public String pop() {
        if (size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        
        String element = elements[--pointer];
        
        elements[pointer] = null;
        
        return element;
    }

    @Override
    public boolean isEmpty() {
        return pointer == 0;
    }

    //return the number of elements in the stack (not capacity)
    @Override
    public int size() {
        return pointer;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        
    }
}
