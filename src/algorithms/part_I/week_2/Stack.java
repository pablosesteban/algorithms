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
public interface Stack<E> {
    void push(E item);
    
    E pop();
    
    boolean isEmpty();
    
    int size();
}
