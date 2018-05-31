 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package main.java.com.pablosesteban.adt;

/**
 * An Abstract Data Type (ADT) for a collection of elements that is based on a
 * Double-Ended Queue (DEQue).
 * Is like a Queue but supports adding and removing elements at both ends.
 * The order of iteration follows the FIFO, First-In-First-Out, policy.
 *
 * @param <E> type of elements stored in the queue
 */
public interface Deque<E> extends Iterable<E> {
    /**
     * Adds an element to the beginning of the queue
     * 
     * @param element element to be stored to the beginning of the queue
     */
    void addFirst(E element);
    
    /**
     * Adds an element to the end of the queue
     * 
     * @param element element to be stored to the end of the queue
     */
    void addLast(E element);
    
    /**
     * Removes the first element from the beginning of the queue
     * 
     * @return the first element from the beginning of the queue
     */
    E removeFirst();
    
    /**
     * Removes the first element from the end of the queue
     * 
     * @return the first element from the end of the queue
     */
    E removeLast();
    
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
