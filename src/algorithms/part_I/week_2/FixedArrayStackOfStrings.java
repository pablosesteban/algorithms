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
 */
public class FixedArrayStackOfStrings implements Stack<String> {
    private String[] items;
    private int pointer;
    
    //client must provide an initial capacity
    public FixedArrayStackOfStrings(int capacity) {
        items = new String[capacity];
    }
    
    @Override
    public void push(String item) {
        if (size() == items.length - 1) {
            throw new IllegalStateException("Stack is full");
        }
        
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        items[pointer++] = item;
    }

    @Override
    public String pop() {
        if (size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        
        String item = items[--pointer];
        
        items[pointer] = null;
        
        return item;
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
        return Arrays.toString(items);
    }
}
