import java.util.Scanner;

public class KilometerToMiles {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Distance in Kilometers: ");
        double kilometers = sc.nextDouble();

        double miles = kilometers * 0.621371;

        System.out.println("Distance in Miles = " + miles);

        sc.close();
    }
}