/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Stack;
import java.util.Iterator;

/**
 * An implementation of the Stack ADT based on a fixed capacity array implementation.
 * The primary performance characteristic of this implementation is that the push and pop operations take time independent of the stack size.
 * null elements are not allowed.
 * The order of iteration follows the FIFO, First-In-First-Out, policy, as iterates the internal representation of the stack.
 * 
 * @param <E> type of elements stored in the stack
 */
public class FixedArrayStack<E> implements Stack<E> {
    private final E[] elements;
    private int pointer;
    
    public FixedArrayStack(int capacity) {
        // generic array creation is disallowed in Java for historical and technical reasons
        elements = (E[]) new Object[capacity];
    }
    
    @Override
    public void push(E element) {
        if (size() == elements.length) {
            throw new IllegalStateException("stack is full");
        }
        
        if (element == null) {
            throw new IllegalArgumentException("null elements are not allowed");
        }
        
        elements[pointer++] = element;
    }

    @Override
    public E pop() {
        if (size() == 0) {
            throw new IllegalStateException("stack is empty");
        }
        
        E element = elements[--pointer];
        
        elements[pointer] = null;
        
        return element;
    }

    @Override
    public boolean isEmpty() {
        return pointer == 0;
    }

    @Override
    public int size() {
        // returns the number of elements in the stack, not the capacity
        return pointer;
    }

    @Override
    public String toString() {
        String name = getClass().getSimpleName();
        
        if (size() == 0) {
            return name + "{elements: {}}";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(name);
        sb.append("{elements: {");
        
        for (Iterator iterator = iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            sb.append(", ");
        }
        
        int lastComma = sb.lastIndexOf(",");
        sb.deleteCharAt(lastComma);
        
        int lastWitheSpace = sb.lastIndexOf(" ");
        sb.deleteCharAt(lastWitheSpace);
        
        sb.append("}}");
        
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator<>();
    }
    
    private class StackIterator<E> implements Iterator<E> {
        private int pointer = 0;
        
        @Override
        public boolean hasNext() {
            return pointer < elements.length && elements[pointer] != null;
        }

        @Override
        public E next() {
            return (E) elements[pointer++];
        }
    }
    
    public static void main(String[] args) {
        FixedArrayStack<String> stack = new FixedArrayStack(5);
        
        System.out.println("----Push Pablo");
        stack.push("Pablo");
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        
        try {
            System.out.println("----Push null");
            stack.push(null);
        } catch (IllegalArgumentException iae) {
            System.out.println("\t--" + iae);
        }
        
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Push Michico");
        stack.push("Michico");
        System.out.println("----Push Alfredo");
        stack.push("Alfredo");
        
        try {
            System.out.println("----Push on full stack");
            stack.push("Maria");
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
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
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        
        System.out.println("----Iteration order");
        for (String element : stack) {
            System.out.println("\t--"+ element);
        }
    }
}
