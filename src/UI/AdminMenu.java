package UI;

import DAO.DiscountDAO;
import DAO.ProductDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Models.Discount;
import Models.Product;
import Models.Transaction;
import Models.User;
import Utilities.InputValidator;
import Utilities.PrintUtility;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 7) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Print All Users");
            System.out.println("2. Print All Products");
            System.out.println("3. Print All Transactions");
            System.out.println("4. Print All Discount Promotions");
            System.out.println("5. Add User");
            System.out.println("6. Add Product");
            System.out.println("7. Add Discount Promotion");
            System.out.println("8. Modify User");
            System.out.println("9. Modify Product");
            System.out.println("10. Modify Discount Promotion");
            System.out.println("11. Modify Transaction");
            System.out.println("12. Delete User");
            System.out.println("13. Delete Product");
            System.out.println("14. Back to Main Menu");

            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Fetch and print all users
                    UserDAO userDAO = new UserDAO();
                    List<User> users = userDAO.readAll();
                    PrintUtility.printUserList(users);
                    break;
                case 2:
                    // Fetch and print all products
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.readAll();
                    PrintUtility.printProductList(products);
                    break;
                case 3:
                    // Fetch and print all transactions
                    TransactionDAO transactionDAO = new TransactionDAO();
                    List<Transaction> transactions = transactionDAO.readAll();
                    PrintUtility.printTransactionList(transactions);
                    break;
                case 4:
                    // Fetch and print all discounts
                    DiscountDAO discountDAO = new DiscountDAO();
                    List<Discount> discounts = discountDAO.readAll();
                    PrintUtility.printDiscountList(discounts);
                    break;
                case 5:
                    // Add new user
                    addNewUser(scanner);
                    break;
                case 6:
                    // Add new product
                    addNewProduct(scanner);
                    break;
                case 7:
                    // Add new discount promotion
                    addNewDiscountPromotion(scanner);
                    break;
                case 8:
                    // Modify existing user
                    modifyUser(scanner);
                    break;
                case 9:
                    // Modify existing product
                    modifyProduct(scanner);
                    break;
                case 10:
                    // Modify existing discount promotion
                    modifyDiscount(scanner);
                    break;
                case 11:
                    // Modify existing transaction
                    modifyTransaction(scanner);
                    break;
                case 12:
                    // Delete user
                    deleteUser(scanner);
                    break;
                case 13:
                    // Delete product
                    deleteProduct(scanner);
                    break;
                case 14:
                    return; // Go back to Main Menu
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void modifyDiscount(Scanner scanner) {
    }

    private static void modifyProduct(Scanner scanner) {

    }
    private static void modifyTransaction(Scanner scanner) {

    }

    private static void addNewUser(Scanner scanner) {
        // Get user details and create a new user
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone number (or press Enter to skip): ");
        String phoneNum = scanner.nextLine();
        if(phoneNum.isEmpty()) {
            //assigns phoneNumber to null if string is empty
            phoneNum = null;
        }
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter state: ");
        String state = scanner.nextLine();
        System.out.print("Enter zip code: ");
        String zipInput = "";
        int zip = 0;

        while (!zipInput.isEmpty()) {
            zipInput = scanner.nextLine();
            try {
                //Try to parse the input as an integer
                zip = Integer.parseInt(zipInput);

                // Check if the zip code has exactly 5 digits
                if (zip >= 10000 && zip <= 99999) {
                    break;  // Valid 5-digit zip code
                } else {
                    System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                }
            } catch (NumberFormatException e) {
                //If the input is not a valid integer, prompt the user again
                System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
            }
        }

        User newUser = new User(firstName, lastName, phoneNum, address, city, state, zip, true);
        UserDAO userDAO = new UserDAO();
        userDAO.create(newUser); // Assuming create method exists in UserDAO
        System.out.println("User added successfully.");
    }

    private static void addNewProduct(Scanner scanner) {
        // Get product details and create a new product
        scanner.nextLine();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product description (or press Enter to skip): ");
        String description = scanner.nextLine();
        if(description.isEmpty()) {
            //assigns description to null if string is empty
            description = null;
        }
        System.out.print("Enter product price: ");
        float price = scanner.nextFloat();
        System.out.print("Enter brand ID: ");
        int brandId = scanner.nextInt();

        Product newProduct = new Product(brandId, productName, description, price, true);
        ProductDAO productDAO = new ProductDAO();
        productDAO.create(newProduct); // Assuming create method exists in ProductDAO
        System.out.println("Product added successfully.");
    }

    private static void addNewDiscountPromotion(Scanner scanner) {
        // Get discount promotion details and create a new discount
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter discount description (or press Enter to skip): ");
        String description = scanner.nextLine();
        System.out.print("Enter discount amount: ");
        float amount = scanner.nextFloat();

        Discount newDiscount = new Discount(description, amount);
        DiscountDAO discountDAO = new DiscountDAO();
        newDiscount = discountDAO.create(newDiscount);
        System.out.println("Discount added successfully.");
    }

    private static void modifyUser(Scanner scanner) {
        // Get user ID and modify existing user
        int userId = InputValidator.promptForValidId("db_user", scanner);
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(userId);

        System.out.println("Updating account for " + user.getFirstName() + " " + user.getLastName());
        scanner.nextLine();  // Consume the leftover newline

        System.out.print("Enter new first name (or press Enter to skip): ");
        String firstName = scanner.nextLine();
        if (firstName.isEmpty()) firstName = user.getFirstName();  // No change if empty

        System.out.print("Enter new last name (or press Enter to skip): ");
        String lastName = scanner.nextLine();
        if (lastName.isEmpty()) lastName = user.getLastName();

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

        int zip = user.getZip();  //Default to current zip

        while (!zipInput.isEmpty()) {
            try {
                //Try to parse the input as an integer
                zip = Integer.parseInt(zipInput);

                // Check if the zip code has exactly 5 digits
                if (zip >= 10000 && zip <= 99999) {
                    break;  // Valid 5-digit zip code
                } else {
                    System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                    zipInput = scanner.nextLine();  // Get new input
                }
            } catch (NumberFormatException e) {
                //If the input is not a valid integer, prompt the user again
                System.out.print("Invalid zip code. Please enter a valid 5-digit zip code (or press Enter to skip): ");
                zipInput = scanner.nextLine();  // Get new input
            }
        }

        //Create updated user object and call the DAO update method
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

    private static void deleteUser(Scanner scanner) {
        // Get user ID and delete user
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        UserDAO userDAO = new UserDAO();
        userDAO.delete(userId); // Assuming delete method exists in UserDAO
        System.out.println("User deleted successfully.");
    }

    private static void deleteProduct(Scanner scanner) {
        // Get product ID and delete product
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        ProductDAO productDAO = new ProductDAO();
        productDAO.delete(productId); // Assuming delete method exists in ProductDAO
        System.out.println("Product deleted successfully.");
    }
}
