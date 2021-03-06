/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

/**
 * A recursive data structure that might hold any kind of data, in addition to
 * the DoublyLinkedListNode references that characterizes its role in building
 * doubly-linked lists.
 * Doubly-Linked lists, where each node has two links (one in each direction),
 * are a fundamental alternative to arrays for structuring a collection of data,
 * although programming with doubly-linked lists presents all sorts of
 * challenges and is notoriously difficult to debug. In modern code, the use of
 * safe pointers, automatic garbage collection, and ADTs allows us to
 * encapsulate doubly-linked list processing code in just a few classes.
 * To emphasize that we are just using the DoublyLinkedListNode class to
 * structure the data, we define no methods and you have to refer directly to
 * the instance variables in code. Because of this it does not implement an
 * abstract data type and classes of this kind are sometimes called RECORDS.
 *
 * @param <E> the data to structure the doubly-linked list
 */
class DoublyLinkedListNode<E> {
    E value;
    DoublyLinkedListNode<E> next;
    DoublyLinkedListNode<E> previous;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + "value:" + value + ", next:" + next + ", previous:" + (previous == null ? null : previous.value) + '}';
    }
}
