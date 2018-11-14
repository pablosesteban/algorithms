/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import org.junit.internal.matchers.CombinableMatcher;

import com.pablosesteban.api.Sort;

/**
 * An implementation for sorting an array of elements based on an array binary heap data structure.
 * It is built around the sink operation and make it an in-place sort.
 * It uses fewer than 2NlgN + 2N compares and half that many exchanges to sort N items: the 2N term
 * covers the cost of heap construction and the 2NlgN term follows from bounding the cost of each sink
 * operation during the sortdown by 2lgN.
 * It is rarely used in typical applications on modern systems because it has poor cache performance,
 * i.e. array entries are rarely compared with nearby array entries, so the number of cache misses is
 * far higher than for Quicksort, Mergesort, and even Shellsort, where most compares are with nearby
 * entries.
 */
public final class HeapSort {
	private HeapSort() {}
	
	public static void sort(Comparable[] arr) {
		int index = arr.length;
		
		/*
		 * Heap construction: reorganize the original array into a binary heap. Proceed from right to left, using
		 * sink() to make subheaps as we go. Every position in the array is the root of a small subheap; sink()
		 * works for such subheaps, as well. If the two children of a node are heaps, then calling sink() on that
		 * node makes the subtree rooted at the parent a heap. This process establishes the heap order inductively.
		 * The scan starts halfway back through the array because we can skip the subheaps of size 1.
		 * Large items are moving to the beginning of the array as the heap is being constructed.
		 */
		for (int i = index/2; i > 0; i--) {
			sink(i, index, arr);
		}
		
		/*
		 * Sortdown: pull the items out of the heap in decreasing order to build the sorted result. Removes the
		 * largest remaining item from the heap and put it into the array position vacated as the heap shrinks.
		 */
		while (index > 1) {
			exchange(1, index--, arr);
			
			sink(1, index, arr);
		}
	}
	
	// heap is 0-based, not 1-based as in PriorityQueue implementation
	private static boolean less(int i, int j, Comparable[] binaryHeap) {
		return binaryHeap[i-1].compareTo(binaryHeap[j-1]) < 0;
	}

	// heap is 0-based, not 1-based as in PriorityQueue implementation
	private static void exchange(int i, int j, Comparable[] binaryHeap) {
		Comparable element = binaryHeap[i-1];

		binaryHeap[i-1] = binaryHeap[j-1];
		binaryHeap[j-1] = element;
	}

	/*
	 * Auxiliary method to restore the heap order.
	 * When the priority of some node is decreased because a node’s element becomes smaller
	 * than one or both of that node’s children’s elements, we have to travel down the heap
	 * to restore the heap order.
	 * The new node, having a too small a element, has to sink to a lower level in the heap.
	 * 
	 * @param i new node's index
	 */
	private static void sink(int i, int size, Comparable[] binaryHeap) {
		while (i*2 < size) {
			int j = i*2;
			
			if (j < size - 1 && binaryHeap[j+1] != null && less(j, j+1, binaryHeap)) {
				j++;
			}

			if (binaryHeap[j] == null || !less(i, j, binaryHeap)) {
				break;
			}

			exchange(i, j, binaryHeap);

			i = j;
		}
	}
	
	public static void main(String[] args) {
		String[] arr = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		
		System.out.println("ARRAY: " + Arrays.toString(arr));
		
		HeapSort.sort(arr);
		
		System.out.println("SORTED: " + Arrays.toString(arr));
	}
}
