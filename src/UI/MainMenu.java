package UI;

import java.util.Scanner;

public class MainMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 3) {
            System.out.println("Main Menu:");
            System.out.println("1. Customer Menu");
            System.out.println("2. Admin Menu");
            System.out.println("3. Exit");

            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    CustomerMenu.display(); // Direct to Customer Menu
                    break;
                case 2:
                    AdminMenu.display(); // Direct to Admin Menu
                    break;
                case 3:
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
