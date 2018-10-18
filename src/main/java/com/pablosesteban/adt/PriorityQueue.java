/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt;

/**
 * An Abstract Data Type (ADT) for a collection of elements which are sorted.
 * The elements can be processed in ascending or descending order, depends on
 * the implementation. The head of this queue is the least element with respect
 * to the specified ordering. If multiple elements are tied for least value,
 * the head is one of those elements.
 * 
 * @param <E> type of elements stored in the priority queue
 */
public interface PriorityQueue<E extends Comparable<E>> {
	/**
	 * Insert an element into the priority queue
	 *
	 * @param element element to be inserted on priority queue
	 */
	void insert(E element);
	
	/**
	 * Returns the head element of the priority queue
	 * 
	 * @return the head element
	 */
	E peek();
	
	/**
	 * Returns and removes the head element of the priority queue
	 * 
	 * @return the head element
	 */
	E poll();
	
	/**
	 * Check if priority queue is empty
	 * 
	 * @return true if the priority queue has no elements, otherwise false
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the priority queue
	 * 
	 * @return the number of elements in the priority queue
	 */
	int size();
}
