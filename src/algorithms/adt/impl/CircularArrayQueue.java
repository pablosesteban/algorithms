 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.impl;

import algorithms.adt.Queue;
import java.util.Iterator;

/**
 * A Circular Queue is a Queue where the last position is connected back to the
 * first position to make a circle.
 * It is also called "Ring Buffer".
 * In a circular queue, elements are always enqueue at REAR position and are
 * always dequeue from FRONT position.
 * null elements are not allowed.
 *
 * @param <E> type of elements stored in the queue
 */
public class CircularArrayQueue<E> implements Queue<E> {
    private final E[] elements;
    private int front;
    private int rear;
    private int size;
    
    public CircularArrayQueue(int capacity) {
        elements = (E[]) new Object[capacity];
    }
    
    @Override
    public void enqueue(E element) {
        if (element == null) {
            throw new IllegalArgumentException("null elements are not allowed");
        }
        
        // check if queue is full
        if (size() == elements.length) {
            throw new IllegalStateException("queue is full");
        }
        
        if (rear == elements.length && front != 0) {
            rear = front - 1;
        }
        
        elements[rear++] = element;
        
        size++;
    }
    
    @Override
    public E dequeue() {
        // check if queue is empty
        if (size() == 0) {
            throw new IllegalStateException("stack is empty");
        }
        
        if (front == elements.length) {
            front = 0;
        }
        
        E element = elements[front];
        
        elements[front] = null;
        
        front++;
        
        size--;
        
        return element;
    }
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append("{elements: {");
        
        for (E element : elements) {
            sb.append(element);
            sb.append(", ");
        }
        
        int lastComma = sb.lastIndexOf(",");
        sb.deleteCharAt(lastComma);
        
        int lastWitheSpace = sb.lastIndexOf(" ");
        sb.deleteCharAt(lastWitheSpace);
        
        sb.append("}}");
        
        return sb.toString();
    }
    
    private void print() {
        System.out.println(this);
        System.out.println("\trear: " + rear);
        System.out.println("\tfront: " + front);
        System.out.println("\tsize: " + size());
    }
    
    public static void main(String[] args) {
        CircularArrayQueue<String> queue = new CircularArrayQueue<>(3);
        
        queue.print();
        
        System.out.println("----Enqueue Pablo");
        queue.enqueue("Pablo");
        
        queue.print();
        
        System.out.println("----Enqueue Carlos");
        queue.enqueue("Carlos");
        
        System.out.println("----Enqueue Alvaro");
        queue.enqueue("Alvaro");
        
        System.out.println("----Enqueue null");
        try {
            queue.enqueue(null);
        }catch (IllegalArgumentException iae) {
            System.out.println("\t--" + iae);
        }
        
        System.out.println("----Enqueue Maria on full queue");
        try {
            queue.enqueue("Maria");
        }catch (IllegalStateException iee) {
            System.out.println("\t--" + iee);
        }
        
        queue.print();
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Enqueue Dani");
        queue.enqueue("Dani");
        
        queue.print();
        
        System.out.println("----Enqueue Eita on full queue");
        try {
            queue.enqueue("Eita");
        }catch (IllegalStateException iee) {
            System.out.println("\t--" + iee);
        }
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Enqueue Alvaro");
        queue.enqueue("Alvaro");
        
        queue.print();
        
        System.out.println("----Enqueue Carlos");
        queue.enqueue("Carlos");
        
        queue.print();
        
        System.out.println("----Enqueue Pablo");
        queue.enqueue("Pablo");
        
        queue.print();
        
        System.out.println("----Enqueue Eita on full queue");
        try {
            queue.enqueue("Eita");
        }catch (IllegalStateException iee) {
            System.out.println("\t--" + iee);
        }
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
        
        System.out.println("----Enqueue Carlos");
        queue.enqueue("Carlos");
        
        queue.print();
        
        System.out.println("----Dequeue " + queue.dequeue());
        
        queue.print();
    }
}
