/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Bag;
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
 * The bag is backed by a simple "linked list" as each node has only one link
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
public class LinkedBag<E> implements Bag<E> {
    private LinkedListNode first;
    private int size;

    @Override
    public void add(E element) {
        LinkedListNode oldFirst = first;
        
        first = new LinkedListNode();
        first.value = element;
        first.next = oldFirst;
        
        size++;
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
        return new LinkedListIterator<>(first);
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
    }
}
