/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author psantama
 */
//Union-Find Data Type
public interface UnionFind {
    public void union(int p, int q);
    
    public boolean isConnected(int p, int q);
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(""));
        
        UnionFind uf = new QuickFind(Integer.parseInt(br.readLine()));
        
        String line;
        while((line = br.readLine()) != null) {
            int p = Integer.parseInt(line.split(" ")[0]);
            int q = Integer.parseInt(line.split(" ")[1]);
            
            if (!uf.isConnected(p, q)) {
                uf.union(p, q);
            }
        }
    }
}
