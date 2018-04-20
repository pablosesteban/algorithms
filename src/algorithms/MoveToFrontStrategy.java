/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms;

import algorithms.adt.impl.DoublyLinkedListNode;

/**
 * Implementation of a self-organizing list based on the move to front strategy
 * backed by a doubly-linked list.
 * A self-organizing list is a list that reorders its elements based on some
 * self-organizing heuristic to improve average access time. The aim of a
 * self-organizing list is to improve efficiency of linear search by moving more
 * frequently accessed items towards the head of the list.
 * The implementation uses a reorganizing algorithm in such a way that when a
 * previously unseen element is added, it is inserted at the front of the list
 * and, when a duplicate element is added, it is deleted from the list and
 * reinserted at the beginning.
 * 
 * @param <E> the data to structure the doubly-linked list
 */
public class MoveToFrontStrategy<E> {
    private DoublyLinkedListNode first;
    private int size;
    
    public void add(E element) {
        if (size() == 0) {
            addFirst(element);
            
            return;
        }
        
        for(DoublyLinkedListNode n = first; n != null; n = n.next) {
            if (n.value.equals(element)) {
                remove(n);
                
                break;
            }
        }
        
        addFirst(element);
    }
    
    private void addFirst(E element) {
        DoublyLinkedListNode oldFirst = first;
        
        first = new DoublyLinkedListNode();
        first.value = element;
        
        if (size() != 0) {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        
        size++;
    }
    
    private void remove(DoublyLinkedListNode n) {
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
    
    public int size() {
        return size;
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
        MoveToFrontStrategy<Character> selfOrganizingList = new MoveToFrontStrategy<>();
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add D");
        selfOrganizingList.add('D');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add E");
        selfOrganizingList.add('E');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add B");
        selfOrganizingList.add('B');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add C");
        selfOrganizingList.add('C');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
        
        System.out.println("----Add A");
        selfOrganizingList.add('A');
        
        System.out.println(selfOrganizingList);
    }
}
