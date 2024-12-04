/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VivaQ6;

import java.util.Scanner;

public class VivaQ6 {


    public static void main(String[] args) 
    {
        Scanner sc=new Scanner(System.in);
        int number,countSecondLarge=0,countMax=1,sum=0;
        int max=Integer.MIN_VALUE,secondLarge=Integer.MIN_VALUE;
        boolean negative=false;
        
        System.out.print("Enter numbers: ");
        
        while(true)
        {number=sc.nextInt();
        
        if(number==0)
            break;
            
        if(number>max)
            {secondLarge=max;
            max=number;
            countMax=1;
            countSecondLarge=(secondLarge!=Integer.MIN_VALUE)? 1:0 ;}
            
        else if(number==max)
                countMax++;
        
        else if(number<max && number>secondLarge)
            {secondLarge=number;
            countSecondLarge=1;}
        
        else if(number==secondLarge)
            countSecondLarge++;
            
        if(number<0)
            negative=true;
        
        sum+=number;
        }
        
        
        System.out.println("The largest number is "+max);
        System.out.println("The occurrence count of the largest number is "+countMax);
        
        if(countSecondLarge>0)
            {System.out.println("The second-largest number is "+secondLarge);
            System.out.println("The occurence count of the second-largest number is "+countSecondLarge);}
        
        else
            System.out.println("There was no valid second-largest number. ");
        
        System.out.println("The total sum of all numbers is "+sum);
        
        if(negative==true)
            System.out.println("Negative numbers were entered");
            
    }
    
}
