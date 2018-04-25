/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Queue;
import java.util.Iterator;

/**
 * A Circular Queue is a Queue of a fixed size where the last position is
 * connected back to the first position to make a circle.
 * It is also called "Ring Buffer".
 * In a circular queue, elements are always enqueue at REAR position and are
 * always dequeue from FRONT position.
 * The implementation is backed by an array and prevent overwriting the data by
 * raising an IllegalStateException if an enqueue operation is called when the
 * buffer is full.
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
        
        if (size() == elements.length) {
            throw new IllegalStateException("queue is full");
        }
        
        if (rear == elements.length && front == elements.length) {
            rear = front = 0;
        }
        
        if (rear == elements.length && front != 0) {
            rear = front-1;
        }
        
        elements[rear++] = element;
        
        size++;
    }
    
    @Override
    public E dequeue() {
        if (size() == 0) {
            throw new IllegalStateException("queue is empty");
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
        return new CircularArrayQueueIterator<>();
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
    
    private class CircularArrayQueueIterator<E> implements Iterator<E> {
        private int itFront;
        private int itRear;
        
        public CircularArrayQueueIterator() {
            itFront = front;
            itRear = rear;
        }
        
        @Override
        public boolean hasNext() {
            return itFront != itRear;
        }

        @Override
        public E next() {
            return (E) elements[itFront++];
        }
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
        
        // enqueue to be full
//        System.out.println("----Enqueue Pablo");
//        queue.enqueue("Pablo");
//        queue.print();
//        System.out.println("----Enqueue Carlos");
//        queue.enqueue("Carlos");
//        queue.print();
//        System.out.println("----Enqueue Alvaro");
//        queue.enqueue("Alvaro");
//        queue.print();
//        
//        System.out.println("----FIFO iteration order");
//        for (String element : queue) {
//            System.out.println("\t--"+ element);
//        }
//        
//        System.out.println("\n--------------------------------\n");
//        
//        // dequeue to be empty
//        System.out.println("----Dequeue " + queue.dequeue());
//        queue.print();
//        System.out.println("----Dequeue " + queue.dequeue());
//        queue.print();
//        System.out.println("----Dequeue " + queue.dequeue());
//        queue.print();
//        
//        System.out.println("----FIFO iteration order");
//        for (String element : queue) {
//            System.out.println("\t--"+ element);
//        }
//        
//        System.out.println("\n--------------------------------\n");
//        
//        // alternate enqueue and dequeue
//        for (int i = 0; i < 12; i++) {
//            System.out.println("----Enqueue Pablo");
//            queue.enqueue("Pablo");
//            queue.print();
//            
//            System.out.println("----Dequeue " + queue.dequeue());
//            queue.print();
//        }
//        
//        System.out.println("----FIFO iteration order");
//        for (String element : queue) {
//            System.out.println("\t--"+ element);
//        }
//        
//        System.out.println("\n--------------------------------\n");
        
        // alternate 2 enqueue and dequeue
        for (int i = 0; i < 12; i++) {
            try {
                System.out.println("----Enqueue Pablo");
                queue.enqueue("Pablo");
                queue.print();
            } catch (IllegalStateException ise) {
                System.out.println("\t--" + ise);
            }
            
            try {
                System.out.println("----Enqueue Carlos");
                queue.enqueue("Carlos");
                queue.print();
            } catch (IllegalStateException ise) {
                System.out.println("\t--" + ise);
            }
            
            try {
                System.out.println("----Dequeue " + queue.dequeue());
                queue.print();
            } catch (IllegalStateException ise) {
                System.out.println("\t--" + ise);
            }
        }
        
        System.out.println("----FIFO iteration order");
        for (String element : queue) {
            System.out.println("\t--"+ element);
        }
        
        System.out.println("\n--------------------------------\n");

//        // alternate enqueue and 2 dequeue
//        for (int i = 0; i < 6; i++) {
//            try {
//                System.out.println("----Enqueue Pablo");
//                queue.enqueue("Pablo");
//                queue.print();
//            } catch (IllegalStateException ise) {
//                System.out.println("\t--" + ise);
//            }
//            
//            try {
//                System.out.println("----Dequeue " + queue.dequeue());
//                queue.print();
//            } catch (IllegalStateException ise) {
//                System.out.println("\t--" + ise);
//            }
//            
//            try {
//                System.out.println("----Dequeue " + queue.dequeue());
//                queue.print();
//            } catch (IllegalStateException ise) {
//                System.out.println("\t--" + ise);
//            }
//        }
//        
//        System.out.println("----FIFO iteration order");
//        for (String element : queue) {
//            System.out.println("\t--"+ element);
//        }
    }
}
