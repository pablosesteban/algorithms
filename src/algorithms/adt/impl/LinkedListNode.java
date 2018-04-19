/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

/**
 * A recursive data structure that might hold any kind of data, in addition to
 * the LinkedListNode reference that characterizes its role in building linked
 * lists.
 * Linked lists, where each node has one link to the next element in the list,
 * are a fundamental alternative to arrays for structuring a collection of data,
 * although programming with linked lists presents all sorts of challenges and
 * is notoriously difficult to debug. In modern code, the use of safe pointers,
 * automatic garbage collection, and ADTs allows us to encapsulate linked list
 * processing code in just a few classes.
 * To emphasize that we are just using the LinkedListNode class to structure the
 * data, we define no methods and you have to refer directly to the instance
 * variables in code. Because of this it does not implement an abstract data
 * type and classes of this kind are sometimes called RECORDS.
 *
 * @param <E> the data to structure the linked list
 */
class LinkedListNode<E> {
    E value;
    LinkedListNode next;
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + "value: " + value + ", next: " + next + '}';
    }
}
