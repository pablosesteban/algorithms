/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

/**
 * Data structure to represent a weighted edge between two vertices.
 * The natural ordering of the edges is based on its weight.
 */
public class Edge implements Comparable<Edge> {
	private int from;
	private int to;
	private double weight;
	
	public Edge(int from, int to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edge e) {
		if (this == e || this.weight == e.getWeight()) {
			return 0;
		}
		
		if (this.weight < e.getWeight()) {
			return -1;
		}
		
		return 1;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " {from: " + from + ", to: " + to + ", weight: " + weight + "}";
	}
}
