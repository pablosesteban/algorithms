/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

public interface Sort<T extends Comparable<T>> {
	void sort(T[] arr);
}
