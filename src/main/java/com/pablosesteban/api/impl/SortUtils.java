/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

public class SortUtils {
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
	 * 
	 * @param arr array to be sorted
	 * @return the array sorted
	 */
	public static Comparable[] shellSort(Comparable[] arr) {
		
		
		return arr;
	}
	
	private static void exchange(Comparable[] arr, int i, int j) {
		Comparable v = arr[i];
		
		arr[i] = arr[j];
		
		arr[j] = v;
	}
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	public static void main(String[] args) {
		String[] unsorted = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		
		System.out.println("Unsorted: " + Arrays.toString(unsorted));
		
		Comparable[] sorted = SortUtils.insertionSort(unsorted);
		
		System.out.println("Sorted: " + Arrays.toString(sorted));
	}
}
