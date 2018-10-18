/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.PriorityQueue;

/**
 * A priority queue implementation based on an array binary heap data structure.
 * A Binary Heap is a data structure that consists on a collection of elements arranged
 * such that each element is guarateed to be larger than or equal to the elements at two
 * other specific positions (children). In turn, each of those elements must be larger
 * than or equal to two additional elements, and so forth (level order).
 * The elements are stored in an array with the root at position 1, its children at
 * positions 2 and 3, their children in positions 4, 5, 6, and 7, and so on (not using the
 * first entry).
 * This ordering is easy to see if you view the elements as being in a binary tree structure
 * with edges from each element to the two elements known to be smaller.
 * Array representation is a rigid structure, but take advantage of the capability to move up
 * and down paths in the tree without pointers as in linked representations.
 * It can efficiently, logarithmic performance, support the basic priority queue operations by
 * means of the auxiliary methods sink and swim, because of a property of complete binary
 * trees: the height of a complete binary tree of size N is lgN, as the height increases by 1
 * when N is a power of 2.
 * 
 * @param <E> type of elements stored in the priority queue
 */
public class MaxPQ<E extends Comparable<E>> implements PriorityQueue<E> {
	private E[] binaryHeap;
	private int index;

	public MaxPQ(int capacity) {
		binaryHeap = (E[]) new Object[capacity+1];
	}

	private boolean less(int i, int j) {
		return binaryHeap[i].compareTo(binaryHeap[j]) < 0;
	}

	private void exchange(int i, int j) {
		E element = binaryHeap[i];

		binaryHeap[i] = binaryHeap[j];
		binaryHeap[j] = element;
	}

	/**
	 * Auxiliary method to restore the heap order.
	 * When the priority of some node is increased because a node�s element becomes larger
	 * than that node�s parent�s key, we have to travel up the heap to restore the heap order.
	 * The new node, having a too large element, has to swim to a higher level in the heap.
	 * 
	 * @param i new node's index
	 */
	private void swim(int i) {
		while (i > 1 && less(i/2, i)) {
			exchange(i, i/2);

			i = i/2;
		}
	}

	/**
	 * Auxiliary method to restore the heap order.
	 * When the priority of some node is decreased because a node�s element becomes smaller
	 * than one or both of that node�s children�s elements, we have to travel down the heap
	 * to restore the heap order.
	 * The new node, having a too small a element, has to sink to a lower level in the heap.
	 * 
	 * @param i new node's index
	 */
	private void sink(int i) {
		while (i*2 < binaryHeap.length) {
			int j = i*2;

			if (j < binaryHeap.length - 1 && less(i*2, i*2+1)) {
				j = i*2+1;
			}

			if (!less(i, j)) {
				break;
			}

			exchange(i, j);

			i = j;
		}
	}

	/* 
	 * Adds the new element at the end of the array, increment the size of the heap,
	 * and then swim up through the heap with that element to restore the heap order.
	 */
	@Override
	public void insert(E element) {
		binaryHeap[++index] = element;

		swim(index);
	}

	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		}

		return binaryHeap[1];
	}

	/* 
	 * Removes the maximum element: take the largest element off the top, put the item from
	 * the end of the heap at the top, decrement the size of the heap, and then sink down
	 * through the heap with that element to restore the heap order.
	 */
	@Override
	public E poll() {
		if (isEmpty()) {
			return null;
		}
		
		E root = binaryHeap[1];
		binaryHeap[1] = null;
		
		exchange(1, index--);
		
		sink(1);
		
		return root;
	}

	@Override
	public boolean isEmpty() {
		return index == 0;
	}

	@Override
	public int size() {
		return index;
	}
	
	public static void main(String[] args) {
		
	}
}
