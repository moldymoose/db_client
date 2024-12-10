package UI;

import DAO.LineItemDAO;
import DAO.ProductDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Utilities.InputValidator;
import Utilities.PrintUtility;
import model.LineItem;
import model.Product;
import model.Transaction;
import model.User;

import java.sql.Timestamp;
import java.util.Scanner;

public class CustomerMenu {

    private static final UserDAO userDAO = new UserDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void display() {
        System.out.print("\n");
        int userId = InputValidator.promptForValidId("db_user", scanner);
        User user = userDAO.read(userId);

        System.out.println("\nWelcome, " + user.getFirstName() + " " + user.getLastName());

        // Continue to the next options (e.g., Start Transaction, Update Account)
        showCustomerOptions(user);
    }

    public static void showCustomerOptions(User user) {
        int choice = -1;

        while (choice != 3) {
            System.out.println("1. Start Transaction");
            System.out.println("2. Update Account");
            System.out.println("3. Return to Main Menu");  // Add option to return to main menu
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startTransaction(user);
                    break;
                case 2:
                    updateAccount(user);
                    break;
                case 3:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void startTransaction(User user) {
        System.out.println("\nStarting a new transaction for " + user.getFirstName() + " " + user.getLastName());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Timestamp for transaction

        Transaction transaction = new Transaction(user.getId(), timestamp);
        TransactionDAO transactionDAO = new TransactionDAO();
        transaction = transactionDAO.create(transaction);

        System.out.println("Transaction: " + transaction.getId() + " created!");

        transactionLoop(scanner, transaction);

    }

    private static void transactionLoop(Scanner scanner, Transaction transaction) {
        //add first line item
        addLineItem(scanner, transaction);

        int choice = -1;

        while(choice != 3) {
            PrintUtility.printTransaction(transaction);

            System.out.print("\n");
            System.out.println("Cart Options");
            System.out.println("1: Add Item");
            System.out.println("2: Remove Item");
            System.out.println("3: Checkout");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addLineItem(scanner, transaction);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid!");
                    break;
            }
        }
    }

    private static void addLineItem(Scanner scanner, Transaction transaction) {
        PrintUtility.printProductList();

        int productID = InputValidator.promptForValidId("db_product", scanner);

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.read(productID);

        LineItemDAO lineItemDAO = new LineItemDAO();
        LineItem lineItem = new LineItem(productID, transaction.getId(), null);

        lineItem = lineItemDAO.create(lineItem);
    }

    public static void updateAccount(User user) {
        // Prompt for new user information
        System.out.println("Updating account for " + user.getFirstName() + " " + user.getLastName());
        scanner.nextLine();  // Consume the leftover newline

        System.out.print("Enter new phone number (or press Enter to skip): ");
        String phoneNum = scanner.nextLine();
        if (phoneNum.isEmpty()) phoneNum = user.getPhoneNum();  // No change if empty

        System.out.print("Enter new address (or press Enter to skip): ");
        String address = scanner.nextLine();
        if (address.isEmpty()) address = user.getAddress();  // No change if empty

        System.out.print("Enter new city (or press Enter to skip): ");
        String city = scanner.nextLine();
        if (city.isEmpty()) city = user.getCity();  // No change if empty

        System.out.print("Enter new state (or press Enter to skip): ");
        String state = scanner.nextLine();
        if (state.isEmpty()) state = user.getState();  // No change if empty

        System.out.print("Enter new zip code (or press Enter to skip): ");
        String zipInput = scanner.nextLine();
        int zip = user.getZip();  // Default to current zip
        if (!zipInput.isEmpty()) {
            zip = Integer.parseInt(zipInput);  // Only change if input is provided
        }

        // Create updated user object and call the DAO update method
        User updatedUser = new User(
                user.getId(),  // ID remains the same
                user.getFirstName(),
                user.getLastName(),
                phoneNum,
                address,
                city,
                state,
                zip,
                user.isActive()  // Active status remains the same
        );

        userDAO.update(updatedUser);
    }
}