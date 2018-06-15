/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

/**
 * API to order vertices in a digraph in different ways.
 * Enables clients to iterate through the vertices in this three vertex
 * orderings which are of interest in typical applications.
 */
public interface DigraphOrder {
	/**
	 * Types of orders.
	 */
	enum Order {
		PRE_ORDER("Puts the vertex on a queue before visiting the next vertices"),
		POST_ORDER("Puts the vertex on a queue after visiting the next vertices"),
		REVERSE_POST_ORDER("Puts the vertex on a stack after visiting the next vertices");
		
		private String description;
		
		private Order(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
    
    /**
     * Get the vertices in the specified order
     * 
     * @param order the kind of order
     * @return the vertices in the specified order
     */
    Iterable<Integer> order(Order order);
}
