/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.api.Sort;
import com.pablosesteban.api.impl.MergeSort.Type;

import edu.princeton.cs.algs4.StdRandom;

/**
 * An implementation for sorting an array of elements which is an example of the utility of
 * the DIVIDE-AND-CONQUER paradigm for efficient algorithm design: solving a large problem by
 * dividing it into subproblems, solving the subproblems, then using the solutions for the
 * subproblems to solve the whole problem.
 * It is implemented in two different ways:<br>
 * <ul>
 * <li>STANDARD: the crux of the algorithm is the partitioning process which returns the
 * index of the partitioning item j. It rearranges the array to maintain the following
 * invariant: <b>no entry in arr[lo] through arr[j-1] is greater than arr[j], no entry in
 * arr[j+1] through arr[hi] is less than arr[j] and the entry arr[j] is in its final place
 * in the array.</b> Works by partitioning an array into two sub-arrays, then sorting the
 * sub-arrays independently, so if the left subarray and the right subarray are both properly
 * sorted, then the result array, made up of the left subarray (in order, with no entry
 * larger than the partitioning item), the partitioning item, and the right subarray (in order,
 * with no entry smaller that the partitioning item), is in order.
 * The efficiency of the sort depends on how well the partitioning divides the array, which
 * in turn depends on the value of the partitioning item’s key. The best case is when each
 * partitioning stage divides the array exactly in half, i.e. making the number of compares
 * used satisfy the divide-and-conquer recurrence ~ N lgN. This circumstance is true on the
 * averag as well. In the worst case uses ~ N^2 compares e, but random shuffling protects
 * against this case.</li>
 * <li>THREE WAY: an improved implementation for arrays with large numbers of duplicate keys
 * which arise frequently in applications. Based on partitioning the array into three parts,
 * one each for items with keys smaller than, equal to, and larger than the partitioning
 * item’s key. Puts keys equal to the partitioning element in place and thus does not have
 * to include those keys in the sub-arrays for the recursive calls. It is far more efficient
 * than the standard implementation for arrays with large numbers of duplicate keys.
 * It uses ~ N*H compares to sort N items, where H is the Shannon entropy, defined from the
 * frequencies of key values (counting the frequency of each key value). In the worst case,
 * H = lg N when the keys are all distinct (all the probabilities are 1/N). It reduces the
 * time of the sort from linearithmic to linear for arrays with large numbers of duplicate
 * keys.</li>
 * </ul>
 * Both implementations are typically faster than Mergesort because, even though it does more
 * compares, it does much less data movement and uses only a small auxiliary stack of extra
 * memory because of the recursion.
 * 
 * @param <T> type of elements in the array
 */
public class QuickSort<T extends Comparable<T>> implements Sort<T> {
	public enum Type {
		STANDARD,
		THREE_WAY;
	}
	
	private static int CUTOFF_LENGTH_INSERTION_SORT = 7;
	private Type type;
	
	public QuickSort(Type type) {
		this.type = type;
	}
	
	@Override
	public void sort(T[] arr) {
		// randomized algorithm
		StdRandom.shuffle(arr);
		
		switch(type) {
			case STANDARD:
				quickSort(arr, 0, arr.length - 1);
				
				break;
			case THREE_WAY:
				quickSortThreeWay(arr, 0, arr.length - 1);
				
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * A recursive method that sorts a sub-array arr[lo...hi] by using a partition method that puts
	 * arr[i] into position and arranges the rest of the entries such that the recursive calls finish
	 * the sort.
	 * Recursive calls are done after working on the whole array.
	 *
	 * param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param hi highest index in the array
	 */
	private void quickSort(Comparable<T>[] arr, int lo, int hi) {
		// use insertion sort for small sub-arrays (faster than quicksort)
		if (hi <= lo + CUTOFF_LENGTH_INSERTION_SORT) {
			SortUtils.insertionSort(arr);
			
			return;
		}
		
		int partitioningItemIndex = partition(arr, lo, hi);
		
		quickSort(arr, lo, partitioningItemIndex - 1);
		
		quickSort(arr, partitioningItemIndex + 1, hi);
	}
	
	/**
	 * Dijkstra’s solution for partition the array into three parts.
	 * It is based on a single left-to-right pass through the array that maintains a pointer leftIndex
	 * such that arr[lo..leftIndex-1] is less than partitionItem, a pointer rightIndex such that
	 * arr[rightIndex+1, hi] is greater than partitionItem, and a pointer middleIndex such that
	 * arr[leftIndex..middleIndex-1] are equal to partitionItem and arr[middleIndex..rightIndex] are not
	 * yet examined.
	 * Each of these comparisons maintains the invariant so that the loop terminates.
	 * Every item encountered leads to an exchange except for those items with keys equal to the
	 * partitioning item’s key.
	 * 
	 * param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param hi highest index in the array
	 */
	private void quickSortThreeWay(Comparable<T>[] arr, int lo, int hi) {
		// use insertion sort for small sub-arrays (faster than quicksort)
		if (hi <= lo + CUTOFF_LENGTH_INSERTION_SORT) {
			SortUtils.insertionSort(arr);

			return;
		}
		
		int leftIndex = lo, middleIndex = lo + 1, rightIndex = hi;
		
		Comparable<T> partitionItem = arr[lo];
		
		while (middleIndex <= rightIndex) {
			int compareTo = arr[middleIndex].compareTo((T) partitionItem);
			
			if (compareTo == 0) {
				middleIndex++;
			}else if (compareTo > 0) {
				SortUtils.exchange(arr, middleIndex, rightIndex);
				
				rightIndex--;
			}else {
				SortUtils.exchange(arr, leftIndex, middleIndex);
				
				leftIndex++;
				middleIndex++;
			}
		}
		
		quickSort(arr, lo, leftIndex - 1);
		quickSort(arr, rightIndex + 1, hi);
	}
	
	/**
	 * First, arbitrarily choose arr[lo] to be the partitioning item, i.e. the one that will go into
	 * its final position. Next, scan from the left end of the array (lo+1) until we find an entry greater
	 * than or equal to the partitioning item, and scan from the right end of the array until we find
	 * an entry less than or equal to the partitioning item. The two items that stopped the scans are
	 * out of place in the final partitioned array, so we exchange them. Continuing in this way, ensures
	 * that no array entries to the left of the left index i are greater than the partitioning item, and
	 * no array entries to the right of the right index j are less than the partitioning item. When the
	 * scan indices cross, to complete the partitioning process exchanging the partitioning item arr[lo]
	 * with the rightmost entry of the left subarray arr[j] and return its index j.
	 * 
	 * param arr array to be sorted
	 * @param lo lowest index in the array
	 * @param hi highest index in the array
	 * @return the index of the partitioning item
	 */
	private int partition(Comparable<T>[] arr, int lo, int hi) {
		int leftIndex = lo + 1, rightIndex = hi;
		
		Comparable<T> partitioningItem = arr[lo];
		
		while (true) {
			while (SortUtils.less(arr[leftIndex], partitioningItem)) {
				++leftIndex;
				
				if (leftIndex > hi) {
					break;
				}
			}

			while (SortUtils.less(partitioningItem, arr[rightIndex])) {
				--rightIndex;
				
				if (rightIndex == lo) {
					break;
				}
			}

			if (leftIndex >= rightIndex) {
				break;
			}

			SortUtils.exchange(arr, leftIndex, rightIndex);
		}
		
		SortUtils.exchange(arr, lo, rightIndex);
		
		return rightIndex;
	}
	
	public static void main(String[] args) {
		String[] arr = {"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};

		System.out.println("Unsorted: " + Arrays.toString(arr));

		QuickSort<String> quickSort = new QuickSort<>(Type.THREE_WAY);
		
		quickSort.sort(arr);
		
		System.out.println("Sorted:   " + Arrays.toString(arr));
	}
}
