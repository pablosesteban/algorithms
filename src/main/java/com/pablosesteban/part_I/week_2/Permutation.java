/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_2;

import edu.princeton.cs.algs4.StdIn;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author psantama
 */
public class Permutation {
    public static void main(String[] args) throws IOException {
        boolean coursera = false;
        
        RandomizedQueue<String> dequeue = new RandomizedQueue<>();
        
        int count = 0;
        
        if (coursera) {
            while (!StdIn.isEmpty()) {
                dequeue.enqueue(StdIn.readString());
            }
            
            count = Integer.parseInt(args[0]);
        }else {
            BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/queues/duplicates.txt"));
            
            count = 8;
            
            String[] input = br.readLine().split("\\s+");
            
            for (int i = 0; i < input.length; i++) {
                dequeue.enqueue(input[i]);
            }
        }
        
        for (int i = 0; i < count; i++) {
            System.out.println(dequeue.dequeue());
        }
    }
}
