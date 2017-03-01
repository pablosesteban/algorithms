/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.Queue;
import algorithms.part_I.week_2.ResizingArrayQueueOfStrings;
import algorithms.part_I.week_2.ResizingArrayStackOfStrings;
import algorithms.part_I.week_2.Stack;

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
        
        for (int i = 0; i < 10; i++) {
            queue.enqueue("pablo_" + i);
        }
        
        System.out.println(queue);
        System.out.println(queue.size());
        
//        for (int i = 0; i < 7; i++) {
//            queue.dequeue();
//        }
//        
//        System.out.println(queue);
//        System.out.println(queue.size());
        
//        Stack stack = new ResizingArrayStackOfStrings();
//        
//        for (int i = 0; i < 10; i++) {
//            stack.push("pablo_" + i);
//        }
//        
//        System.out.println(stack);
//        System.out.println(stack.size());
//        
//        for (int i = 0; i < 7; i++) {
//            stack.pop();
//        }
//        
//        System.out.println(stack);
//        System.out.println(stack.size());
    }
    
}
