/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.api.Sort;

/**
 * A recursive sort implementation based on an abstract in-place merge.
 * It is one of the best-known examples of the utility of the DIVIDE-AND-CONQUER paradigm
 * for efficient algorithm design.
 * It is a simple recursive sort method to sort an array which consist on dividing it into
 * two halves, sort the two halves (recursively), and then merge the results.
 * This recursive code is the basis for an inductive proof that the algorithm sorts the
 * array: if it sorts the two sub-arrays, it sorts the whole array, by merging together the
 * sub-arrays.
 * It guarantees to sort any array of N items in time proportional to N log N, but uses extra
 * space proportional to N.
 *
 * @param <T> type of elements in the array
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {
	// auxiliary array for merges
	private T[] elements;

	@Override
	public void sort(T[] arr) {
		// allocate space for auxiliary array just once
		elements = Arrays.copyOf(arr, arr.length);

		mergeSort(arr, 0, arr.length - 1);
	}

	/**
	 * To sort a sub-array arr[lo..hi] we divide it into two parts: arr[lo..mid] and arr[mid+1..hi], sort
	 * them independently (via recursive calls), and merge the resulting ordered sub-arrays to produce
	 * the result.
	 * Provides an organized way to sequence the calls to the merge method.
	 * 
	 * @param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param hi highest index in the array
	 */
	private void mergeSort(T[] arr, int lo, int hi) {
		if (hi <= lo) {
			return;
		}

		int mid = lo + (hi - lo) / 2;

		// sort left half
		mergeSort(arr, lo, mid);

		// sort right half
		mergeSort(arr, mid + 1, hi);

		// merge both halves
		merge(arr, lo, mid, hi);
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
		String[] arr = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};

		System.out.println("Unsorted: " + Arrays.toString(arr));

		MergeSort<String> mergeSort = new MergeSort<>();
		
		mergeSort.sort(arr);
		
		System.out.println("Sorted: " + Arrays.toString(arr));
	}
}
