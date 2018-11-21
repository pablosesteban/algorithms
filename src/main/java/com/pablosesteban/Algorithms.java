/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Algorithms {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("a");
        l.add("b");
        l.add("c");
        
        Stream<String> stream1 = l.stream();
        
        Stream<String> stream2 = stream1.map((e) -> {return e.toUpperCase();});
        
        for (String string : l) {
			System.out.println(l);
		}
    }
}
