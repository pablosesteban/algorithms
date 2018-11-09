/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.api.Sort;

/**
 * @author psantamartae
 *
 * @param <E>
 */
public class HeapSort<E extends Comparable<E>> implements Sort<E> {
	public HeapSort() {}
	
	@Override
	public void sort(E[] arr) {
		int index = arr.length;
		
		/*
		 * Heap construction: a clever method is to proceed from right to left, using sink() to make subheaps as we go.
		 * Every position in the array is the root of a small subheap; sink() works for such subheaps, as well. If the
		 * two children of a node are heaps, then calling sink() on that node makes the subtree rooted at the parent a
		 * heap. This process establishes the heap order inductively. The scan starts halfway back through the array
		 * because we can skip the subheaps of size 1.
		 */
		for (int i = index/2; i >= 0; i--) {
			sink(i, arr);
		}
		
//		while (index > 1) {
//			exchange(0, --index, arr);
//			
//			sink(0, arr);
//		}
	}
	
	private boolean less(int i, int j, E[] binaryHeap) {
		return binaryHeap[i].compareTo(binaryHeap[j]) < 0;
	}

	private void exchange(int i, int j, E[] binaryHeap) {
		E element = binaryHeap[i];

		binaryHeap[i] = binaryHeap[j];
		binaryHeap[j] = element;
	}
	
	/**
	 * Auxiliary method to restore the heap order.
	 * When the priority of some node is increased because a node’s element becomes larger
	 * than that node’s parent’s key, we have to travel up the heap to restore the heap order.
	 * The new node, having a too large element, has to swim to a higher level in the heap.
	 * 
	 * @param i new node's index
	 */
	private void swim(int i, E[] binaryHeap) {
		while (i > 0 && less(i/2, i, binaryHeap)) {
			exchange(i, i/2, binaryHeap);

			i = i/2;
		}
	}

	/**
	 * Auxiliary method to restore the heap order.
	 * When the priority of some node is decreased because a node’s element becomes smaller
	 * than one or both of that node’s children’s elements, we have to travel down the heap
	 * to restore the heap order.
	 * The new node, having a too small a element, has to sink to a lower level in the heap.
	 * 
	 * @param i new node's index
	 */
	private void sink(int i, E[] binaryHeap) {
		while (i*2 < binaryHeap.length) {
			int j = i*2;

			if (j < binaryHeap.length - 1 && binaryHeap[i*2+1] != null && less(i*2, i*2+1, binaryHeap)) {
				j = i*2+1;
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
		
		HeapSort<String> heapSort = new HeapSort<>();
		
		System.out.println("ARRAY: " + Arrays.toString(arr));
		
		heapSort.sort(arr);
		
		System.out.println("BINARY HEAP: " + Arrays.toString(arr));
	}
}
