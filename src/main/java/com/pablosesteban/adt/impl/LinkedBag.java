/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.Bag;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A generic iterable implementation of the Bag collection ADT based on a
 * linked list data structure.
 * A linked list is a recursive data structure that is either empty (null) or a
 * reference to a node having a generic type and a reference to a linked list. A
 * linked list represents a sequence of elements where inserting and removing
 * items is somehow easy.
 * The reference to node instance characterize the linked nature of the data
 * structure. It is commonly use the term "link" to refer to node references.
 * The bag is backed by a linked list where each node has only one link to the
 * next element in the list.
 * null elements are allowed.
 * The implementation achieves the two optimum performance goals for collection
 * ADTs: the space used should always be within a constant factor of the
 * collection size and each operation should require time independent of the
 * collection size.
 * 
 * @param <E> the data to structure the linked list
 */
public class LinkedBag<E> implements Bag<E> {
    private LinkedListNode<E> first;
    private int size;
    private int numberOfOperations;

    @Override
    public void add(E element) {
        LinkedListNode<E> oldFirst = first;
        
        first = new LinkedListNode<>();
        first.value = element;
        first.next = oldFirst;
        
        size++;
        
        numberOfOperations++;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedBagIterator();
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        
        if (size() == 0) {
            return className + "{elements: {}}";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(className);
        sb.append("{elements: {");
        sb.append(first.toString());
        sb.append("}}");
        
        return sb.toString();
    }
    
    /**
     * A fail-fast iterator.
     * Immediately throws a ConcurrentModificationException if the client
     * modifies the collection (via push or pop operations) during iteration.
     * 
     * @param <E> the data stored in the linked list
     */
    private class LinkedBagIterator implements Iterator<E> {
        private LinkedListNode<E> currentNode;
        private final int currentNumberOfOperations;
        
        LinkedBagIterator() {
            currentNode = first;
            currentNumberOfOperations = numberOfOperations;
        }
        
        @Override
        public boolean hasNext() {
            if (currentNumberOfOperations != numberOfOperations) {
                throw new ConcurrentModificationException();
            }
            
            return currentNode != null;
        }
        
        @Override
        public E next() {
            if (currentNumberOfOperations != numberOfOperations) {
                throw new ConcurrentModificationException();
            }
            
            E value = (E) currentNode.value;
            
            currentNode = currentNode.next;
            
            return value;
        }
    }
    
    public static void main(String[] args) {
        LinkedBag<String> bag = new LinkedBag<>();
        
        System.out.println("----Add Pablo");
        bag.add("Pablo");
        System.out.println("----Add Carlos");
        bag.add("Carlos");
        System.out.println("----Add null");
        bag.add(null);
        System.out.println("----Add Andres");
        bag.add("Andres");
        
        System.out.println("----bag: " + bag);
        System.out.println("----size: " + bag.size());
        
        System.out.println("----Add Alvaro");
        bag.add("Alvaro");
        System.out.println("----Add Alfredo");
        bag.add("Alfredo");
        System.out.println("----Add Maria");
        bag.add("Maria");
        
        System.out.println("----bag: " + bag);
        System.out.println("----size: " + bag.size());
        
        System.out.println("----Iteration order");
        for (String element : bag) {
            System.out.println("\t--"+ element);
        }
        
        System.out.println("----Add operation while iteration");
        try {
            for (String element : bag) {
                System.out.println("\t--"+ element);

                bag.add("Test");
            }
        }catch (ConcurrentModificationException cme) {
            System.out.println("\t--"+ cme);
        }
    }
}
