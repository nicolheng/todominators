/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package q5;

/**
 *
 * @author user
 */
import java.util.Scanner;
public class Q5FancyVer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //input
        Scanner input = new Scanner (System.in);
        System.out.print("Enter remixed text: ");
        String remixed = input.nextLine();
        
        //initialize var needed
        String original = "";
        int x; //x to decide how many num to jump
        for (int i = 0; i < remixed.length(); i+=x) {
            
            if (remixed.charAt(i)=='R'
                    && remixed.charAt(i+1)=='E'
                    && remixed.charAt(i+2)=='M'
                    && remixed.charAt(i+3)=='I'
                    && remixed.charAt(i+4)=='X') { //found remix
                
                original+= ' '; //skip remix, change into space
                x = 5;
                
            } else {
                original += remixed.charAt(i); //copy the char given
                x = 1;
            }
        }
        
        //remove the space in front or at the back
        //check the index in front until it is not space
        int i = 0;
        while (original.charAt(i)==' ') {
            i++;
        }
        
        //check the index at the back until it is not space
        int f = original.length()-1;
        while (original.charAt(f)==' ') {
            f--;
        }
        
        //move the text into new var without extra space in front or at the back
        //also remove the extra space in the middle
        String originalNoSpace = "";
        for (int j = i; j < (f+1); j++) {
            if (original.charAt(j) == original.charAt(j+1) && original.charAt(j) == ' ') {
                continue;
            }
            originalNoSpace += original.charAt(j);
        }
        
        
        System.out.println("original text: "+ originalNoSpace);
        
        input.close();
        
        
            
                
    }
        
}
