/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_2;

import main.java.com.pablosesteban.adt.Queue;
import java.util.Iterator;

/**
 *
 * @author psantama
 */
public class LinkedQueueOfStrings implements Queue<String> {
    private Node<String> first, last;
    
    @Override
    public void enqueue(String item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        Node<String> oldLast = last;
        last = new Node<>();
        last.setItem(item);
        last.setNext(null);
        
        if (isEmpty()) {
            first = last;
        }else {
            oldLast.setNext(last);
        }
    }

    @Override
    public String dequeue() {
        if (size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        
        String item = first.getItem();
        
        //the old first element is prepared for GC (there is no references pointing to it)
        first = first.getNext();
        
        return item;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        int count = 0;
        
        if (first != null) {
            count++;
            
            Node<String> node = first;
            
            while ((node = node.getNext()) != null) {
                count++;
            }
        }
        
        return count;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        if (first != null) {
            sb.append("  ");
            
            sb.append(first.getItem());
            
            sb.append("\n");
            
            Node<String> node = first;
            
            while ((node = node.getNext()) != null) {
                sb.append("  ");
                
                sb.append(node.getItem());
                
                sb.append("\n");
            }
            
            sb.append("}");
        }
        
        return sb.toString();
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
