/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.Deque;
import algorithms.part_I.week_2.DequeImpl;

/**
 *
 * @author psantama
 */
public class Algorithms {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Deque<Integer> deque = new DequeImpl<>();
        
        System.out.println("add first 1");
        deque.addFirst(1);
        System.out.println("add first 2");
        deque.addFirst(2);
        System.out.println("add last 3");
        deque.addLast(3);
        System.out.println("add last 4");
        deque.addLast(4);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("remove first: " + deque.removeFirst());
        System.out.println("remove first: " + deque.removeFirst());
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("remove last: " + deque.removeLast());
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("remove first: " + deque.removeFirst());
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("add first 1");
        deque.addFirst(1);
        System.out.println("add first 2");
        deque.addFirst(2);
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("remove last: " + deque.removeLast());
        System.out.println("remove last: " + deque.removeLast());
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("add last 1");
        deque.addLast(1);
        System.out.println("add last 2");
        deque.addLast(2);
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
        
        System.out.println("remove first: " + deque.removeFirst());
        System.out.println("remove first: " + deque.removeFirst());
        
        System.gc();
        Thread.sleep(2000);
        
        System.out.println("----------------");
        System.out.println("size: " + deque.size());
        System.out.println(deque);
        System.out.println("Number of nodes: " + DequeImpl.nodeInstances);
        System.out.println("----------------");
    }
}
