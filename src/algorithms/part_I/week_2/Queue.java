package algorithms.part_I.week_2;

/**
 * 
 * @author psantama
 * 
 * FIFO: First-In-First-Out
 * 
 * @param <E> type of elements stored in the queue
 */
public interface Queue<E> {
    /**
     * Adds an element at the top of the queue
     * 
     * @param item item to be stored in the queue
     */
    void enqueue(E item);
    
    /**
     * Removes the first element added to the queue
     * 
     * @return the first element added to the queue
     */
    E dequeue();
    
    /**
     * Check if the queue is empty
     * 
     * @return false if the stack is empty, otherwise true
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements inserted in the queue
     * 
     * @return the number of elements in the queue
     */ 
    int size();
}
