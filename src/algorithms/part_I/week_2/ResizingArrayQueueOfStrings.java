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
public class ResizingArrayQueueOfStrings implements Queue<String> {
    private String[] queue;
    private int dequeuePointer, enqueuePointer;
    private int size;
    
    public ResizingArrayQueueOfStrings() {
        queue = new String[10];
        
        dequeuePointer = queue.length - 1;
        enqueuePointer = queue.length;
    }
    
    @Override
    public void enqueue(String item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        if (size() == queue.length) {
            resize(2 * queue.length);
        }
        
        queue[--enqueuePointer] = item;
        
        ++size;
    }

    @Override
    public String dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        if (size() <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        String item = queue[dequeuePointer];
        
        queue[dequeuePointer] = null;
        
        --dequeuePointer;
        --size;
        
        return item;
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        String[] tmpArr = new String[size];
        
        System.out.println("START RESIZING...");
        for (int i = 1, j = dequeuePointer; i <= size(); i++, j--) {
            System.out.println("tmpArr[" + (size - i) + "]: " + tmpArr[size - i] + ", queue[" + j + "]: " + queue[j]);
            tmpArr[size - i] = queue[j];
        }
        
        dequeuePointer = size - 1;
        enqueuePointer = size;
        
        System.out.println("enqueuePointer: " + enqueuePointer + ", dequeuePointer: " + dequeuePointer);
        
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
}
