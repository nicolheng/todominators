/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package q4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Q4 {
    static Scanner input=new Scanner(System.in);
    static boolean orderedPizza=false;
    static boolean orderedDrinks=false;
    static boolean orderedDessert=false;
    static double total=0;

    
    public static void main(String[] args) {
    mainMenu:
    while(true) {
         System.out.print("""
                            \nWelcome to Maroni's Pizza!
                            1. Pizza
                            2. Drinks
                            3. Dessert
                            4. CHECKOUT
                          
                              """);
        System.out.print("Pick an option: ");
        int option=input.nextInt();
        
        switch (option) {
            case 1 : 
                PizzaMenu();

                continue mainMenu;
            case 2:
                DrinksMenu();

                continue mainMenu;
            case 3 :
                DessertMenu();

                continue mainMenu;
            case 4:
                Checkout(orderedPizza,orderedDrinks,orderedDessert);
                break mainMenu;
            default:
                System.out.println("Invalid option. Please try again.");
                continue mainMenu;
                }
        }
    }
    public static void PizzaMenu() {
        System.out.print("""
                            \nPIZZA
                            1 Chicken Peperoni - RM15
                            2 Chicken Supreme  - RM18
                            3 Vegan Indulgence - RM12
                            4 Beef Delight     - RM22
                            5 Margherita       - RM9
                            6 BACK TO MAIN MENU 
                                                    
                              """);
        Pizzaloop:
        while(true) {
            System.out.print("Pick an option:");
            int optionPizza=input.nextInt();
            

                if (optionPizza==1){
                    addTocart("Chicken Peperoni",15);
                    orderedPizza=true;
                    continue Pizzaloop;}
                else if (optionPizza==2){
                    addTocart("Chicken Supreme",18);
                    orderedPizza=true;
                    continue Pizzaloop;}
                else if (optionPizza==3){
                    addTocart("Vegan Indulgence",12);
                    orderedPizza=true;
                    continue Pizzaloop;}
                else if (optionPizza==4){
                    addTocart("Beef Delight",22);
                    orderedPizza=true;
                    continue Pizzaloop;}
                else if (optionPizza==5){
                    addTocart("Margherita",9);
                    orderedPizza=true;
                    continue Pizzaloop;}
                else if (optionPizza==6)
                    break Pizzaloop;
                else
                    System.out.println("Invalid option. Please try again.");   
            }
        }
    
    
           public static void DrinksMenu() {
                    System.out.print("""
                                 \nDRINKS 
                                 1 Strawberry Smoothie - RM8
                                 2 Banana Smoothie - RM8
                                 3 Mocktail - RM12
                                 4 Soft Drink - RM5
                                 5 Mineral Water - RM3
                                 6 BACK TO MAIN MENU
                                 
                                 """);
           Drinksloop:
           while(true) {
           System.out.print("Pick an option:");
           int optionDrinks=input.nextInt();
           
           switch (optionDrinks) {
               case 1:
                   addTocart("Strawberry Smoothie",8);
                   orderedDrinks=true;
                   continue Drinksloop;
               case 2:
                   addTocart("Banana Smoothie",8);
                   orderedDrinks=true;
                   continue Drinksloop;
               case 3:
                   addTocart("Mocktail",12);
                   orderedDrinks=true;
                   continue Drinksloop;
               case 4:
                   addTocart("Soft Drink",5);
                   orderedDrinks=true;
                   continue Drinksloop;
               case 5:
                  addTocart("Mineral Water ",3);
                  orderedDrinks=true;
                  continue Drinksloop; 
               case 6:
                   break Drinksloop;
               default:
                   System.out.println("Invalid option. Please try again.");  
           }
                }
            }
           
           public static void DessertMenu() {
                   System.out.print("""
                                  \nDESSERT
                                  1 Tiramisu - RM7
                                  2 Strawberry Shortcake - RM10
                                  3 Green Jello - RM4
                                  4 Creme Brulee - RM15
                                  5 Raspberry Pie - RM20
                                  6 BACK TO MAIN MENU
                                 
                                 """);
               Dessertloop:
               while(true) {
                   System.out.print("Pick an option: ");
                   int optionDessert=input.nextInt();
                   
                   switch(optionDessert) {
                       case 1 : 
                           addTocart("Tiramisu",7);
                           orderedDessert=true;
                           continue Dessertloop;
                       case 2: 
                           addTocart("Strawberry Shortcake",10);
                           orderedDessert=true;
                           continue Dessertloop;
                       case 3 : 
                           addTocart("Green Jello",4);
                           orderedDessert=true;
                           continue Dessertloop;
                       case 4: 
                           addTocart("Creme Brulee",15);
                           orderedDessert=true;
                           continue Dessertloop;
                       case 5: 
                           addTocart("Raspberry Pie",20);
                           orderedDessert=true;
                           continue Dessertloop;
                       case 6: 
                           break Dessertloop;
                       default:
                           System.out.println("Invalid option. Please try again.");
                   }
               }
           }
                   public static void addTocart(String item, double price) {
                       total+=price;
                       System.out.println("Added " + item);
                       System.out.printf("Current total: RM%.1f\n\n",total);
                   }
                   public static void Checkout(boolean orderedPizza ,boolean orderedDrinks, boolean orderedDessert) {
                       System.out.printf("\nYour total is RM%.1f!\n",total);
                       if (orderedPizza && orderedDrinks && orderedDessert) {
                           double newtotal=total*0.8;
                           System.out.println("You've availed the One-of-each offer. You get a 20% discount!");
                           System.out.printf("Your new total is RM%.1f!\n",newtotal);
                       }
                       
                       System.out.println("Have a nice day!");
                    }
}