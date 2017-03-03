/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_2;

import edu.princeton.cs.algs4.StdRandom;

/**
 *
 * @author psantama
 */
public class Permutation {
   public static void main(String[] args) {
//       String[] input = {"AA", "BB", "BB", "BB", "BB", "BB", "CC", "CC"};
       String[] input = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
       int count = 3;
       boolean[] pickedInput = new boolean[input.length];
       
       DequeImpl dequeue = new DequeImpl();
       
       for (int i = 0; i < count; i++) {
           int rand = StdRandom.uniform(0, 2);
           
           int index = StdRandom.uniform(0, input.length);
           
           while (pickedInput[index] == true) {
               index = StdRandom.uniform(0, count);
           }
           
           if (rand == 0) {
               dequeue.addFirst(input[index]);
           }else {
               dequeue.addLast(input[index]);
           }
           
           pickedInput[index] = true;
       }
       
       System.out.println(dequeue);
   }
}
