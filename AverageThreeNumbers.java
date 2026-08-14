import java.util.Scanner;

public class AverageThreeNumbers {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter First Number: ");
        double num1 = sc.nextDouble();

        System.out.print("Enter Second Number: ");
        double num2 = sc.nextDouble();

        System.out.print("Enter Third Number: ");
        double num3 = sc.nextDouble();

        double average = (num1 + num2 + num3) / 3;

        System.out.println("Average = " + average);

        sc.close();
    }
}