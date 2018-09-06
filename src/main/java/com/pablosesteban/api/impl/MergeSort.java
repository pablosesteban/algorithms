/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.api.Sort;

/**
 * An implementation for sorting an array of elements which is one of the best-known examples
 * of the utility of the DIVIDE-AND-CONQUER paradigm for efficient algorithm design: solving
 * a large problem by dividing it into subproblems, solving the subproblems, then using the
 * solutions for the subproblems to solve the whole problem.
 * It is implemented in two different ways:<br>
 * <ul>
 * <li>TOP DOWN: a recursive sort implementation based on an abstract in-place merge.
 * It is a simple recursive method to sort an array which consist on dividing it into
 * two halves, sort the two halves (recursively), and then merge the results.
 * This recursive code is the basis for an inductive proof that the algorithm sorts the
 * array: if it sorts the two sub-arrays, it sorts the whole array, by merging together the
 * sub-arrays. A way to understand it is to examine the tree where each node
 * depicts a subarray for which sort does a merge. The tree has precisely n levels. For k from
 * 0 to n-1, the k-th level from the top depicts 2^k subarrays, each of length 2^n-k, each of
 * which thus requires at most 2^n-k compares for the merge. Thus we have 2^k*2^n-k = 2^n total
 * cost for each of the n levels, for a total of n*2^n = N lgN.</li>
 * <li>BOTTOM UP: organize the merges so that we do all the merges of tiny subarrays on one pass,
 * then do a second pass to merge those subarrays in pairs, and so forth, continuing until we
 * do a merge that encompasses the whole array. Start by doing a pass of 1-by-1 merges (considering
 * individual items as subarrays of size 1), then a pass of 2-by-2 merges (merge subarrays of size 2
 * to make subarrays of size 4), then 4-by-4 merges, and so forth. All merges involve subarrays of
 * equal size, doubling the sorted subarray size for the next pass, except the second subarray in the
 * last merge on each pass which may be smaller than the first (but is no problem for merge).</li>
 * </ul>
 * Both implementations guarantee to sort any array of N items in time proportional to N log N, but
 * uses extra space proportional to N.
 *
 * @param <T> type of elements in the array
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {
	public enum Type {
		TOP_DOWN,
		BOTTOM_UP;
	}
	
	private static int CUTOFF_LENGTH_INSERTION_SORT = 15;
	
	// auxiliary array for merges
	private T[] elements;
	private Type type;

	public MergeSort(Type type) {
		this.type = type;
	}
	
	@Override
	public void sort(T[] arr) {
		// allocate space for auxiliary array just once
		elements = Arrays.copyOf(arr, arr.length);

		switch (type) {
			case BOTTOM_UP:
				mergeSort(arr);
				break;
			case TOP_DOWN:
				mergeSort(arr, 0, arr.length - 1);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Bottom-up mergesort consists on building small solutions into larger ones, i,e. a sequence of passes
	 * over the whole array, doing sz-by-sz merges, starting with sz equal to 1 and doubling sz on each pass.
	 * The final subarray is of size sz only when the array size is an even multiple of sz, otherwise it is
	 * less than sz.
	 * 
	 * @param arr array to be sorted
	 */
	private void mergeSort(T[] arr) {
		for (int subarraysSize = 1; subarraysSize < arr.length; subarraysSize *= 2) {
			for (int lo = 0; lo < arr.length - subarraysSize; lo += subarraysSize * 2 ) {
				merge(arr, lo, lo + subarraysSize - 1, Math.min(lo + subarraysSize * 2 - 1, arr.length - 1));
			}
		}
	}
	
	/**
	 * Top-down mergesort consist on breaking the array up into smaller problems and solving them recursively,
	 * i.e. dividing the sub-array arr[lo..hi] into two parts, arr[lo..mid] and arr[mid+1..hi], sort them
	 * independently (via recursive calls), and merge the resulting ordered sub-arrays to produce the result.
	 * Provides an organized way to sequence the calls to the merge method.
	 * 
	 * @param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param hi highest index in the array
	 */
	private void mergeSort(T[] arr, int lo, int hi) {
		// use insertion sort for small sub-arrays (faster than merge sort)
		if (hi <= lo + CUTOFF_LENGTH_INSERTION_SORT - 1) {
			SortUtils.insertionSort(arr);
			
			return;
		}

		int mid = lo + (hi - lo) / 2;

		// sort left half
		mergeSort(arr, lo, mid);

		// sort right half
		mergeSort(arr, mid + 1, hi);

		// check whether the array is already in order
		if (arr[mid].compareTo(arr[mid+1]) > 0) {
			// merge both halves
			merge(arr, lo, mid, hi);
		}
	}

	/**
	 * An in-place merge which puts the result of merging the sub-arrays arr[lo..mid] with
	 * arr[mid+1..hi] into a single ordered array, leaving the result in arr[lo..hi].
	 * Both sub-arrays are supposed to be sorted.
	 * 
	 * @param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param mid middle index in the array
	 * @param hi highest index in the array
	 */
	private void merge(T[] arr, int lo, int mid, int hi) {
		for (int i = lo; i <= hi; i++) {
			elements[i] = arr[i];
		}

		int leftHalfStartIndex = lo;
		int leftHalfEndIndex = mid;
		int rightHalfStartIndex = mid + 1;
		int rightHalfEndtIndex = hi;

		for (int i = lo; i <= hi; i++) {
			if (leftHalfStartIndex > leftHalfEndIndex) { // left half exhausted (take from the right)
				arr[i] = elements[rightHalfStartIndex++];
			}else if (rightHalfStartIndex > rightHalfEndtIndex) { // right half exhausted (take from the left)
				arr[i] = elements[leftHalfStartIndex++];
			}else if (elements[rightHalfStartIndex].compareTo(elements[leftHalfStartIndex]) < 0) { // current key on right less than current key on left (take from the right)
				arr[i] = elements[rightHalfStartIndex++];
			}else { // current key on right greater than or equal to current key on left (take from the left)
				arr[i] = elements[leftHalfStartIndex++];
			}
		}
	}

	public static void main(String[] args) {
		String[] arr = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};

		System.out.println("Unsorted: " + Arrays.toString(arr));

		MergeSort<String> mergeSort = new MergeSort<>(Type.BOTTOM_UP);
		
		mergeSort.sort(arr);
		
		System.out.println("Sorted:   " + Arrays.toString(arr));
	}
}
