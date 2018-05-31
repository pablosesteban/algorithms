/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_2;

/**
 *
 * @author psantama
 */
public class Node<E> {
    private E item;
    private Node<E> next;
    
    public E getItem() {
        return item;
    }
    
    public void setItem(E item) {
        this.item = item;
    }
    
    public Node<E> getNext() {
        return next;
    }
    
    public void setNext(Node<E> next) {
        this.next = next;
    }
}
