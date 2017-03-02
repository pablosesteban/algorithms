/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import java.util.Iterator;

/**
 *
 * @author psantama
 */
public interface Deque<E> extends Iterable<E> {
    // is the deque empty?
    public boolean isEmpty();
    
    // return the number of items on the deque
    public int size();
    
    // add the item to the front
    public void addFirst(E item);
    
    // add the item to the end
    public void addLast(E item);
    
    // remove and return the item from the front
    public E removeFirst();
    
    // remove and return the item from the end
    public E removeLast();
    
    // return an iterator over items in order from front to end
    @Override
    public Iterator<E> iterator();
}
