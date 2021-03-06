/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt;

/**
 * An Abstract Data Type (ADT) for a collection of elements that is based on the
 * FIFO, First-In-First-Out, policy.
 * The elements are processed in the order they were added to the queue, they
 * come out in the same order in which they were put in.
 * The order of iteration follows the FIFO, First-In-First-Out, policy.
 * 
 * @param <E> type of elements stored in the queue
 */
public interface Queue<E> extends Iterable<E> {
    /**
     * Adds an element to the end of the queue
     * 
     * @param element element to be stored at the end of the queue
     */
    void enqueue(E element);
    
    /**
     * Removes the first element added to the queue
     * 
     * @return the first element added to the queue
     */
    E dequeue();
    
    /**
     * Check if the queue is empty
     * 
     * @return false if the queue is empty, otherwise true
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements inserted in the queue
     * 
     * @return the number of elements in the queue
     */ 
    int size();
}
