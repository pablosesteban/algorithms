/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban;

public class Algorithms {
	public static void main(String[] args) {
		System.out.println(X.y);
		
		System.out.println(X.s);
    }
}

class T {
	static String s = "HELLO WORLD";
}

class X extends T {
	static String y = "MUNDO";
	
	static {
		System.out.print("HOLA ");
	}
}
