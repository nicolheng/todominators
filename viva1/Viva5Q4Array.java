/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.util.Scanner;
import java.util.ArrayList;
public class Viva5Q4Array {
    static Scanner input=new Scanner(System.in);
    static boolean orderedPizza=false;
    static boolean orderedDrinks=false;
    static boolean orderedDessert=false;
    static double total=0;
    static ArrayList<String> cart=new ArrayList<String>();
    
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
                orderedPizza=true;
                continue mainMenu;
            case 2:
                DrinksMenu();
                orderedDrinks=true;
                continue mainMenu;
            case 3 :
                DessertMenu();
                orderedDessert=true;
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
        Pizzaloop:
        while(true) {
        System.out.print("""
                            \nPIZZA
                            1 Chicken Peperoni - RM15
                            2 Chicken Supreme  - RM18
                            3 Vegan Indulgence - RM12
                            4 Beef Delight     - RM22
                            5 Margherita       - RM9
                            6 BACK TO MAIN MENU 
                                                    
                              """);
            System.out.print("Pick an option:");
            int optionPizza=input.nextInt();
        
            switch (optionPizza){
                case 1: 
                    addTocart("Chicken Peperoni",15);
                    continue Pizzaloop;
                case 2:
                    addTocart("Chicken Supreme",18);
                    continue Pizzaloop;
                case 3:
                    addTocart("Vegan Indulgence",12);
                    continue Pizzaloop;
                case 4:
                    addTocart("Beef Delight",22);
                    continue Pizzaloop;
                case 5:
                    addTocart("Margherita",9);
                    continue Pizzaloop;
                case 6: 
                    break Pizzaloop;
                default:
                    System.out.println("Invalid option. Please try again.");   
            }
        }
    }
            
           public static void DrinksMenu() {
                Drinksloop:
                while(true) {
                    System.out.print("""
                                 \nDRINKS 
                                 1 Strawberry Smoothie - RM8
                                 2 Banana Smoothie - RM8
                                 3 Mocktail - RM12
                                 4 Soft Drink - RM5
                                 5 Mineral Water - RM3
                                 6 BACK TO MAIN MENU
                                 
                                 """);
           System.out.print("Pick an option:");
           int optionDrinks=input.nextInt();
           
           switch (optionDrinks) {
               case 1:
                   addTocart("Strawberry Smoothie",8);
                   continue Drinksloop;
               case 2:
                   addTocart("Banana Smoothie",8);
                   continue Drinksloop;
               case 3:
                   addTocart("Mocktail",12);
                   continue Drinksloop;
               case 4:
                   addTocart("Soft Drink",5);
                   continue Drinksloop;
               case 5:
                  addTocart("Mineral Water ",3);
                  continue Drinksloop; 
               case 6:
                   break Drinksloop;
               default:
                   System.out.println("Invalid option. Please try again.");  
           }
                }
            }
           
           public static void DessertMenu() {
               Dessertloop:
               while(true) {
                   System.out.print("""
                                  \nDESSERT
                                  1 Tiramisu - RM7
                                  2 Strawberry Shortcake - RM10
                                  3 Green Jello - RM4
                                  4 Crème Brûlée - RM15
                                  5 Raspberry Pie - RM20
                                  6 BACK TO MAIN MENU
                                 
                                 """);
                   System.out.print("Pick an option: ");
                   int optionDessert=input.nextInt();
                   
                   switch(optionDessert) {
                       case 1 : 
                           addTocart("Tiramisu",7);
                           continue Dessertloop;
                       case 2: 
                           addTocart("Strawberry Shortcake",10);
                           continue Dessertloop;
                       case 3 : 
                           addTocart("Green Jello",4);
                           continue Dessertloop;
                       case 4: 
                           addTocart("Creme Brulee",15);
                           continue Dessertloop;
                       case 5: 
                           addTocart("Raspberry Pie",20);
                           continue Dessertloop;
                       case 6: 
                           break Dessertloop;
                       default:
                           System.out.println("Invalid option. Please try again.");
                   }
               }
           }
                   public static void addTocart(String item, double price) {
                       cart.add(item);
                       total+=price;
                       System.out.println("Added " + cart);
                       System.out.printf("Current total: RM%.1f\n",total);
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

                
        
    
    
    
  
    
    
        

