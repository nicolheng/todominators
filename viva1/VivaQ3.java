import java.util.Scanner;
/**
 *
 * @author synye
 */
public class VivaQ3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, factorCtr = 2, factorSum = 0, j;
        long factorPdt = 1;
        boolean isPrime = true, isSqr = false;
        
        System.out.print("Enter a positive integer larger than 1 : ");
        n = sc.nextInt();
        //Ensure input is larger than 1
        while (n <= 1){
            System.out.print("Input integer is less than or equal to 1. Enter a positive integer that is larger than 1: ");
            n = sc.nextInt();
        }
        //determine whether ineteger is prime, if not count the number of factor
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                isPrime = false;
                factorCtr++;
            }
        }
        //handle overflow case
        boolean isOverflow = false;
        //if integer is not prime, list out factor and display sum and product of factor(if not overflowed)
        if (!isPrime) {
            System.out.println("Integer is not a prime number, it has " + factorCtr + " factors");
            System.out.println("The factors of this integer are: ");
            for (int i = 1; i <= n; i++) {
                if (n % i == 0) {
                    System.out.print(i);
                    factorSum += i;
                    if (!isOverflow) {
                        if (factorPdt > Long.MAX_VALUE / i)
                            isOverflow = true;
                        factorPdt *= i;
                    }
                    if (i != n)
                        System.out.print(", ");
                    else
                        System.out.println();
                    }
            }
            System.out.println("The sum of the factors is " + factorSum);
            if (isOverflow)
                System.out.println("The product of the factors is too large to display");
            else
                System.out.println("The product of the factors is " + factorPdt);
        }
        else
            System.out.println("Integer is a prime number.");
        //check perfect number
        int x = 1, sqr = x * x;
        for (;sqr <= n; x++) {
            if (sqr == n)
                isSqr = true;
            sqr = x * x;
        }
        if (isSqr)
            System.out.println(n + " is a perfect number.");
        else
            System.out.println(n + " is not a perfect number.");
         //print prime number
        int count = 0;
        int y = -1;
        System.out.printf("Prime numbers between 2 and %d: ", n);
        for (int i = 2; i < n; i++) {
            j = 2;
            isPrime = true;
            while (j < i) {
                if (i % j == 0) 
                    isPrime = false;
                j++;
            }
            if (isPrime) {
                if (i != 2) {
                    System.out.print(", ");
                    if (count == 25 + (y * 16)) {
                        System.out.println();
                        y++;
                    }
                }
                System.out.print(i);
                count++;
            }
        }
        System.out.println();
        
        sc.close();
    }
    
}