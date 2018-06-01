/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 *
 * @author psantama
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int pointer;
    private int size;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[0];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("null items are not allowed");
        }
        
        if (size == queue.length) {
            // check when the array is empty and its length is 0: (2 * 0 = 0) -> resize(0) -> throw an ArrayIndexOutOfBoundsException
            if (size == 0) {
                resize(1);
            }else {
                resize(2 * queue.length);
            }
        }
        
        queue[pointer++] = item;
        
        size++;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        int index = StdRandom.uniform(0, size);
        
        Item item = queue[index];
        
        // put the last element in this random place to avoid null elements scattered
        if (index != size - 1) {
            queue[index] = queue[size - 1];
            
            queue[size - 1] = null;
        }else {
            queue[index] = null;
        }
        
        pointer--;
        
        size--;
        
        if (size <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        return item;
    }
    
    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int newSize) {
        Item[] tmpArr = (Item[]) new Object[newSize];
        
        System.out.println("START RESIZING...");
        for (int i = 0; i < size; i++) {
            System.out.println("tmpArr[" + (i) + "]: " + tmpArr[i] + ", queue[" + (i) + "]: " + queue[i]);
            
            tmpArr[i] = queue[i];
        }
        
        System.out.println("END RESIZING...");
        
        queue = tmpArr;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        return queue[StdRandom.uniform(0, size)];
    }
    
    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new Itr<>();
    }
    
    private class Itr<Item> implements Iterator<Item> {
        // array to keep track if an item has already been picked
        private boolean[] pickedItems;
        private int count;
        
        public Itr() {
            pickedItems = new boolean[size];
        }
        
        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            
            int index = StdRandom.uniform(0, size);
            
            while (pickedItems[index]) {
                index = StdRandom.uniform(0, size);
            }
            
            Item item = (Item) queue[index];
            
            pickedItems[index] = true;
                    
            count++;
            
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        for (Integer item : rq) {
            System.out.println(item);
        }
    }
}
