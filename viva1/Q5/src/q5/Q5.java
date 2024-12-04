/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package q5;

/**
 *
 * @author user
 */
//
import java.util.Scanner;
public class Q5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        System.out.print("Enter text: ");
        String text = input.nextLine();
        
        //initialize var
        String original;
        
        //removing process
        //remove remix
        original = text.replaceAll("REMIX", " ");
        //remove extra space, trim remove the one in the front and at the end, replace all replace the one extra in the middle
        original = original.replaceAll("A", "");
        original = original.trim().replaceAll(" +"," ");
        System.out.println("original text: "+ original);
       
        input.close();
    }
    
}
