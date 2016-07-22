
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago
 */
public class NovoClass {
    public static void main(String args[]){
      ArrayList matrix = new ArrayList();
      matrix.add(new ArrayList());
      ((ArrayList)matrix.get(0)).add("row 0 col 0");
      ((ArrayList)matrix.get(0)).add("row 0 col 1");
      matrix.add(new ArrayList());
      ((ArrayList)matrix.get(1)).add("row 1 col 0");
      ((ArrayList)matrix.get(1)).add("row 1 col 1");
      int i = 0;
      int j = 0;
      // display contents of matrix
      for(i = 0; i < matrix.size();i++){
         for(j = 0; j < ((ArrayList)matrix.get(i)).size(); j++){
            System.out.print( (String)((ArrayList)matrix.get(i)).get(j) +"  ");
         }
         System.out.println();
      }
   }

}
