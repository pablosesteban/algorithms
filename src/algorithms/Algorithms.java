/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.Queue;
import algorithms.part_I.week_2.ResizingArrayQueueOfStrings;

/**
 *
 * @author psantama
 */
public class Algorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue queue = new ResizingArrayQueueOfStrings();
        queue.enqueue("pablo");
        queue.enqueue("maria");
        queue.enqueue("prolo");
        queue.enqueue("roberT");
        System.out.println(queue);
        System.out.println(queue.size());
        
        System.out.println(queue.dequeue());
        System.out.println(queue);
        System.out.println(queue.size());
        
        System.out.println(queue.dequeue());
        System.out.println(queue);
        System.out.println(queue.size());
        
        System.out.println(queue.dequeue());
        System.out.println(queue);
        System.out.println(queue.size());
        
        System.out.println(queue.dequeue());
        System.out.println(queue);
        System.out.println(queue.size());
        
        try {
            System.out.println(queue.dequeue());
            System.out.println(queue);
            System.out.println(queue.size());
        }catch (IllegalStateException ise) {
            System.out.println(ise.getMessage());
        }
        
        queue.enqueue("lil' funesto");
        System.out.println(queue);
        System.out.println(queue.size());
        
        queue.enqueue("hugo");
        System.out.println(queue);
        System.out.println(queue.size());
        
        queue.enqueue("uri");
        System.out.println(queue);
        System.out.println(queue.size());
    }
    
}
