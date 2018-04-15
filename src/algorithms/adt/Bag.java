/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt;

/**
 * An Abstract Data Type (ADT) for a collection of elements where removing any
 * of them is not supported.
 * Its purpose is to provide clients with the ability to collect elements and 
 * then to iterate through the collected elements.
 * The order of iteration is unspecified and should be immaterial to the client.
 * 
 * @param <E> type of elements stored in the bag
 */
public interface Bag<E> extends Iterable<E> {
    /**
     * Adds an element to the bag
     * 
     * @param element element to be stored in the bag
     */
    void add(E element);
    
    /**
     * Check if the bag is empty
     * 
     * @return false if the bag is empty, otherwise true
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements inserted in the bag
     * 
     * @return the number of elements in the bag
     */
    int size();
}
