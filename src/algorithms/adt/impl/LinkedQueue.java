/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Queue;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A generic iterable implementation of the Queue collection ADT based on a
 * linked list data structure.
 * A linked list is a recursive data structure that is either empty (null) or a
 * reference to a node having a generic type and a reference to a linked list. A
 * linked list represents a sequence of elements where inserting and removing
 * items is somehow easy.
 * The reference to node instance characterize the linked nature of the data
 * structure. It is commonly use the term "link" to refer to node references.
 * The queue is backed by a linked list where each node has only one link to the
 * next element in the list.
 * null elements are allowed.
 * The implementation achieves the two optimum performance goals for collection
 * ADTs: the space used should always be within a constant factor of the
 * collection size and each operation should require time independent of the
 * collection size.
 * 
 * @param <E> the data to structure the linked list
 */
public class LinkedQueue<E> implements Queue<E> {
    // head of the queue, i.e. link to least recently added node
    private LinkedListNode first;
    // end of the queue, i.e. link to most recently added node
    private LinkedListNode last;
    private int size;
    private int numberOfOperations;
    
    @Override
    public void enqueue(E item) {
        LinkedListNode oldLast = last;
        
        last = new LinkedListNode();
        last.value = item;
        
        if (size() == 0) {
            first = last;
        }else {
            oldLast.next = last;
        }
        
        size++;
        
        numberOfOperations++;
    }

    @Override
    public E dequeue() {
        if (size() == 0) {
            throw new IllegalStateException("queue is empty");
        }
        
        E value = (E) first.value;
        
        first = first.next;
        
        size--;
        
        numberOfOperations++;
        
        return value;
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
        return new LinkedQueueIterator<>();
    }
    
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        
        if (size() == 0) {
            return className + "{elements: {}}";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(className);
        sb.append("{elements: {");
        sb.append(first.toString());
        sb.append("}}");
        
        return sb.toString();
    }
    
    /**
     * A fail-fast iterator.
     * Immediately throws a ConcurrentModificationException if the client
     * modifies the collection (via push or pop operations) during iteration.
     * 
     * @param <E> the data stored in the linked list
     */
    private class LinkedQueueIterator<E> implements Iterator<E> {
        private LinkedListNode currentNode;
        private final int currentNumberOfOperations;
        
        LinkedQueueIterator() {
            currentNode = first;
            currentNumberOfOperations = numberOfOperations;
        }
        
        @Override
        public boolean hasNext() {
            if (currentNumberOfOperations != numberOfOperations) {
                throw new ConcurrentModificationException();
            }
            
            return currentNode != null;
        }
        
        @Override
        public E next() {
            if (currentNumberOfOperations != numberOfOperations) {
                throw new ConcurrentModificationException();
            }
            
            E value = (E) currentNode.value;
            
            currentNode = currentNode.next;
            
            return value;
        }
    }
    
    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<>();
        
        System.out.println("----Enqueue Pablo");
        queue.enqueue("Pablo");
        System.out.println("----Enqueue Carlos");
        queue.enqueue("Carlos");
        System.out.println("----Enqueue null");
        queue.enqueue(null);
        System.out.println("----Enqueue Andres");
        queue.enqueue("Andres");
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----Enqueue Alvaro");
        queue.enqueue("Alvaro");
        System.out.println("----Enqueue Alfredo");
        queue.enqueue("Alfredo");
        System.out.println("----Enqueue Maria on full queue");
        queue.enqueue("Maria");
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        System.out.println("----Dequeue " + queue.dequeue());
        
        try {
            System.out.println("----Dequeue on empty queue");
            queue.dequeue();
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----Enqueue Pablo");
        queue.enqueue("Pablo");
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----Enqueue Carlos");
        queue.enqueue("Carlos");
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----Enqueue Andres");
        queue.enqueue("Andres");
        
        System.out.println("----queue: " + queue);
        System.out.println("----size: " + queue.size());
        
        System.out.println("----FIFO iteration order");
        for (String element : queue) {
            System.out.println("\t--"+ element);
        }
        
        System.out.println("----Dequeue operation while FIFO iteration order");
        try {
            for (String element : queue) {
                System.out.println("\t--"+ element);

                queue.dequeue();
            }
        }catch (ConcurrentModificationException cme) {
            System.out.println("\t--"+ cme);
        }
    }
}
