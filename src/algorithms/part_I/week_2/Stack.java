/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

/**
 *
 * @author psantama
 */

//LIFO: Last-In-First-Out
public interface Stack<E> {
    //adds an element to the top of the stack
    void push(E item);
    
    //returns and removes the last element added to the stack
    E pop();
    
    boolean isEmpty();
    
    //returns the number of elements in the stack
    int size();
}
