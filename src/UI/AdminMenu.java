package UI;

import DAO.ProductDAO;
import DAO.UserDAO;
import Utilities.PrintUtility;
import Models.Product;
import Models.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 3) {
            System.out.print("\n");
            System.out.println("Admin Menu:");
            System.out.println("1. Print All Users");
            System.out.println("2. Print All Products");
            System.out.println("3. Back to Main Menu");

            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Fetch and print all users
                    UserDAO userDAO = new UserDAO();
                    List<User> users = userDAO.readAll();
                    PrintUtility.printUserList(users); // Print users using the PrintUtility
                    break;
                case 2:
                    // Fetch and print all products
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.readAll();
                    PrintUtility.printProductList(products); // Print products using the PrintUtility
                    break;
                case 3:
                    return; // Go back to Main Menu
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}