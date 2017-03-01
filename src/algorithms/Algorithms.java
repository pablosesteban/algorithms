/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.Queue;
import algorithms.part_I.week_2.ResizingArrayQueueOfStrings;
import algorithms.part_I.week_2.ResizingArrayStackIterable;
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
        ResizingArrayStackIterable<String> stackOfStrings = new ResizingArrayStackIterable<>();
        
        stackOfStrings.push("pablo");
        stackOfStrings.push("prolo");
        stackOfStrings.push("funesto");
        
        System.out.println(stackOfStrings);
        
        stackOfStrings.pop();
        stackOfStrings.pop();
        stackOfStrings.push("roberT");
        
        System.out.println(stackOfStrings);
        
        stackOfStrings.pop();
        stackOfStrings.pop();
        stackOfStrings.pop();
        
        for (String item : stackOfStrings) {
            System.out.println(item);
        }
    }
    
}
