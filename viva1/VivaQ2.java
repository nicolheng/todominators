import java.util.Scanner;
/**
 *
 * @author synye
 */
public class VivaQ2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n1, n2, a, b, step1 = 0, step2 = 0;
        while (true) {
            System.out.print("Enter the number for n, a and b : ");
            n1 = sc.nextInt();
            a = sc.nextInt();
            b = sc.nextInt();
            if (n1>=1 && n1 <= 1000000000 && a<= n1 && a>=1 && b>=2 && b<=100000 ){
                break;
            } else {
                System.out.println("value input invalid. Try again.");
            }
        }
        
        n2 = n1;
        //divide then minus
        while (n1 > 1) {
            while (n1 % b == 0) {
                n1 /= b;
                step1++;
            }
            while (n1 >= a) {
                n1 -= a;
                step1++;
            }
            if (n1 % b != 0 && n1 < a)
                break;
        }
        //minus then divide
        while (n2 > 1) {
            while (n2 >= a) {
                n2 -= a;
                step2++;
            }
            while (n2 % b == 0 && n2 != 0) {
                n2 /= b;
                step2++;
            }
            if (n2 % b != 0 && n2 < a)
                break;
        }
        //compare which step is fewer
        if (n1 == 1 && step1 <= step2)
            System.out.println(step1);
        else if (n2 == 1 && step2 <= step1)
            System.out.println(step2);
        else
            System.out.println("-1");
        
        sc.close();
    }
}