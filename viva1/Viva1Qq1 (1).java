/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package viva1q1;


/**
 *
 * @author kanpenny
 */
import java.util.Scanner;
public class Viva1Qq1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long num;
        do{
            System.out.print("Enter a number: ");
            num = sc.nextLong();
            if(num<=0){
                System.out.println("Invalid input. Please enter a positive integer");
            }
        }while(num<=0);
        
        //repeat the process as long as num has more than one digit
        while(num>=10){
            long sum= 0;//initialize sum
            //loop to calculate the sum of all digits in num
            for(;num>0;num/=10){//eliminate the last digit(has been added to sum) from num
                sum+=num%10;}//extract the last digit(remainder after modulus 10) and add it to sum
            num=sum;//update the num to the sum for the next iteration
            }
        System.out.println("Sum of digits until single digit: " +num);//print final single digit sum
        sc.close();
        }
    }
    

