import java.util.Scanner;

public class CalculatorController {

    public static double calculate(double a, double b, String operation) {

        switch (operation) {

            case "add":
                return a + b;

            case "subtract":
                return a - b;

            case "multiply":
                return a * b;

            case "divide":
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double a = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double b = scanner.nextDouble();

        System.out.print("Enter operation (add/subtract/multiply/divide): ");
        String operation = scanner.next();

        try {
            double result = calculate(a, b, operation);
            System.out.println("Result: " + result);
        }
        catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
