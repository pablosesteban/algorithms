/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_2;

import com.pablosesteban.adt.Queue;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author psantama
 */
public class ResizingArrayQueueOfStrings implements Queue<String> {
    private String[] queue;
    //two pointers needed
    private int popPointer, pushPointer;
    private int size;
    
    public ResizingArrayQueueOfStrings() {
        //initial capacity of 2
        queue = new String[2];
    }
    
    @Override
    public void enqueue(String item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        if (isEmpty()) {
            popPointer = pushPointer = size();
        }
        
        if (size() == queue.length) {
            resize(2 * queue.length);
        }
        
        queue[pushPointer++] = item;
        
        //when there is space in the array (at the beginning) but the pushPointer is at the end (because of pop operations), reseting it to store next element at position 0
        if (pushPointer == queue.length) {
            pushPointer = 0;
        }
        
        size++;
    }
    
    @Override
    public String dequeue() {
        if (isEmpty()) {
            popPointer = pushPointer = 0;
            
            return "Stack is empty";
        }
        
        String item = queue[popPointer];
        
        queue[popPointer] = null;
        
        popPointer++;
        
        size--;
        
        //when there was space in the array (at the beginning) and new elements were store here (because of pop operations), reseting it to pop next element at position 0
        if (popPointer == queue.length) {
            popPointer = 0;
        }
        
        if (size() <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        return item;
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        String[] tmpArr = new String[size];
        
        System.out.println("START RESIZING...");
        for (int i = 0; i < size(); i++) {
            if (popPointer == queue.length) {
                popPointer = 0;
            }
            
            System.out.println("tmpArr[" + (i) + "]: " + tmpArr[i] + ", queue[" + (popPointer) + "]: " + queue[popPointer]);
            tmpArr[i] = queue[popPointer];
            
            popPointer++;
        }
        
        popPointer = 0;
        pushPointer = size();
        
        System.out.println("enqueuePointer: " + pushPointer + ", dequeuePointer: " + popPointer);
        
        System.out.println("END RESIZING...");
        queue = tmpArr;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
