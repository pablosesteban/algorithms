/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

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
        queue = (Item[]) new Object[2];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
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
        
        if (isEmpty()) {
            pointer = 0;
        }
        
        if (size() == queue.length) {
            resize(2 * queue.length);
        }
        
        queue[pointer++] = item;
        
        //when there is space in the array (at the beginning) but the pushPointer is at the end (because of pop operations), reseting it to store next element at position 0
        if (pointer == queue.length) {
            pointer = 0;
        }
        
        size++;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        int popPointer = StdRandom.uniform(0, queue.length);
        
        Item item = queue[popPointer];
        
        queue[popPointer] = null;
        
        //check if returned element is null (already random dequeued or not set because of doubling the size) so do not change size
        if (item != null) {
            --size;
        }
        
        if (size() <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        return item;
    }
    
    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void resize(int size) {
        Item[] tmpArr = (Item[]) new Object[size];
        
        System.out.println("START RESIZING...");
        for (int i = 0; i < size();) {
            if (pointer == queue.length) {
                pointer = 0;
            }
            
            System.out.println("tmpArr[" + (i) + "]: " + tmpArr[i] + ", queue[" + (pointer) + "]: " + queue[pointer]);
            //putting not null elements (because of random dequeue operations)
            if (queue[pointer] != null) {
                tmpArr[i] = queue[pointer];
                
                i++;
            }
            
            pointer++;
        }
        
        pointer = size();
        
        System.out.println("pointer: " + pointer);
        
        System.out.println("END RESIZING...");
        queue = tmpArr;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        return queue[StdRandom.uniform(0, queue.length)];
    }
    
    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new Itr<>();
    }
    
    private class Itr<Item> implements Iterator<Item> {
        private int current = StdRandom.uniform(0, queue.length);
        private int count;
        
        @Override
        public boolean hasNext() {
            return current != count;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            
            Item item = (Item) queue[current];
            
            if (item != null) {
                count++;
            }
            
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
        System.out.println("enqueue 1");
        rq.enqueue(1);
        System.out.println("enqueue 2");
        rq.enqueue(2);
        System.out.println("enqueue 3");
        rq.enqueue(3);
        
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
        
        System.out.println("dequeue " + rq.dequeue());
        System.out.println(rq);
        System.out.println("size: " + rq.size());
    }
}
