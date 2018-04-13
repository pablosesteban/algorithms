/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import algorithms.adt.Stack;
import java.util.Iterator;

/**
 *
 * @author psantama
 */

/*
every operation takes constant time in the worst case that's a guarantee

use a little extra time and space to deal with the links
*/
public class LinkedStackOfStrings implements Stack<String> {
    private Node<String> first;
    
    @Override
    public void push(String item) {
        if (item == null) {
            throw new IllegalArgumentException("NULL items are not allowed");
        }
        
        Node<String> oldFirst = first;
        
        first = new Node<>();
        first.setItem(item);
        first.setNext(oldFirst);
    }

    @Override
    public String pop() {
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
