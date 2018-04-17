/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Stack;
import java.util.Iterator;

/**
 * A generic iterable implementation of the Stack collection ADT based on a
 * linked list data structure.
 * A linked list is a recursive data structure that is either empty (null) or a
 * reference to a node having a generic type and a reference to a linked list. A
 * linked list represents a sequence of elements where inserting and removing
 * items is somehow easy.
 * The reference to node instance characterize the linked nature of the data
 * structure. It is commonly use the term "link" to refer to node references.
 * The stack is backed by a simple "linked list" as each node has only one link
 * to the next element in the list (in contrast to a "doubly-linked list" where
 * each node has two links, one in each direction).
 * null elements are allowed.
 * The implementation achieves the two optimum performance goals for collection
 * ADTs: the space used should always be within a constant factor of the
 * collection size and each operation should require time independent of the
 * collection size.
 * 
 * @param <E> any data that we might want to structure with a linked list
 */
public class LinkedStack<E> implements Stack<E> {
    // top of the stack, i.e. link to most recently added node
    private LinkedListNode first;
    private int size;
    
    @Override
    public void push(E item) {
        LinkedListNode oldFirst = first;
        
        first = new LinkedListNode();
        first.value = item;
        first.next = oldFirst;
        
        size++;
    }

    @Override
    public E pop() {
        if (size() == 0) {
            throw new IllegalStateException("stack is empty");
        }
        
        E value = (E) first.value;
        
        // the old first node is going to be an orphan object reclaimed by the GC
        first = first.next;
        
        size--;
        
        return value;
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
        return new LinkedStackIterator<>();
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
    
    private class LinkedStackIterator<E> implements Iterator<E> {
        private LinkedListNode current = first;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E value = (E) current.value;
            
            current = current.next;
            
            return value;
        }
    }
    
    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<>();
        
        System.out.println("----Push Pablo");
        stack.push("Pablo");
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        System.out.println("----Push null");
        stack.push(null);
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Push Alvaro");
        stack.push("Alvaro");
        System.out.println("----Push Alfredo");
        stack.push("Alfredo");
        System.out.println("----Push Maria on full stack");
        stack.push("Maria");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        
        try {
            System.out.println("----Pop on empty stack");
            stack.pop();
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Push Pablo");
        stack.push("Pablo");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----LIFO iteration order");
        for (String element : stack) {
            System.out.println("\t--"+ element);
        }
    }
}
