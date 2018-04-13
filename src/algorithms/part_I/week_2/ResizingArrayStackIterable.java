/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import algorithms.adt.Stack;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author psantama
 */
public class ResizingArrayStackIterable<T> implements Stack<T>, Iterable<T> {
    private T[] stack;
    private int pointer;
    
    //client must provide an initial capacity
    public ResizingArrayStackIterable() {
        /*
        initial capacity of 2
        
        cannot create a generic type array, instead creates an Object array and casting it to generic type
        */
        stack = (T[]) new Object[2];
    }
    
    //in worst cases it takes time proportional to N
    @Override
    public void push(T item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        /*
        only when the stack is full (unusual operation)
        */
        if (size() == stack.length) {
            resize(2 * stack.length);
        }
        
        stack[pointer++] = item;
    }
    
    //in worst cases it takes time proportional to N
    @Override
    public T pop() {
        if (size() == 0) {
            return null;
        }
        
        T item = stack[--pointer];
        
        stack[pointer] = null;
        
        /*
        only when the stack is quarter full the array is halving the size (unsual operation)
        
        if we would halving the array when it gets to be half full, if the client happens to do push, pop, push, pop alternating when the array is full then, it's going to be doubling, halving, doubling, halving and creating new arrays on every operation to take time proportional to N for every operation and therefore, quadratic time
        */
        if (size() == stack.length / 4) {
            resize(stack.length / 2);
        }
        
        return item;
    }
    
    //resizing the array when it is full and quarter full guarantees that the amount of memory that we use is always only a constant multiple of the number of items actually on the stack
    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        T[] tmpArr = (T[]) new Object[size];
        
        System.out.println("START RESIZING...");
        
        for (int i = 0; i < size(); i++) {
            System.out.println("tmpArr[" + i + "]: " + tmpArr[i] + ", stack[" + i + "]: " + stack[i]);
            tmpArr[i] = stack[i];
        }
        
        System.out.println("END RESIZING...");
        
        stack = tmpArr;
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
        return Arrays.toString(stack);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }
    
    private class Itr implements Iterator<T> {
        private int current = 0;
        
        @Override
        public boolean hasNext() {
            return current != stack.length;
        }

        /**
         * Returns the next element in the stack in insertion order
         * 
         * @return the next element in insertion order
         */
        @Override
        public T next() {
            return stack[current++];
        }

        @Override
        public void remove() {
            Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
        }
    }
}