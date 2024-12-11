package UI;

import DAO.DiscountDAO;
import DAO.ProductDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Models.Discount;
import Models.Transaction;
import Utilities.PrintUtility;
import Models.Product;
import Models.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 5) {
            System.out.print("\n");
            System.out.println("Admin Menu:");
            System.out.println("1. Print All Users");
            System.out.println("2. Print All Products");
            System.out.println("3. Print All Transactions");
            System.out.println("4. Print All Discount Promotions");
            System.out.println("5. Back to Main Menu");

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
                    TransactionDAO transactionDAO = new TransactionDAO();
                    List<Transaction> transactions = transactionDAO.readAll();
                    PrintUtility.printTransactionList(transactions);
                    break;
                case 4:
                    DiscountDAO discountDAO = new DiscountDAO();
                    List<Discount> discounts = discountDAO.readAll();
                    PrintUtility.printDiscountList(discounts);
                case 5:
                    return; // Go back to Main Menu
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}