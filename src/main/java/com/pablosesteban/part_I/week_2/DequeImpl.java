/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author psantama
 */
public class DequeImpl<Item> implements Deque<Item> {
    private Node<Item> first, last;
    private int count;
    public static int nodeInstances;
    
    @Override
    public boolean isEmpty() {
        return first == null || last == null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("null items are not allowed");
        }
        
        Node<Item> oldFirst = first;
        
        first = new Node<>();
        first.setItem(item);
        first.setNext(oldFirst);
        
        if (isEmpty()) {
            last = first;
        }else {
            oldFirst.setPrevious(first);
        }
        
        ++count;
    }

    @Override
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("null items not allowed");
        }
        
        Node<Item> oldLast = last;
        
        last = new Node<>();
        last.setItem(item);
        last.setNext(null);
        last.setPrevious(oldLast);
        
        if (isEmpty()) {
            first = last;
        }else {
            oldLast.setNext(last);
        }
        
        ++count;
    }

    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        Item item = first.getItem();
        
        //the old first element is NOT prepared for GC as there is a references pointing to it inside "previous" field inside next element!
        first = first.getNext();
        
        if  (size() > 1) {
            //manually remove that reference
            first.setPrevious(null);
        }else {
            first = last = null;
        }
        
        --count;
        
        return item;
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        
        Item item = last.getItem();
        
        //the old last element is NOT prepared for GC as there is a references pointing to it inside "next" field inside previous element!
        last = last.getPrevious();
        
        if (size() > 1) {
            //manually remove that reference
            last.setNext(null);
        }else {
            first = last = null;
        }
        
        --count;
        
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Itr<>();
    }
    
    private class Itr<Item> implements Iterator<Item> {
        private Node<Item> current = (Node<Item>) first;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            
            Item item = current.getItem();
            
            current = current.getNext();
            
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        public Node() {
            nodeInstances++;
        }
        
        public Item getItem() {
            return item;
        }
        
        public void setItem(Item item) {
            this.item = item;
        }
        
        public Node<Item> getNext() {
            return next;
        }
        
        public void setNext(Node<Item> next) {
            this.next = next;
        }

        public Node<Item> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<Item> previous) {
            this.previous = previous;
        }

        @Override
        public String toString() {
            return "{item: " + item + ", next: " + next + "}";
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            
            System.out.println("FINALIZED!");
            
            nodeInstances--;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        for (Item item : this) {
            sb.append("\t");
            sb.append(item);
            sb.append("\n");
        }
        
        sb.append("}");
        
        return sb.toString();
    }
}
