/**
 *
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Stack;
import java.util.Iterator;

/**
 * A generic iterable implementation of the Stack collection ADT based on a
 * doubly-linked list abstract entity.
 * 
 * @param <E>
 */
public class LinkedStack<E> implements Stack<E> {
    private Node first;
    private Node last;
    private int size;
    
    @Override
    public void push(E item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E pop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * 
     * @param <E> 
     */
    private class Node<E> {
        private E value;
        private Node next;
        private Node previous;
    }
}
