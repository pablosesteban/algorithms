/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_2.FixedArrayStackOfStrings;
import algorithms.part_I.week_2.LinkedStackOfStrings;
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
        Stack stackOfString = new FixedArrayStackOfStrings(10);
        
        stackOfString.push("pablo");
        System.out.println(stackOfString.size());
        
        stackOfString.push("maria");
        System.out.println(stackOfString.size());
        
        stackOfString.push("prolo");
        System.out.println(stackOfString.size());
        
        stackOfString.push("roberT");
        System.out.println(stackOfString.size());
        
        System.out.println(stackOfString);
        
        System.out.println(stackOfString.pop());
        
        System.out.println(stackOfString);
        System.out.println(stackOfString.size());
    }
    
}
