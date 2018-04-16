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
 * reference to a Node having a generic type and a reference to a linked list. A
 * linked list represents a sequence of elements where inserting and removing
 * items is somehow easy.
 * The reference to Node instance characterize the linked nature of the data
 * structure. It is commonly use the term "link" to refer to Node references.
 * The stack is backed by a simple "linked list" as each Node has only one link
 * to the next element in the list (in contrast to a "doubly-linked list" where
 * each Node has two links, one in each direction).
 * null elements are allowed.
 * 
 * @param <E> any data that we might want to structure with a linked list
 */
public class LinkedStack<E> implements Stack<E> {
    // top of the stack
    private Node first;
    private int size;
    
    @Override
    public void push(E item) {
        Node oldFirst = first;
        
        first = new Node();
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        sb.append(first.toString());
        sb.append("}}");
        
        return sb.toString();
    }
    
    /**
     * A recursive data structure that might hold any kind of data, in addition to the
     * Node reference that characterizes its role in building linked lists.
     * To emphasize that we are just using the Node class to structure the data,
     * we define no methods and you have to refer directly to the instance
     * variables in code. Because of this it does not implement an abstract data
     * type and classes of this kind are sometimes called RECORDS.
     * 
     * @param <E> any data that we might want to structure with a linked list
     */
    private class Node<E> {
        private E value;
        private Node next;

        @Override
        public String toString() {
            return "Node{" + "value: " + value + ", next: " + next + '}';
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
        
//        System.out.println("----Iteration order");
//        for (String element : stack) {
//            System.out.println("\t--"+ element);
//        }
    }
}
