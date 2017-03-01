/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author psantama
 */
public class DequeImpl<E> implements Deque<E> {
    private Node<E> head, tail;
    private int count;
    
    // construct an empty deque
    public DequeImpl() {
        
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void addFirst(E item) {
        if (item == null) {
            throw new NullPointerException("null items not allowed");
        }
        
        Node<E> newNode = new Node<>();
        newNode.setItem(item);
        
        if (size() == 0) {
            head = newNode;
            tail = newNode;
        }
        
    }

    @Override
    public void addLast(E item) {
        if (item == null) {
            throw new NullPointerException("null items not allowed");
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr<>();
    }
    
    private class Itr<E> implements Iterator<E> {
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        
    }
}
