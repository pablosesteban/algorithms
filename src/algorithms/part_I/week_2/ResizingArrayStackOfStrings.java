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

/*
have a good amortized time so total average over the whole process is good

have less wasted space and probably faster implementation of each operation
*/
public class ResizingArrayStackOfStrings implements Stack<String> {
    private String[] stack;
    private int pointer;
    
    //client must provide an initial capacity
    public ResizingArrayStackOfStrings() {
        //initial capacity of 10
        stack = new String[10];
    }
    
    //in worst cases it takes time proportional to N
    @Override
    public void push(String item) {
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
    public String pop() {
        if (size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        
        /*
        only when the stack is quarter full the array is halving the size (unsual operation)
        
        if we would halving the array when it gets to be half full, if the client happens to do push, pop, push, pop alternating when the array is full then, it's going to be doubling, halving, doubling, halving and creating new arrays on every operation to take time proportional to N for every operation and therefore, quadratic time
        */
        if (size() == stack.length / 4) {
            resize(stack.length / 2);
        }
        
        String item = stack[--pointer];
        
        stack[pointer] = null;
        
        return item;
    }
    
    //resizing the array when it is full and quarter full guarantees that the amount of memory that we use is always only a constant multiple of the number of items actually on the stack
    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        String[] tmpArr = new String[size];
        
        for (int i = 0; i < stack.length; i++) {
            tmpArr[i] = stack[i];
        }
        
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
}
