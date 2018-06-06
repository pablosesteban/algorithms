/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.SelfOrganizingList;

/**
 * Implementation of a self-organizing list based on the move to front strategy
 * backed by a doubly-linked list.
 * The implementation uses a reorganizing algorithm in such a way that when a
 * previously unseen element is added, it is inserted at the front of the list
 * and, when a duplicate element is added, it is deleted from the list and
 * reinserted at the beginning.
 * This implementation has the advantage of being easily implemented and
 * requiring no extra memory. On the other hand, it may prioritize infrequently
 * accessed nodes, i.e. if an uncommon node is accessed even once, it is moved
 * to the head of the list.
 * Useful for caching, data compression, and other applications where items that
 * have been recently accessed are more likely to be re-accessed.
 * 
 * @param <E> the data to structure the doubly-linked list
 */
public class MoveToFrontStrategy<E> implements SelfOrganizingList<E> {
    private DoublyLinkedListNode<E> first;
    private int size;
    
    @Override
    public void add(E element) {
        if (size() == 0) {
            addFirst(element);
            
            return;
        }
        
        for(DoublyLinkedListNode<E> n = first; n != null; n = n.next) {
            if (n.value.equals(element)) {
                remove(n);
                
                break;
            }
        }
        
        addFirst(element);
    }
    
    @Override
    public E get(E element) {
        E searched = null;
        
        for(DoublyLinkedListNode<E> n = first; n != null; n = n.next) {
            if (n.value.equals(element)) {
                return (E) n.value;
            }
        }
        
        return searched;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
    
    private void addFirst(E element) {
        DoublyLinkedListNode<E> oldFirst = first;
        
        first = new DoublyLinkedListNode<>();
        first.value = element;
        
        if (size() != 0) {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        
        size++;
    }
    
    private void remove(DoublyLinkedListNode<E> n) {
        // check if n is the first node
        if (n.previous == null) {
            first = n.next;
            first.previous = null;
            
            size--;
            
            return;
        }
        
        // check if n is the last node
        if (n.next == null) {
            n.previous.next = null;
            
            size--;
            
            return;
        }
        
        n.previous.next = n.next;
        n.next.previous = n.previous;
        
        size--;
    }
    
    public static void main(String[] args) {
        MoveToFrontStrategy<Character> selfOrganizingList = new MoveToFrontStrategy<>();
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add D");
        selfOrganizingList.add('D');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add E");
        selfOrganizingList.add('E');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        System.out.println("size: " + selfOrganizingList.size());
    }
}
