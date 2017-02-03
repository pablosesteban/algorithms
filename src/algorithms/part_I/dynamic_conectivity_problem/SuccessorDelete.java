/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import java.util.Arrays;

/**
 *
 * @author psantama
 */
public class SuccessorDelete {
    private int[] id;
    
    public SuccessorDelete(int[] numbers) {
        id = new int[numbers.length];
        
        for (int i = 0; i < numbers.length; i++) {
            if (i == 0){
                id[i] = numbers[i];
            }else {
                if (id[i - 1] > numbers[i]) {
                    id[i] = id[i - 1];
                    
                    id[i - 1] = numbers[i];
                }
            }
        }
    }
    
    public void remove(int i) {
        id[i] = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(id);
    }
    
    public static void main(String[] args) {
        System.out.println(new SuccessorDelete(new int[]{7,2,4,1,8}));
    }
}
