/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Deque;
import java.util.Iterator;

/**
 * A generic iterable implementation of the Deque collection ADT based on a
 * doubly-linked list data structure.
 * A linked list is a recursive data structure that is either empty (null) or a
 * reference to a node having a generic type and a reference to a linked list. A
 * linked list represents a sequence of elements where inserting and removing
 * items is somehow easy.
 * The reference to node instance characterize the linked nature of the data
 * structure. It is commonly use the term "link" to refer to node references.
 * The deque is backed by a simple doubly-linked list where each node has two
 * links, one in each direction.
 * null elements are allowed.
 * The implementation achieves the two optimum performance goals for collection
 * ADTs: the space used should always be within a constant factor of the
 * collection size and each operation should require time independent of the
 * collection size.
 * 
 * @param <E> any data that we might want to structure with a doubly-linked list
 */
public class LinkedDeque<E> implements Deque<E> {
    // head of the queue, i.e. link to least recently added at the beginning node
    private DoublyLinkedListNode first;
    // end of the queue, i.e. link to most recently added at the end node
    private DoublyLinkedListNode last;
    private int size;

    @Override
    public void pushHead(E element) {
        DoublyLinkedListNode oldFirst = first;
        
        first = new DoublyLinkedListNode();
        first.value = element;
        
        if (size() == 0) {
            last = first;
        }else {
            first.next = oldFirst;
        
            oldFirst.previous = first;
        }
        
        size++;
    }

    @Override
    public void pushTail(E element) {
        DoublyLinkedListNode oldLast = last;
        
        last = new DoublyLinkedListNode();
        last.value = element;
        
        if (size() == 0) {
            first = last;
        }else {
            last.previous = oldLast;
            
            oldLast.next = last;
        }
        
        size++;
    }

    @Override
    public E popHead() {
        if (size() == 0) {
            throw new IllegalStateException("deque is empty");
        }
        
        E value = (E) first.value;
        
        first = first.next;
        
        if (size() > 1) {
            first.previous = null;
        }
        
        size--;
        
        return value;
    }

    @Override
    public E popTail() {
        if (size() == 0) {
            throw new IllegalStateException("deque is empty");
        }
        
        E value = (E) last.value;
        
        last = last.previous;
        
        if (size() > 1) {
            last.next = null;
        }
        
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
        return new LinkedQueueIterator<>();
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
    
    private class LinkedQueueIterator<E> implements Iterator<E> {
        private DoublyLinkedListNode current = first;
        
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
        LinkedDeque<String> deque = new LinkedDeque<>();
        
        System.out.println("----Push head Pablo");
        deque.pushHead("Pablo");
        System.out.println("----Push head Alvaro");
        deque.pushHead("Alvaro");
        System.out.println("----Push tail Carlos");
        deque.pushTail("Carlos");
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop tail");
        System.out.println("\t--" + deque.popTail());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop tail");
        System.out.println("\t--" + deque.popTail());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop tail");
        System.out.println("\t--" + deque.popTail());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Push head Pablo");
        deque.pushHead("Pablo");
        System.out.println("----Push head Alvaro");
        deque.pushHead("Alvaro");
        System.out.println("----Push tail Carlos");
        deque.pushTail("Carlos");
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----FIFO iteration order");
        for (String element : deque) {
            System.out.println("\t--"+ element);
        }
        
        System.out.println("----Pop head");
        System.out.println("\t--" + deque.popHead());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop head");
        System.out.println("\t--" + deque.popHead());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop head");
        System.out.println("\t--" + deque.popHead());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        try {
            System.out.println("----Pop head on empty deque");
            deque.popHead();
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        try {
            System.out.println("----Pop tail on empty deque");
            deque.popTail();
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        System.out.println("----Push tail Carlos");
        deque.pushTail("Carlos");
        System.out.println("----Push tail Pablo");
        deque.pushTail("Pablo");
        System.out.println("----Push head Alvaro");
        deque.pushHead("Alvaro");
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop head");
        System.out.println("\t--" + deque.popHead());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
        
        System.out.println("----Pop tail");
        System.out.println("\t--" + deque.popTail());
        
        System.out.println("----deque: " + deque);
        System.out.println("----size: " + deque.size());
    }
}
