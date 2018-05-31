/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package main.java.com.pablosesteban.adt;

/**
 * An Abstract Data Type (ADT) for a collection of elements that is based on the
 * LIFO, Last-In-First-Out, policy.
 * The elements are processed in the reverse of the order in which they were
 * added.
 * The order of iteration follows the LIFO, Last-In-First-Out, policy.
 * 
 * @param <E> type of elements stored in the stack
 */
public interface Stack<E> extends Iterable<E> {
    /**
     * Adds an element at the top of the stack
     * 
     * @param element element to be stored in the stack
     */
    void push(E element);

    /**
     * Removes the last element added to the stack
     * 
     * @return the last element added to the stack
     */
    E pop();
    
    /**
     * Check if the stack is empty
     * 
     * @return false if the stack is empty, otherwise true
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements inserted in the stack
     * 
     * @return the number of elements inserted in the stack
     */ 
    int size();
}
