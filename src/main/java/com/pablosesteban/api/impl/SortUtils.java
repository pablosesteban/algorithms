/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

public final class SortUtils {
	private SortUtils() {}
	
	/**
	 * Finds the smallest item in the array and exchange it with the first entry
	 * (itself if the first entry is already the smallest), then, find the next
	 * smallest item and exchange it with the second entry and so on until the
	 * entire array is sorted.
	 * The items to the left of the current index are in sorted order during the sort
	 * and are into its final position.
	 * The running time NOT depends on the input, it takes about as long to run for
	 * an array that is already in order or for an array with all keys equal as it
	 * does for a randomly-ordered array:
	 * <ul><li>CUADRATIC: uses N^2/2 compares and N exchanges to sort a randomly
	 * ordered array of length N with distinct keys.</li></ul>
	 * For each i from 0 to N-1, there is one exchange, and N-1-i compares, so the
	 * totals are N exchanges and (N-1)+(N-2)+...+ 2+1+0 = N(N-1)/2 ~ N^2/2 compares.
	 * 
	 * @param arr array to be sorted
	 * @return the array sorted
	 */
	public static Comparable[] selectionSort(Comparable[] arr) {
		for (int pos = 0; pos < arr.length; pos++) {
			int min = pos;
			
			for (int pointer = pos+1; pointer < arr.length; pointer++) {
				if (less(arr[pointer], arr[min])) {
					min = pointer;
				}
			}
			
			// each exchange puts an item into its final position
			exchange(arr, pos, min);
		}
		
		return arr;
	}
	
	/**
	 * Builds the final sorted array one item at a time by checking the next item in the array
	 * and finds its current position in the lower part of the array which is maintained
	 * sorted by making space to insert the current item by moving larger items one position
	 * to the right.
	 * The items to the left of the current index are in sorted order during the sort
	 * but are not into its final position, i.e they may have to be moved to make room
	 * for smaller items encountered later.
	 * The running time depends on the initial order of the items in the input:
	 * <ul>
	 * <li>CUADRATIC ON THE AVERAGE: uses ~N^2/4 compares and ~N^2/4 exchanges to sort a randomly
	 * ordered array of length N with distinct keys.</li>
	 * <li>CUADRATIC IN THE WORST CASE (array in reverse order): uses ~N^2/2 compares and ~N^2/2 exchanges.</li>
	 * <li>LINEAR IN THE BEST CASE (array in order): uses N-1 compares and 0 exchanges.</li>
	 * </ul>
	 * Works well for certain types of non-random arrays (partially sorted) that often arise
	 * in practice, even if they are huge.
	 * 
	 * @param arr array to be sorted
	 * @return the array sorted
	 */
	public static Comparable[] insertionSort(Comparable[] arr) {
		for (int pos = 1; pos < arr.length; pos++) {
			for (int pointer = pos; pointer > 0; pointer--) {
				if (less(arr[pointer], arr[pointer - 1])) {
					exchange(arr, pointer, pointer - 1);
				}
			}
		}
		
		return arr;
	}
	
	/**
	 * An extension of insertion sort that gains speed by allowing exchanges of array entries
	 * that are far apart, to produce partially sorted arrays that can be efficiently sorted,
	 * eventually by insertion sort.
	 * The idea is to rearrange the array to give it the property that taking every hth entry
	 * (starting anywhere) yields a sorted subsequence, such an array is said to be h-sorted
	 * (h independent sorted subsequences interleaved together).
	 * h is called the INCREMENT SEQUENCE
	 * By h-sorting for some large values of h, we can move items in the array long distances
	 * and thus make it easier to h-sort for smaller values of h. Using such a procedure for
	 * any sequence of values of h that ends in 1 will produce a sorted array, i.e. h-sort the
	 * array and decrease h through a sequence of increments starting at an increment as large
	 * as a constant fraction of the array length and ending at 1.
	 * Because the subsequences are independent, when h-sorting the array, we insert each item
	 * among the previous items in its h-subsequence by exchanging it with those that have larger
	 * keys (moving them each one position to the right in the subsequence, like insertion sort).
	 * This implementation gains efficiency by making a tradeoff between size and partial order in
	 * the subsequences, i.e. at the beginning, the subsequences are short; later in the sort, the
	 * subsequences are partially sorted and in both cases, insertion sort is the method of choice.
	 * Is useful even for large arrays, particularly by contrast with selection sort and insertion
	 * sort and also performs well on arrays that are in arbitrary order (not necessarily random).
	 * The most important thing is the knowledge that the running time of this algorithm is not
	 * necessarily quadratic, the worst-case number of compares is proportional to N^3/2.
	 * Experienced programmers sometimes choose this implementation because it has acceptable
	 * running time even for moderately large arrays, requires a small amount of code and it uses
	 * no extra space.
	 * 
	 * @param arr array to be sorted
	 * @return the array sorted
	 */
	public static Comparable[] shellSort(Comparable[] arr) {
		int h = 1;
		
		// calculating h as large as a constant fraction of the array length
		while(h < arr.length/3) {
			h = 3 * arr.length + 1;
		}
		
		while (h >= 1) {
			for (int pos = h; pos < arr.length; pos++) {
				for (int pointer = pos; pointer >= h; pointer -= h) {
					if (less(arr[pointer], arr[pointer - h])) {
						exchange(arr, pointer, pointer - h);
					}
				}
			}

			h = h/3;
		}
		
		return arr;
	}
	
	public static void exchange(Comparable[] arr, int i, int j) {
		Comparable v = arr[i];
		
		arr[i] = arr[j];
		
		arr[j] = v;
	}
	
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	public static void main(String[] args) {
		String[] unsorted = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		
		System.out.println("Unsorted: " + Arrays.toString(unsorted));
		
		Comparable[] sorted = SortUtils.shellSort(unsorted);
		
		System.out.println("Sorted: " + Arrays.toString(sorted));
	}
}
