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
//have a good amortized time so total average over the whole process is good
//have less wasted space and probably faster implementation of each operation
public class ResizingArrayStackOfStrings implements Stack<String> {
    private String[] items;
    private int pointer;
    
    //client must provide an initial capacity
    public ResizingArrayStackOfStrings() {
        items = new String[10];
    }
    
    @Override
    public void push(String item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        //guaranteed that the amount of memory that we use is always only a constant multiple of the number of items actually on the stack
        if (size() == items.length) {
            resize(2 * items.length);
        }
        
        items[pointer++] = item;
    }
    
    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        String[] tmpArr = new String[size];
        
        for (int i = 0; i < items.length; i++) {
            tmpArr[i] = items[i];
        }
        
        items = tmpArr;
    }

    @Override
    public String pop() {
        if (size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        
        //guaranteed that the amount of memory that we use is always only a constant multiple of the number of items actually on the stack
        if (size() == items.length / 4) {
            resize(items.length / 2);
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
