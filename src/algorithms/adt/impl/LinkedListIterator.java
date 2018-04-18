 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.impl;

import java.util.Iterator;

public class LinkedListIterator<E> implements Iterator<E> {
    private LinkedListNode current;
    
    LinkedListIterator(LinkedListNode first) {
        this.current = first;
    }
    
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
