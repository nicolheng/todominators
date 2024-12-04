/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package q2;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class Q2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        int n1, n2, n3, n4, a, b, step1 = 0, step2 = 0, step3 = 0, step4 = 0;
        System.out.println("""
                           Constraints:
                                        1 <= n <= 10^9
                                        1 <= a <= n
                                        2 <= b <= 10^5
                           """);
        System.out.print("Enter the number for n, a and b : ");
        n1 = sc.nextInt();
        a = sc.nextInt();
        b = sc.nextInt();
        while (n1 < 1 || n1 > 1000000000 || a < 1 || a > n1 || b < 2 || b > 100000) {
            System.out.println("Input is invalid. Try again.");
            System.out.print("Enter the number for n, a and b : ");
            n1 = sc.nextInt();
            a = sc.nextInt();
            b = sc.nextInt();
        }
        n2 = n1;        //divide then minus
        n3 = n1;
        n4 = n1;
        while (n1 > 1) {
            while (n1 % b == 0) {
                n1 /= b;
                step1++;
            }
            while (n1 > a) {
                n1 -= a;
                step1++;
            }
            if (n1 % b != 0 && n1 <= a)
                break;
        }
        //minus then divide
        while (n2 > 1) {
            while (n2 > a) {
                n2 -= a;
                step2++;
            }
            while (n2 % b == 0) {
                n2 /= b;
                step2++;
            }
            if (n2 % b != 0 && n2 <= a)
                break;
        }
        while (n3 > 1) {
            if (n3 % b == 0) {
                n3 /= b;
                step3++;
            }
            if (n3 > a) {
                n3 -= a;
                step3++;
            }
            if (n3 % b != 0 && n3 <= a)
                break;
        }
        //minus then divide
        while (n4 > 1) {
            if (n4 > a) {
                n4 -= a;
                step4++;
            }
            if (n4 % b == 0) {
                n4 /= b;
                step4++;
            }
            if (n4 % b != 0 && n4 <= a)
                break;
        }
        
        //compare which step is fewer
        if (n1 == 1 && step1 <= step2 && step1 <= step3 && step1 <= step4)
            System.out.println(step1);
        else if (n2 == 1 && step2 <= step1 && step2 <= step3 && step2 <= step4)
            System.out.println(step2);
        else if (n3 == 1 && step3 <= step1 && step3 <= step2 && step3 <= step4)
            System.out.println(step3);
        else if (n4 == 1 && step4 <= step1 && step4 <= step2 && step4 <= step3)
            System.out.println(step4);
        else
            System.out.println("-1");
        
        sc.close();
    }
    
}
